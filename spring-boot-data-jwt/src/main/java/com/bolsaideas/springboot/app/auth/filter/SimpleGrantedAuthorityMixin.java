package com.bolsaideas.springboot.app.auth.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthorityMixin {

	//para indicar que ese es un constructor por defecto cuando se creen objetos authorities a partir del json
	@JsonCreator
	//@JsonProperty para convertir la propiedad del json que esta mapeada al rol
	public SimpleGrantedAuthorityMixin(@JsonProperty("authority") String role) {
		
	}

}
