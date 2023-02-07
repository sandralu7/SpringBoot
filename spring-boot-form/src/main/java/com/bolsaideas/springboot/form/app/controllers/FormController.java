package com.bolsaideas.springboot.form.app.controllers;

import java.awt.List;
import java.io.ObjectInputStream.GetField;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsaideas.springboot.form.app.editors.NombreMayusculaEditor;
import com.bolsaideas.springboot.form.app.models.domain.Usuario;
import com.bolsaideas.springboot.form.app.validation.UsuarioValidador;

@Controller
//esta anotación sirve para mantener los atributos del usuario entre peticiones a menos que estos se modifiquen
@SessionAttributes("user")
public class FormController {
	
	@Autowired
	private UsuarioValidador validador;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validador);
		binder.registerCustomEditor(String.class, new NombreMayusculaEditor());
		//Le puedo poner tambien el nombre del campo
		binder.registerCustomEditor(String.class,"nombre", new NombreMayusculaEditor());
	}
	
	@ModelAttribute("paises")
	public java.util.List<String> paiser(){
		return Arrays.asList("España", "Mexico", "Colombia", "Argentina");
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		Usuario usuario = new Usuario();
		usuario.setNombre("Sandra");
		usuario.setApellido("Rodriguez");
		usuario.setIdentificador("111");
		model.addAttribute("titulo", "Formulario Usuario");
		model.addAttribute("user", usuario);
		return "form";
	}

	//BindingResult tiene los resultados de la validaciòn y tiene que ir como parametro siguiente al objeto que se quiere validar
	@PostMapping("/form")
	public String procesar(@Valid @ModelAttribute ("user") Usuario usuario, BindingResult resut, Model model, SessionStatus status
//					, @RequestParam String username,
//					@RequestParam String password, 
//					@RequestParam String email
					) {
//		Usuario usuario = new Usuario();
//		usuario.setUsername(username);
//		usuario.setPassword(password);
//		usuario.setEmail(email);
		
		//Este se puede incluir directamente o con InitBinder
		//validador.validate(usuario, resut);
		model.addAttribute("titulo", "Resultado Form");
		
		if(resut.hasErrors()) {
//			Metodo para validar de forma manual
//			Map<String, String> errores = new HashMap<>();
//			resut.getFieldErrors().forEach(err ->{
//				errores.put(err.getField(), "El campo "+ err.getField().concat(err.getDefaultMessage()));
//			});
//			model.addAttribute("error", errores);
		
			
			return "form";
		}
		
	
		model.addAttribute("user", usuario);
		//elimina los datos del objeto usuario user porque ya se completo el proceso.
		status.setComplete();
		return "resultado";
	}
}
