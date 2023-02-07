package com.bolsaideas.springboot.app.oauth.services;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bolsaideas.springboot.app.commons.usuarios.models.entity.Usuario;
import com.bolsaideas.springboot.app.oauth.clients.UsuarioFeingClient;

import brave.Tracer;
import feign.FeignException;

@Service
public class UsuarioService implements UserDetailsService, IUsuarioService{
	
	private Logger log = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private UsuarioFeingClient client;
	
	@Autowired
	private Tracer tracer;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		try {
			
		
		
		Usuario usuario = client.findByUsername(username);
		
		
		List<GrantedAuthority> authorities = usuario.getRoles().
				stream().
				map(role -> new SimpleGrantedAuthority(role.getNombre()))
				//Para escribir el nombre del rol
				.peek(authority -> log.info("Role: " + authority.getAuthority()))
				.collect(Collectors.toList());
		
		log.info("Usuario Autenticado" +  username);
		
		return new User(usuario.getUsername(),usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
		
}		catch(FeignException e) {
			String error = "Error en el login, el usuario '"+ username + "' no existe";
			tracer.currentSpan().tag("error.mensaje", error + ": " + e.getMessage());
			log.error(error);
			throw new UsernameNotFoundException(error);
		}
		
	}

	@Override
	public Usuario findByUsername(String username) {
		return client.findByUsername(username);
	}

	@Override
	public Usuario update(Usuario usuario, Long id) {
		return client.update(usuario, id);
	}

}
