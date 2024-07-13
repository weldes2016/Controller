package br.com.controller.domain;

import java.io.Serializable;

public class Empresas implements Serializable {
	private static final long serialVersionUID = 3217849163800254698L;
	private Integer id_empresa;
	private String usu;
	private String nom;
	private String inscriMunicipal;
	private String imgLogo;
	private String Ca;
	private String Conjurrc;

	public Integer getId_empresa() {
		return id_empresa;
	}

	public void setId_empresa(Integer id_empresa) {
		this.id_empresa = id_empresa;
	}

	public String getUsu() {
		return usu;
	}

	public void setUsu(String usu) {
		this.usu = usu;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCa() {
		return Ca;
	}

	public void setCa(String ca) {
		Ca = ca;
	}

	public String getConjurrc() {
		return Conjurrc;
	}

	public void setConjurrc(String conjurrc) {
		Conjurrc = conjurrc;
	}

	public String getInscriMunicipal() {
		return inscriMunicipal;
	}

	public void setInscriMunicipal(String inscriMunicipal) {
		this.inscriMunicipal = inscriMunicipal;
	}

	public String getImgLogo() {
		return imgLogo;
	}

	public void setImgLogo(String imgLogo) {
		this.imgLogo = imgLogo;
	}

	
}
