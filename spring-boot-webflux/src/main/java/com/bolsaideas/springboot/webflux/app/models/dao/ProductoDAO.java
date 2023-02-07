package com.bolsaideas.springboot.webflux.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.bolsaideas.springboot.webflux.app.models.documents.Producto;

public interface ProductoDAO extends ReactiveMongoRepository<Producto, String> {

}
