package com.bolsaideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table (name="usua_usuarios")
public class Usuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "USUA_ID")
	private Long id;
	
	@NotEmpty
	@Size(min=2, max=10)
	@Column (name = "USUA_NOMBRE")
	private String nombre;
	
	@NotEmpty
	@Column (name = "USUA_APELLIDO")
	private String apellido;
	
	@NotEmpty
	@Email
	@Column (name = "USUA_EMAIL")
	private String email;
	
	@NotNull
	@Column (name = "USUA_CREATE_AT")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm")
	private Date createAt;
	
	@Column (name = "USUA_FOTO")
	private String foto;
	
	@OneToMany (mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference //relacion que se quiere mostrar dentro del json
	private List<AlbumUsuario> albumesUsuario;
	
	
//	@OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinColumn (name= "USUA_ID")
//	private List<AlbumUsuario> albumesUsuario;
	
	
	
//	@PrePersist
//	public void prePersist() {
//		createAt = new Date();
//	}

	public Usuario() {
		this.albumesUsuario = new ArrayList<AlbumUsuario>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<AlbumUsuario> getAlbumesUsuario() {
		return albumesUsuario;
	}

	public void setAlbumesUsuario(List<AlbumUsuario> albumesUsuario) {
		this.albumesUsuario = albumesUsuario;
	}
	
	public void addItemAlbumUsuario(AlbumUsuario albumUsuario) {
		this.albumesUsuario.add(albumUsuario);
	}

	@Override
	public String toString() {
		return nombre + " " + apellido ;
	}

}
