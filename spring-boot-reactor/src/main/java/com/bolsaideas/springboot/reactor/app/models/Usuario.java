package com.bolsaideas.springboot.reactor.app.models;

public class Usuario {
	
	private String nombre;
	private String apellidos;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public Usuario(String nombre, String apellidos) {

		this.nombre = nombre;
		this.apellidos = apellidos;
	}
	
	

}
