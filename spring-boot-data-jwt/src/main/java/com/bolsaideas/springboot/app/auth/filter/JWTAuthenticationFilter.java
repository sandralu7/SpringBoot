package com.bolsaideas.springboot.app.auth.filter;

import java.io.IOException;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.bolsaideas.springboot.app.auth.service.JWTService;
import com.bolsaideas.springboot.app.auth.service.JWTServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


//Los filtros hacen algo antes o despues de ejecutar el Request o metodo handler del controlador
// al extender de ese filtro se ejecuta un metodo filter que a la final ejecuta el metodo attemptAuthentication 
// que se está sobreescribiendo. chain.doFilter es para seguir con la secuencia de filtros hasta llegar al handler
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authManager;
	
	public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

	//A pesar de ser un componente se pasa como argumento en el constructor y no con autowired porque en esta clase no se puede
	//Autowired, toca un bean dentro de la configuración
	private JWTService jwtService;
	
	public JWTAuthenticationFilter(AuthenticationManager authManager, JWTService jwtService) {

		this.authManager = authManager;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
		this.jwtService = jwtService;
	}




	//Se encarga de hacer el login para REST, hace la autenticaciòn cuando el filtro sea /login y el metodo post
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
	
		String username = obtainUsername(request);
		String password = obtainPassword(request);

		

		if(username != null && password != null) {
			logger.info("Username: "+ username +", Password: "+password);
		}
		//Si el ingreso de parametros es desde un JSON
		else{
			
			com.bolsaideas.springboot.app.models.entity.User user = null;
			try {
				user= new ObjectMapper().readValue(request.getInputStream(), com.bolsaideas.springboot.app.models.entity.User.class);
				username = user.getUsername();
				password = user.getPassword();
				logger.info("Desde RAW JSON Username: "+ username +", Password: "+password);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		username = username.trim();
		//Se comunica con JPAUserDetailService para hacer la consulta del usuario
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
		
		
		return authManager.authenticate(authToken);
	}




	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String token = jwtService.create(authResult);
		
		
		//se pasa el token a la cabecera y tiene que ir con "Authorization", "Bearer "
		response.addHeader(JWTServiceImpl.HEADER_STRING, JWTServiceImpl.TOKEN_PREFIX + token);
		
		//Se pasa el token al body
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("token", token);
		body.put("user", (User) authResult.getPrincipal());
		body.put("mensaje", String.format("hola %s, has iniciado sesión con exito", authResult.getName()));
		
		
		//guardar el body en el response y que la respuesta sea un json. Para convertir a Json se usa ObjectMapper
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(200);
		response.setContentType("application/json");
		
		
	}




	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("mensaje", "Error de autenticación: Username o Pass incorrecto");
		body.put("error", failed.getMessage());
		
		//La respuesta se pasa en jsonn
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(401);
		response.setContentType("application/json");
		
	}
	

}
