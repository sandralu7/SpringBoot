package com.bolsaideas.springboot.app.view.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.bolsaideas.springboot.app.models.entity.Usuario;


//Es una clase Wrapper porque un XML solo acepta Entity y no Collections
//Si no se asigna un nombre va a tener el de la clase
@XmlRootElement(name="usuarios")
public class UsuarioList {

	//Atributo que va a formar el XML
	@XmlElement (name="usuario")
	public List<Usuario> usuarios;

	public UsuarioList(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public UsuarioList() {
	
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	
	
	
}
