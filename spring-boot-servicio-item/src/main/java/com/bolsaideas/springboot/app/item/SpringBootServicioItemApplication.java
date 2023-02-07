package com.bolsaideas.springboot.app.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//inyectar clientes feing como otros microservicios
@EnableFeignClients
@EnableEurekaClient
//habilita con Hystrix el monitoreo de errores
@EnableCircuitBreaker
//@RibbonClient(name = "servicio-productos")
@SpringBootApplication
@EnableAutoConfiguration (exclude = {DataSourceAutoConfiguration.class})
public class SpringBootServicioItemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootServicioItemApplication.class, args);
	}

}
