package com.bolsaideas.springboot.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bolsaideas.springboot.app.models.service.IUsuarioService;
import com.bolsaideas.springboot.app.view.xml.UsuarioList;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

	
	@Autowired
	@Qualifier("crudRepository")
	private IUsuarioService usuarioService;
	
	//Metodo que responde en formato Json
	//@ResponseBody se puede quitar porque es inherente al @RestController
	@GetMapping(value = "/listar")
	public @ResponseBody UsuarioList listar() {
		
		return new UsuarioList(usuarioService.findAll());
	}
}
