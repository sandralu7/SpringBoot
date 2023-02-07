package com.bolsaideas.springboot.di.app.models.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

//Tambien podrìa ponerse con @Service
//Es posible ponerle un nombre para inyectar una implementaciòn concreta en autowired
//Al crear un constructor con parametros debe crearse uno sin parametros obligatoriamente si no hay error
@Component ("miServicioComplejo")
//Se ejecuta esta implementaciòn por encima de la otra
@Primary
public class MiServicioComplejo implements IServicio{

	public String operacion() {
		return "Ejecutando proceso Complejo";
	}
	
	
	
}
