package com.bolsaideas.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsaideas.springboot.app.models.dao.IUsuarioDAO;
import com.bolsaideas.springboot.app.models.entity.Usuario;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioDAO usuarioDao;
	
	@Override
	@Transactional (readOnly = true)
	public List<Usuario> findAll() {
		return usuarioDao.findAll();
	}

	@Override
	@Transactional
	public void save(Usuario usuario) {
		usuarioDao.save(usuario);
	}

	@Override
	@Transactional (readOnly = true)
	public Usuario findOne(Long id) {
		return usuarioDao.findOne(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		usuarioDao.delete(id);
	}

	@Override
	public Page<Usuario> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario fetchByIdTodaInformacion(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
