package com.bolsaideas.springboot.app.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LocaleController {

	@GetMapping("/locale")
	public String locale(HttpServletRequest request) {
		
		//obtiene la referencia de la ultima pagina
		String ultimaURL = request.getHeader("referer");
		System.out.println("Ultima url" + ultimaURL);
		return "redirect: ".concat(ultimaURL);
	}
	
}
