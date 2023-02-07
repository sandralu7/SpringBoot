package com.bolsaideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "pan_fichas_albumes_usuarios")
public class FichaAlbumUsuario implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FICHA_ALBUM_USUARIO_ID")
	private Long idFichaAlbumUsuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FICHA_ALBUM_USUARIO_CREATE_AT")
	private Date createAt;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FICHA_ID")
	@JsonIgnoreProperties ({"hibernateLazyInitializer", "handler"}) //ignorar algunos atributos que se crean dentro de la entidad producto
	private Ficha ficha;
	
	@Column (name = "FICHA_ALBUM_USUARIO_REPETIDAS")
	private Integer repetidas;

	public Long getIdFichaAlbumUsuario() {
		return idFichaAlbumUsuario;
	}

	public void setIdFichaAlbumUsuario(Long idFichaAlbumUsuario) {
		this.idFichaAlbumUsuario = idFichaAlbumUsuario;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Ficha getFicha() {
		return ficha;
	}

	public void setFicha(Ficha ficha) {
		this.ficha = ficha;
	}

	public Integer getRepetidas() {
		return repetidas;
	}

	public void setRepetidas(Integer repetidas) {
		this.repetidas = repetidas;
	}
	
	public Integer calcularTotal () {
		return this.repetidas + 1;
	}

}
