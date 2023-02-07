package com.proyecto.springboot.app.prueba.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.springboot.app.prueba.models.dao.OperacionDao;
import com.proyecto.springboot.app.prueba.models.entity.Operacion;

@Service
public class OperacionServiceImpl implements IOperacionService{
	
	@Autowired
	private OperacionDao operacionDao;

	@Override
	@Transactional(readOnly = true)
	public List<Operacion> findAll() {
		
		return (List<Operacion>) operacionDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Operacion findById(Long id) {
		return operacionDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Operacion save(Operacion op) {
		return operacionDao.save(op);
		
	}

	@Override
	public void deleteById(Long id) {
		operacionDao.deleteById(id);
		
	}

}
