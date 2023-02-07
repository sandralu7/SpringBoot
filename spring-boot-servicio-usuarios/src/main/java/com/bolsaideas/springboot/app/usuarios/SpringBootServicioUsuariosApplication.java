package com.bolsaideas.springboot.app.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan({"com.bolsaideas.springboot.app.commons.usuarios.models.entity"})
@SpringBootApplication
public class SpringBootServicioUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootServicioUsuariosApplication.class, args);
	}

}
