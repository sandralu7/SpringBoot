package com.bolsaideas.springboot.app.oauth.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

//para que se puedan actualizar las propiedades de configuración como el jwtKey sin bajar y volver a desplegar
@RefreshScope
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	private Environment env;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private InfoAdicionalToken infoAdicionalToken;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		//es el endpoint de autenticacion, valida las credenciales del front y del cliente para autenticar
		security.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		//aqui es para que un front tenga la clave para acceder
		clients.inMemory().withClient(env.getProperty("config.security.oauth.client.id"))
		//aqui es la clave del front
		.secret(passwordEncoder.encode(env.getProperty("config.security.oauth.client.secret")))
		//que puede hacer el front sobre los servicios
		.scopes("read", "write")
		//se autoriza el acceso por un password y el refresh token es para generar un nuevo token antes de que se venza el primero
		.authorizedGrantTypes("password", "refresh_token")
		//Tiempo de caducidad del token
		.accessTokenValiditySeconds(3600)
		//tiempo de caducidad del refresh token
		.refreshTokenValiditySeconds(3600);
//		//es posible configurar varios token para distintos front, angular, react, android etc.
//		.and().withClient("androidapp")
//		//aqui es la clave del front
//		.secret(passwordEncoder.encode("12345"))
//		//que puede hacer el front sobre los servicios
//		.scopes("read", "write")
//		//se autoriza el acceso por un password y el refresh token es para generar un nuevo token antes de que se venza el primero
//		.authorizedGrantTypes("password", "refresh_token")
//		//Tiempo de caducidad del token
//		.accessTokenValiditySeconds(3600)
//		//tiempo de caducidad del refresh token
//		.refreshTokenValiditySeconds(3600);
	}
	

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken,accessTokenConverter()));
		endpoints.authenticationManager(authenticationManager)
		.tokenStore(tokenStore())
		.accessTokenConverter(accessTokenConverter())
		.tokenEnhancer(tokenEnhancerChain);
	}

	@Bean
	public JwtTokenStore tokenStore() {
	
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(env.getProperty("config.security.oauth.jwt.key"));
		return tokenConverter;
	}
	

}
