package com.bolsaideas.springboot.backend.apirest.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bolsaideas.springboot.backend.apirest.models.entity.Usuario;
import com.bolsaideas.springboot.backend.apirest.models.services.IUsuarioService;

//corss para permitir que un dominio acceda al servicio
@CrossOrigin(origins = { "http://localhost:4200" }) 
@RestController
@RequestMapping ("/api")
public class UsuarioRestController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping("/usuarios")
	public List<Usuario> index(){
		return usuarioService.findAll();
	}
	
	@GetMapping ("/usuarios/{id}")
	public ResponseEntity<?> show(@PathVariable  Long id) {
		Usuario usuario = null;
		Map<String, Object> response = new HashMap<>();
		try {
			 usuario = usuarioService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String, Object>> (response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(usuario==null) {
			response.put("mensaje", "El usuario con ID: " + id.toString() + " no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>> (response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	//@RequestBody es para recibir un parametro tipo json
	@PostMapping ("/usuarios")
	//esto hace que no se asigne el codigo 200 despues de crear el objeto sino el 201
	//@ResponseStatus (HttpStatus.CREATED)
	// @ResponseStatus: Esta anotacion se omite porque ya no se regresa ssiempre estado exitoso sino que puede variar
	public ResponseEntity<?>  create(@Valid @RequestBody Usuario usuario, BindingResult result) {
		
		Usuario usuarioNew = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			/*List<String> errors = new ArrayList<>();
		
			for(FieldError err : 	result.getFieldErrors()) {
				errors.add("El campo: '"+ err.getField() +"' " + err.getDefaultMessage());
			}*/
			
			//otra forma de hacerlo con streams y expresion lamda
			List<String> errors = result.getFieldErrors()
					.stream()
					//err guarda el valor de FieldError y lo pasa como parametro a la funcion lambda que tambien podria tener un return con los corchetes
					.map(err -> "El campo: '"+ err.getField() +"' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			usuarioNew = usuarioService.save(usuario);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		response.put("mensaje", "El usuario ha sido creado con exito");
		response.put("usuario", usuarioNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping ("/usuarios/{id}")
	
	//@ResponseStatus (HttpStatus.CREATED)
	public ResponseEntity<?>  update(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id) {
		Usuario usuarioActual = usuarioService.findById(id);
		Usuario usuarioUpdate = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			/*List<String> errors = new ArrayList<>();
		
			for(FieldError err : 	result.getFieldErrors()) {
				errors.add("El campo: '"+ err.getField() +"' " + err.getDefaultMessage());
			}*/
			
			//otra forma de hacerlo con streams y expresion lamda
			List<String> errors = result.getFieldErrors()
					//stream es como en vez de for each
					.stream()
					//err guarda el valor de FieldError y lo pasa como parametro a la funcion lambda que tambien podria tener un return con los corchetes
					.map(err -> "El campo: '"+ err.getField() +"' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(usuarioActual==null) {
			response.put("mensaje", "Error, no se pudo editar, El usuario con ID: " + id.toString() + " no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>> (response, HttpStatus.NOT_FOUND);
		}
		try {
			usuarioActual.setApellido(usuario.getApellido());
			usuarioActual.setNombre(usuario.getNombre());
			usuarioActual.setEmail(usuario.getEmail());
			usuarioActual.setCreateAt(new Date());
			//actualiza o guarda un nuevo usuario
			usuarioUpdate= usuarioService.save(usuarioActual);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El usuario ha sido actualizado con exito");
		response.put("usuario", usuarioUpdate);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
	}
	
	@DeleteMapping ("/usuarios/{id}")
	//se deja ese estado porque no retorna contenido	
	@ResponseStatus (HttpStatus.NO_CONTENT)
	public ResponseEntity<?>  delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			usuarioService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el update en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El cliente ha sido eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
