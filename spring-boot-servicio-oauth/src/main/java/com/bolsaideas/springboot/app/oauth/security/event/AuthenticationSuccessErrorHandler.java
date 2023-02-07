package com.bolsaideas.springboot.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.bolsaideas.springboot.app.commons.usuarios.models.entity.Usuario;
import com.bolsaideas.springboot.app.oauth.services.IUsuarioService;
import com.bolsaideas.springboot.app.oauth.services.UsuarioService;

import brave.Tracer;
import feign.FeignException;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	private Logger log = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);
	
	@Autowired
	private IUsuarioService ususarioService;
	
	@Autowired
	private Tracer tracer;
	
	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		
		if (authentication.getName().equalsIgnoreCase("frontendapp")) {
			return; // si es igual a frontendapp se salen del método!
		}
		
		UserDetails userDetail = (UserDetails) authentication.getPrincipal();
		String userName = userDetail.getUsername();
		String mensaje = "Success Login: " + userName;
		System.out.println(mensaje);
		log.info(mensaje);
 
		try {
			Usuario usuario = ususarioService.findByUsername(userName);
			if (usuario.getIntentos() != null && usuario.getIntentos() > 0) {
				usuario.setIntentos(0);
				ususarioService.update(usuario, usuario.getId());
			}
		} catch (FeignException e) {
			log.error(String.format("El usuario %s no existe en el sistema", userName));
		}
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		String mensaje = "Error en el login: "+exception.getMessage();
		
		if (authentication.getName().equalsIgnoreCase("frontendapp")) {
			return; // si es igual a frontendapp se salen del método!
		}
		
		log.error(mensaje);
		System.out.println(mensaje);
		System.out.println("Usuario error: " + authentication.getName());
		
		
		try {
			
			StringBuilder errors = new StringBuilder();
			errors.append(mensaje);
			Usuario usuario = ususarioService.findByUsername(authentication.getName());
			if(usuario.getIntentos()==null) {
				usuario.setIntentos(0);
			}
			
			log.info("El intento anterior es de " + usuario.getIntentos());
			usuario.setIntentos(usuario.getIntentos() + 1);
			log.info("El intento actual es de " + usuario.getIntentos());
			
			errors.append(" - Intentos del login: " + usuario.getIntentos());
			
			if(usuario.getIntentos()>=3) {
				String errorMaxIntentos = String.format("El usuario %s fue deshabilitado por maximos intentos", usuario.getUsername());
				log.error(errorMaxIntentos);
				errors.append(" - "+errorMaxIntentos);
				usuario.setEnabled(false);
			}
			ususarioService.update(usuario, usuario.getId());
			tracer.currentSpan().tag("error.mensaje", errors.toString());
			
		}catch (Exception e) {
			log.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
		}
	}

}
