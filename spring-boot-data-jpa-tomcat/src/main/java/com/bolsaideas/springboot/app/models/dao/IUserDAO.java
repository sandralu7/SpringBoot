package com.bolsaideas.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.bolsaideas.springboot.app.models.entity.User;

public interface IUserDAO extends CrudRepository<User, Long>{
	
	public User findByUsername(String username);

}
