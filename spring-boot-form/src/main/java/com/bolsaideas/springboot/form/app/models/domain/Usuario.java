package com.bolsaideas.springboot.form.app.models.domain;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.ISBN;
import org.springframework.format.annotation.DateTimeFormat;

import com.bolsaideas.springboot.form.app.validation.IdentificadorRejex;
import com.bolsaideas.springboot.form.app.validation.Requerido;

public class Usuario {
	
	//@Pattern (regexp = "[0-9]{2}[.,][0-9]{3}[0-9]{3}[-][A-Z]{1}")
	@IdentificadorRejex
	private String identificador;
	//@NotEmpty
	private String nombre;
	//@NotEmpty
	@Requerido
	private String apellido;

	@NotBlank
	@Size (min=4, max= 7)
	private String username;
	@NotEmpty
	private String password;
	@NotEmpty
	@Email (message = "Formato incorrecto")
	private String email;
	
	//Si fuera int no se puede validar con @NotNull puede ser con @Min
	@NotNull
	@Min(value = 5)
	@Max(value = 10)
	private Integer  cuenta; 
	
	@NotNull
	@Past
	//@Future
	//formato obligatorio que debe tener cuando el type es date
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaNacimiento;

	@NotEmpty
	private String pais;
	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public Integer getCuenta() {
		return cuenta;
	}

	public void setCuenta(Integer cuenta) {
		this.cuenta = cuenta;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
	
	

}
