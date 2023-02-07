package com.bolsaideas.springboot.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bolsaideas.springboot.app.models.entity.Usuario;

//Repository hace parte de @Component y quiere decir que la clase accede a datos
@Repository ("usuarioDaoJPA")
public class UsuarioDAOImpl implements IUsuarioDAO {

	//hacer operaciones sobre la base de datos
	@PersistenceContext
	private EntityManager em;
	
	//anotacion que toma el contenido del metodo y lo pone en una transaccion, con read only solo lectura
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("from Usuario").getResultList();
	}

	@Override
	public void save(Usuario usuario) {
		if(usuario.getId() != null && usuario.getId()>0) {
			em.merge(usuario);
		}else {
			em.persist(usuario);
		}
		
	}

	@Override
	public Usuario findOne(Long id) {
		// TODO Auto-generated method stub
		return em.find(Usuario.class, id);
	}

	@Override
	public void delete(Long id) {
		Usuario usuario = findOne(id);
		if(usuario!=null) {
			em.remove(usuario);
		}
		
	}

}
