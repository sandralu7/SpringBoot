package com.bolsaideas.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.bolsaideas.springboot.app.models.entity.FichaAlbumUsuario;

public interface IRegistroAlbumesDAO extends CrudRepository<FichaAlbumUsuario, Long> {

}
