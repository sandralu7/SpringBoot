package com.bolsaideas.springboot.backend.apirest.models.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsaideas.springboot.backend.apirest.models.dao.IUsuarioDAO;
import com.bolsaideas.springboot.backend.apirest.models.entity.Usuario;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioDAO usuarioDAO;
	
	@Override
	@Transactional (readOnly = true)
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return (List<Usuario>)usuarioDAO.findAll();
	}

	@Override
	@Transactional 
	public Usuario save(Usuario usuario) {
		return usuarioDAO.save(usuario);
	}

	@Override
	@Transactional 
	public void delete(Long id) {
		usuarioDAO.deleteById(id);
		
	}

	@Override
	@Transactional (readOnly = true)
	public Usuario findById(Long id) {
	
		return usuarioDAO.findById(id).orElse(null);
	}

}
