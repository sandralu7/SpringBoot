package com.bolsaideas.springboot.webflux.app;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.bolsaideas.springboot.webflux.app.models.dao.ProductoDAO;
import com.bolsaideas.springboot.webflux.app.models.documents.Producto;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner{
	
	@Autowired
	private ProductoDAO dao;
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	private static final Logger log = LoggerFactory.getLogger(SpringBootWebfluxApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		mongoTemplate.dropCollection("productos").subscribe();
		Flux.just(new Producto("TV", 452.4),
				new Producto("Camara", 177.89),
				new Producto("Apple", 777.89),
				new Producto("Iphone", 888.89)
				)
		//es igual que el map pero convierte el objeto Mono que es el que devuelve el save a un objeto product
		.flatMap(producto -> {
			producto.setCreateAt(new Date());
			return dao.save(producto);	
		})
		.subscribe(producto -> log.info("Insertando producto: " + producto.getNombre()));
		
	}

}
