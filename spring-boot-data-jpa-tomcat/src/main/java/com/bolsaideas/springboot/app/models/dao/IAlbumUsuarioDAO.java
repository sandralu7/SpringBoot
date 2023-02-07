package com.bolsaideas.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bolsaideas.springboot.app.models.entity.AlbumUsuario;


public interface IAlbumUsuarioDAO extends CrudRepository<AlbumUsuario, Long>{

	
	@Query ("select a from AlbumUsuario a where a.usuario.id = ?1 and a.album.id = ?2")
	public AlbumUsuario findAlbumUsuarioByAlbumUsuario(Long idUsuario, Long idAlbum);
	
	@Query ("select a from AlbumUsuario a where a.usuario.id = ?1 ")
	public List<AlbumUsuario> findAlbumUsuarioByUsuario (Long idUsuario);
	
	@Query ("select au from AlbumUsuario au join fetch au.usuario u join fetch au.album a join fetch au.listaFichasAlbumPorUsuario fu join fetch fu.ficha where au.idAlbumUsuario=?1")
	public AlbumUsuario fetchByIdTodaInformacion(Long idAlbumUsuario);
}
