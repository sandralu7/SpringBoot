package com.proyecto.springboot.app.prueba.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.springboot.app.prueba.models.entity.Operacion;
import com.proyecto.springboot.app.prueba.models.service.IOperacionService;

@RestController
public class OperacionController {
	
	@Autowired
	private IOperacionService operacionService;
	
	@GetMapping("/listar")
	public List<Operacion> listar(){
		return operacionService.findAll();
	}
	
	@GetMapping("/ver/{id}")
	public Operacion detalle(@PathVariable Long id) {
		return operacionService.findById(id);
	}
	
	@PostMapping("/crear/{num1}/{num2}")
	public ResponseEntity<?> crear (@PathVariable Integer num1, @PathVariable Integer num2) {
		Map<String, Object> response = new HashMap<>();
		String resultado ="";
		String descripcion ="";
		
		if(num1<num2) {
			for(int i = num1; i<=num2; i++) {
				if(i%3==0) {
					if(i%5==0) {
						resultado = resultado + "FizzBuzz"; 
					}else {
						resultado = resultado + "Fizz"; 
					}
					
				}else if(i%5==0) {
					resultado = resultado + "Buzz"; 
				}
				else {
					resultado = resultado + i;
				}
				
				resultado = resultado + ",";
			}
			
			if(resultado.contains("FizzBuzz")){
				descripcion = "se encontraron múltiplos de 3 y de 5";
			}else if(resultado.contains("Fizz")){
				descripcion = "se encontraron múltiplos de 3";
			}else if(resultado.contains("Buzz")){
				descripcion = "se encontraron múltiplos de 5";
			}else{
				descripcion = "no se encontraron múltiplos de 3 y de 5";
			}
	
		
		Operacion operacion =  new Operacion();
		operacion.setCode("001");
		operacion.setDescription(descripcion);
		operacion.setError(false);
		operacion.setException("NO");
		operacion.setNum1(num1);
		operacion.setNum2(num2);
		operacion.setPath("/intraway/api/fizzbuzz/" + num1+"/"+num2);
		operacion.setStatus(200);
		operacion.setTimestamp(new Date());
		operacion.setResultListString(resultado);
		
		operacionService.save(operacion);
		response.put("mensaje", "La operación se ha creado con exito");
		response.put("operacion", operacion);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
		}
		
		else {
			response.put("error: ", "Los parámetors enviados son incorrectos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			
		}
	}
	

}
