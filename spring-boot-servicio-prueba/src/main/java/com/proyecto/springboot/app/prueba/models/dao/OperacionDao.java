package com.proyecto.springboot.app.prueba.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.proyecto.springboot.app.prueba.models.entity.Operacion;

public interface OperacionDao extends CrudRepository<Operacion, Long> {

}
