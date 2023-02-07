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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonBackReference;




@Entity
@Table(name = "pan_albumes_usuarios")
public class AlbumUsuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ALBUM_USUARIO_ID")
	private Long idAlbumUsuario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ALBUM_ID")
	@NotNull (message = "Debe seleccionar un album")
	private Album album;
	
	@Column(name = "ALBUM_USUARIO_NIVEL")
	private String nivel;
	
	@OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn (name= "ALBUM_USUARIO_ID")
	private List<FichaAlbumUsuario> listaFichasAlbumPorUsuario;
	
	@ManyToOne (fetch = FetchType.LAZY)
	@JsonBackReference //relacion que no se quiere mostrar
	private Usuario usuario;
	

	public Long getIdAlbumUsuario() {
		return idAlbumUsuario;
	}

	public void setIdAlbumUsuario(Long idAlbumUsuario) {
		this.idAlbumUsuario = idAlbumUsuario;
	}

	
	public Long getTotalLlenado() {
		return album.getCantidadFichas() - listaFichasAlbumPorUsuario.size();
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public List<FichaAlbumUsuario> getListaFichasAlbumPorUsuario() {
		return listaFichasAlbumPorUsuario;
	}

	public void setListaFichasAlbumPorUsuario(List<FichaAlbumUsuario> listaFichasAlbumPorUsuario) {
		this.listaFichasAlbumPorUsuario = listaFichasAlbumPorUsuario;
	}

	//para que cuando genere el xml y consulte los albumes no se regrese a consultar otra vez el usuario
	@XmlTransient
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	

}
