package com.bolsaideas.springboot.web.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/variables")
public class EjemploVariablesRutaController {
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("titulo", "Enviar Parametros con path");
		return "variables/index";
	}

	@GetMapping("/string/{texto}")
	public String variables(@PathVariable String texto, Model model){
		model.addAttribute("titulo", "Ejemplo Parametros con path");
		model.addAttribute("resultado", texto);
		return "variables/ver";
		
	}
	@GetMapping("/string/{texto}/{numero}")
	public String variables(@PathVariable String texto,@PathVariable Integer numero, Model model){
		model.addAttribute("titulo", "Ejemplo Parametros con path");
		model.addAttribute("resultado", texto + "numero: " + numero);
		return "variables/ver";
		
	}
}
