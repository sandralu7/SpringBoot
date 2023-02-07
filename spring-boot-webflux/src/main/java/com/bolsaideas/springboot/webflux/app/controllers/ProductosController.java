package com.bolsaideas.springboot.webflux.app.controllers;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import com.bolsaideas.springboot.webflux.app.models.dao.ProductoDAO;
import com.bolsaideas.springboot.webflux.app.models.documents.Producto;

import reactor.core.publisher.Flux;

@Controller
public class ProductosController {
	
	@Autowired
	private ProductoDAO dao;
	
	private static final Logger log = LoggerFactory.getLogger(ProductosController.class);
	
	@GetMapping({"/listar", "/"})
	public String listar(Model model) {
		Flux<Producto> productos = dao.findAll().map(producto ->{
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
			
		});
		productos.subscribe(prod -> log.info(prod.getNombre()));
		
		model.addAttribute("productos", productos);
		model.addAttribute("Titulo", "Listado de productos");
		return "listar";
	}
	
	@GetMapping("/listar-datadriver")
	public String listarDataDriver(Model model) {
		Flux<Producto> productos = dao.findAll().map(producto ->{
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
			
		}).delayElements(Duration.ofSeconds(1));
		productos.subscribe(prod -> log.info(prod.getNombre()));
		
		model.addAttribute("productos", new ReactiveDataDriverContextVariable(productos, 1));
		model.addAttribute("Titulo", "Listado de productos");
		return "listar";
	}
	
	@GetMapping("/listarfull")
	public String listarFull(Model model) {
		Flux<Producto> productos = dao.findAll().map(producto ->{
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		//Se repite 5000 veces el flujo actual	
		}).repeat(5000);
		productos.subscribe(prod -> log.info(prod.getNombre()));
		
		model.addAttribute("productos", productos);
		model.addAttribute("Titulo", "Listado de productos");
		return "listar";
	}

	//Se ejecuta mas rapido porque fracciona los registros
	@GetMapping("/listar-chunked")
	public String listarChunked(Model model) {
		Flux<Producto> productos = dao.findAll().map(producto ->{
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		//Se repite 5000 veces el flujo actual	
		}).repeat(5000);
		productos.subscribe(prod -> log.info(prod.getNombre()));
		
		model.addAttribute("productos", productos);
		model.addAttribute("Titulo", "Listado de productos");
		return "listar-chunked";
	}
}
