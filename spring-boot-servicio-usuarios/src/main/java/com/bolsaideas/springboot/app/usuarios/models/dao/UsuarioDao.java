package com.bolsaideas.springboot.app.usuarios.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.bolsaideas.springboot.app.commons.usuarios.models.entity.Usuario;

//CREA TODO EL CRUD AUTOMATICAMENTE SI ES DEL TIPO GET, Y SE USA EL PATH RETORNA LOS USUARIOS, 
//SI ES DE TIPO POST CREA EL USUARIO, SI ES DEL TIPO PUT MODIFICA EL USUARIO Y SI ES TIPO DELETE BORRA EL USUARIO. 
//ADICIONALMENTE SI QUEREMOS BUSCAR O ACTUALIZAR UN USUARIO EN PARTICULAR USAMOS /ID ADICIONAL AL PATH /USUARIOS
@RepositoryRestResource(path="usuarios")
public interface UsuarioDao extends PagingAndSortingRepository<Usuario, Long>{
	
	//asi va a ser el path del servicio rest y el parametro se va a poner como se puso en param
	@RestResource(path="buscar-username")
	public Usuario findByUsername (@Param ("username") String username);
	
	@Query("select u from Usuario u where u.username=?1")
	public Usuario obtenerPorUsuario(String usename);

}
