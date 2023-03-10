package com.bolsaideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="USUA_USER")
//Serializable es por si se quiere guardar la información entre request
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "USUA_ID")
	private Long id;
	
	@Column (name = "USUA_USERNAME", unique = true, length=40)
	private String username;
	
	@Column (name = "USUA_PASSWORD", length = 60)
	private String password;
	
	@Column (name = "USUA_ENABLED", length = 60)
	private boolean enabled;
	
	@OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn (name="USUA_ID")
	private List<Role> roles;
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public boolean getEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public List<Role> getRoles() {
		return roles;
	}


	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
