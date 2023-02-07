package com.bolsaideas.springboot.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bolsaideas.springboot.app.models.entity.Usuario;

public interface IUsuarioDAOCrudR extends PagingAndSortingRepository<Usuario, Long> {
	
	@Query("select u from Usuario u left join fetch u.albumesUsuario au where u.id = ?1 ")
	public Usuario fetchByIdTodaInformacion (Long id);

}
