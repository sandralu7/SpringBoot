package com.bolsaideas.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bolsaideas.springboot.app.models.entity.Album;
import com.bolsaideas.springboot.app.models.entity.Ficha;
import com.bolsaideas.springboot.app.models.entity.Usuario;

public interface IAlbumDAO extends PagingAndSortingRepository<Album, Long> {

	
}
