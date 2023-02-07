package com.bolsaideas.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsaideas.springboot.app.models.dao.IUsuarioDAO;
import com.bolsaideas.springboot.app.models.dao.IUsuarioDAOCrudR;
import com.bolsaideas.springboot.app.models.entity.Usuario;

@Service ("crudRepository")
public class UsuarioServiceImplCrud implements IUsuarioService {

	@Autowired
	private IUsuarioDAOCrudR usuarioDao;
	
	@Override
	@Transactional (readOnly = true)
	public List<Usuario> findAll() {
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Override
	@Transactional
	public void save(Usuario usuario) {
		usuarioDao.save(usuario);
	}

	@Override
	@Transactional (readOnly = true)
	public Usuario findOne(Long id) {
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		usuarioDao.deleteById(id);
	}

	@Override
	@Transactional (readOnly = true)
	public Page<Usuario> findAll(Pageable pageable) {
		return usuarioDao.findAll(pageable);
	}

	@Override
	@Transactional (readOnly = true)
	public Usuario fetchByIdTodaInformacion(Long id) {
		return usuarioDao.fetchByIdTodaInformacion(id);
	}

}
