package com.bolsaideas.springboot.app.usuarios;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import com.bolsaideas.springboot.app.commons.usuarios.models.entity.Role;
import com.bolsaideas.springboot.app.commons.usuarios.models.entity.Usuario;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		
		//Expone los id en el json del servicio Rest
		config.exposeIdsFor(Usuario.class, Role.class);
	}

}
