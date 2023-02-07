package com.bolsaideas.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bolsaideas.springboot.app.models.entity.Album;
import com.bolsaideas.springboot.app.models.entity.AlbumUsuario;
import com.bolsaideas.springboot.app.models.entity.Ficha;
import com.bolsaideas.springboot.app.models.entity.FichaAlbumUsuario;

public interface IAlbumService {

	

	public List<Album> findAll();
	
	public Page<Album> findAll(Pageable pageable);
	
	public void save(Album usuario);
	
	public Album findOne(Long id);
	
	public void delete(Long id);
	
	public List<Ficha> findByNombre(String term);
	
	public void saveRegistroAlbum(FichaAlbumUsuario albumUsuario);
	
	public Ficha findFichaById(Long id);
	
	public AlbumUsuario findAlbumUsuarioByAlbumUsuario(Long idUsuario, Long idAlbum);
	
	public void saveAlbumUsuario(AlbumUsuario albumUsuario);
	
	public List<AlbumUsuario>  findAlbumUsuarioByUsuario (Long idUsuario);
	
	public AlbumUsuario findAlbumUsuarioById (Long id);
	
	public void deleteAlbumUsuario(Long id);
	
	public void deleteFichaAlbumUsuario(Long id);
	
	public FichaAlbumUsuario findFichaAlbumUsuarioById(Long id);
	
	public void modificarFichaAlbumUsuarioByID (FichaAlbumUsuario ficha);
	
	public AlbumUsuario fetchByIdTodaInformacion(Long idAlbumUsuario);
}
