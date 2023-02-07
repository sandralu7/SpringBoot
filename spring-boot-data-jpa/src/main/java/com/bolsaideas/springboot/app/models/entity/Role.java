package com.bolsaideas.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="USUA_AUTHORITIES", uniqueConstraints = {@UniqueConstraint(columnNames = {"USUA_ID","AUTH_AUTHORITY"})})
public class Role implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "AUTH_ID")
	private Long id;
	
	@Column (name = "AUTH_AUTHORITY")
	private String authority;
	
	
	
	
	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public String getAuthority() {
		return authority;
	}




	public void setAuthority(String authority) {
		this.authority = authority;
	}




	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
