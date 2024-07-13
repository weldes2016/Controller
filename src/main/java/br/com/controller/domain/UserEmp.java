package br.com.controller.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "useremp")
public class UserEmp implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column
	private String codusu;

	@ManyToOne
	private Usuario usuario;

	@Column
	private String nom;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodusu() {
		return codusu;
	}

	public void setCodusu(String codusu) {
		this.codusu = codusu;
	}

	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

}
