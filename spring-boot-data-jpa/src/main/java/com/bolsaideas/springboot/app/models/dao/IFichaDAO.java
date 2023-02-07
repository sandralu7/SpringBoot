package com.bolsaideas.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bolsaideas.springboot.app.models.entity.Ficha;

public interface IFichaDAO extends CrudRepository<Ficha, Long>{
	
	@Query ("select f from Ficha f where f.nombre like %?1% ")
	public List<Ficha> findByNombre(String term);
	
	public List<Ficha> findByNombreIgnoreCase(String term);
}
