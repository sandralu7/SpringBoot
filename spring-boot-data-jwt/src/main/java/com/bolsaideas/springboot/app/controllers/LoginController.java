package com.bolsaideas.springboot.app.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@GetMapping ("/login")
	public String login(@RequestParam(value="error", required = false) String error,
			@RequestParam(value="logout", required = false) String logout,
			Model model, Principal principal, RedirectAttributes flash) {
		System.out.println("Principal: " + principal);
		if(principal != null) {
				flash.addFlashAttribute("info", "Ya ha iniciado sesión");
				//hace un redirect a la pagina de inicio
				return "redirect:/";
		}
		
		if(error != null){
			model.addAttribute("error", "Nombre de usuario o contraseña incorrecta");
		}
		
		if(logout!=null) {
			model.addAttribute("success", "Ha cerrado sesión con exito");
		}
		
		return "login";
	}
}
