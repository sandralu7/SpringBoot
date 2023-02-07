package com.bolsaideas.springboot.web.app.controllers;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bolsaideas.springboot.web.app.models.Usuario;

//Antotacion que marca la clase como un controlador y hereda de @Component que marca la clase como un componente Spring
//Maneja peticiones del usuario
//Metodos siempre publicos
@Controller 
//se indica esta como ruta de primer nivel
@RequestMapping("/app")
public class IndexController {
	
	@Value("${texto.indexcontroller.index.titulo}")
	private String textoIndex;
	
	@Value("${texto.indexcontroller.perfil.titulo}")
	private String textoPerfil;
	
	@Value("${texto.indexcontroller.listar.titulo")
	private String textoListar;
	
	//Cuando en la url se invoque con el path index el metodo se ejecuta. Si no se indica la peticiòn es Get
	//Tambien podría usarse GetMapping o @RequestMapping
	//En vez de Model para pasar datos a la vista podria usarse ModelAndView
	@GetMapping({"/index", "/", "/home"})
	public String index(Model model) {
		model.addAttribute("titulo", textoIndex);
		return "index";
	}
	
	@RequestMapping("/perfil")
	public String perfil(Model model){
		Usuario usuario = new Usuario();
		usuario.setNombre("Sandra");
		usuario.setApellido("Rodriguez");
		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo", textoPerfil.concat(usuario.getNombre()));
		return "perfil";
	}
	
	@RequestMapping("/listar")
	public String listar(Model model){
		
		
		
		model.addAttribute("titulo", textoListar);
		return "listar";
		
	}
	
	//Quedaria un metodo comun a cualquier metodo del controlador
	@ModelAttribute("usuarios")
	public List<Usuario> poblarUsuarios(){
		
List<Usuario> listadoUsuarios = new ArrayList<>();
		
		Usuario user1 = new Usuario();
		user1.setNombre("Sandra");
		user1.setApellido("Rodriguez");
		user1.setEmail("sandra@gmail.com");
		
		Usuario user2 = new Usuario();
		user2.setNombre("Lucia");
		user2.setApellido("Rodriguez");
		user2.setEmail("sandralu@gmail.com");
		
		listadoUsuarios.add(user1);
		listadoUsuarios.add(user2);
		return listadoUsuarios;
		
	}
}
