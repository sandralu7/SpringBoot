package com.bolsaideas.springboot.backend.apirest.models.services;

import java.util.List;

import com.bolsaideas.springboot.backend.apirest.models.entity.Usuario;

public interface IUsuarioService {

	public List<Usuario> findAll();
	
	public Usuario save(Usuario usuario);
	
	public void delete(Long id);
	
	public Usuario findById(Long id);
	
	
}
