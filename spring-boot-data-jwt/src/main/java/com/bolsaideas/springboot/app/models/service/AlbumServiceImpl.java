package com.bolsaideas.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsaideas.springboot.app.models.dao.IAlbumDAO;
import com.bolsaideas.springboot.app.models.dao.IAlbumUsuarioDAO;
import com.bolsaideas.springboot.app.models.dao.IFichaDAO;
import com.bolsaideas.springboot.app.models.dao.IRegistroAlbumesDAO;
import com.bolsaideas.springboot.app.models.entity.Album;
import com.bolsaideas.springboot.app.models.entity.AlbumUsuario;
import com.bolsaideas.springboot.app.models.entity.Ficha;
import com.bolsaideas.springboot.app.models.entity.FichaAlbumUsuario;

@Service
public class AlbumServiceImpl implements IAlbumService{

	@Autowired
	private IAlbumDAO albumDao;
	
	@Autowired
	private IFichaDAO fichaDao;
	
	@Autowired
	private IRegistroAlbumesDAO registroDao;
	
	@Autowired
	private IAlbumUsuarioDAO albumUsuarioDao;
	
	@Override
	@Transactional (readOnly = true)
	public List<Album> findAll() {
		return (List<Album>) albumDao.findAll();
	}

	@Override
	@Transactional
	public void save(Album album) {
		albumDao.save(album);
	}

	@Override
	@Transactional (readOnly = true)
	public Album findOne(Long id) {
		return albumDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		albumDao.deleteById(id);
	}

	@Override
	@Transactional (readOnly = true)
	public Page<Album> findAll(Pageable pageable) {
		return albumDao.findAll(pageable);
	}

	@Override
	@Transactional (readOnly = true)
	public List<Ficha> findByNombre(String term) {
		return fichaDao.findByNombre(term);
	}

	@Override
	@Transactional
	public void saveRegistroAlbum(FichaAlbumUsuario albumUsuario) {
		registroDao.save(albumUsuario);
	}

	@Override
	@Transactional(readOnly = true)
	public Ficha findFichaById(Long id) {
		return fichaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public AlbumUsuario findAlbumUsuarioByAlbumUsuario(Long idUsuario, Long idAlbum) {
		return (AlbumUsuario) albumUsuarioDao.findAlbumUsuarioByAlbumUsuario(idUsuario, idAlbum);
	}
	
	@Override
	@Transactional
	public void saveAlbumUsuario(AlbumUsuario albumUsuario) {
		albumUsuarioDao.save(albumUsuario);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AlbumUsuario> findAlbumUsuarioByUsuario(Long idUsuario) {
		return albumUsuarioDao.findAlbumUsuarioByUsuario(idUsuario);
	}

	@Override
	@Transactional(readOnly = true)
	public AlbumUsuario findAlbumUsuarioById(Long id) {
		return albumUsuarioDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteAlbumUsuario(Long id) {
		albumUsuarioDao.deleteById(id);
		
	}

	@Override
	@Transactional
	public void deleteFichaAlbumUsuario(Long id) {
		registroDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public FichaAlbumUsuario findFichaAlbumUsuarioById(Long id) {
		return registroDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void modificarFichaAlbumUsuarioByID(FichaAlbumUsuario ficha) {
		registroDao.save(registroDao.findById(ficha.getIdFichaAlbumUsuario()).orElse(null));
		
	}

	@Override
	@Transactional(readOnly = true)
	public AlbumUsuario fetchByIdTodaInformacion(Long idAlbumUsuario) {
		return albumUsuarioDao.fetchByIdTodaInformacion(idAlbumUsuario);
	}
	
	
}
