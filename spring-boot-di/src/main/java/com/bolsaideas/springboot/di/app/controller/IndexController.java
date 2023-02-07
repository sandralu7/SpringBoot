package com.bolsaideas.springboot.di.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bolsaideas.springboot.di.app.models.service.IServicio;


@Controller
public class IndexController {
	
	@Autowired 
	@Qualifier("miServicio")
	private IServicio servicio;
	
	@GetMapping({"/", "",  "/index"})
	public String index(Model model) {
		
		model.addAttribute("objeto", servicio.operacion());
		return "index";
	}
	
//	@Autowired se puede inyectar por constructor o por el set. Si se inyecta por contructor
// 	no se necesita poner explicitamente el autowired.
//	public IndexController(IServicio servicio) {
//		super();
//		this.servicio = servicio;
//	}

}
