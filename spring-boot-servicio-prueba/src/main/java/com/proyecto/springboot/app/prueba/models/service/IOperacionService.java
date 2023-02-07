package com.proyecto.springboot.app.prueba.models.service;

import java.util.List;

import com.proyecto.springboot.app.prueba.models.entity.Operacion;

public interface IOperacionService {

	public List<Operacion> findAll();
	public Operacion findById(Long id);
	public Operacion save (Operacion op);
	public void deleteById(Long id);
	
}
