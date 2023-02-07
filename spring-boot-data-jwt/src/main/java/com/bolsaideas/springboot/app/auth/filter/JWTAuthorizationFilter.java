package com.bolsaideas.springboot.app.auth.filter;

import java.io.IOException;
import java.security.Key;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.bolsaideas.springboot.app.auth.service.JWTService;
import com.bolsaideas.springboot.app.auth.service.JWTServiceImpl;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	
	public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	
	private JWTService service;
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTService service) {
		super(authenticationManager);
		this.service = service;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//trae la información del token
		String header = request.getHeader(JWTServiceImpl.HEADER_STRING);
		if(!requieresAuthentication(header)) {
			chain.doFilter(request, response);
			return;
		}
		
		UsernamePasswordAuthenticationToken authentication = null; 
		
		if(service.validate(header)) {
			
			authentication = new UsernamePasswordAuthenticationToken(service.getUsername(header), null, service.getRoles(header) );	
			
		}
		
		//se asigna la autenticacion dentro del contexto y eso autentica al usuario dentro de los request
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		//para que continue con la ejecuciòn de los otros filtros
		chain.doFilter(request, response);
	}

	protected boolean requieresAuthentication(String header) {
		if(header == null || !header.startsWith(JWTServiceImpl.TOKEN_PREFIX)) {
			
			return false;
		}
		return true;
	}
	
}
