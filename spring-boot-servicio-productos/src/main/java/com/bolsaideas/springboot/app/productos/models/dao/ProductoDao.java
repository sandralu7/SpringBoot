package com.bolsaideas.springboot.app.productos.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.bolsaideas.springboot.app.commons.models.Producto;

public interface ProductoDao extends CrudRepository<Producto, Long>{

}
