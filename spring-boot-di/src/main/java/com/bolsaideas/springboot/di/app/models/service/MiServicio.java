package com.bolsaideas.springboot.di.app.models.service;

import org.springframework.stereotype.Component;

//Tambien podrìa ponerse con @Service
//Es posible ponerle un nombre para inyectar una implementaciòn concreta en autowired
//Al crear un constructor con parametros debe crearse uno sin parametros obligatoriamente si no hay error
@Component ("miServicio")
public class MiServicio implements IServicio{

	public String operacion() {
		return "Ejecutando proceso";
	}
	
	
	
}
