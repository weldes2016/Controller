package br.com.controller.domain;

import java.io.Serializable;
import java.util.List;

public class Banco implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id_banco;
	private String CodBco;
	private String TokenJuno;
	private String IdGalaxPay;
	private String HashGalaxPay;
	private String NomBco;
	private String CtaPlano;
	private List<Duprec> duprecs;
	
	public int getId_banco() {
		return id_banco;
	}

	public void setId_banco(int id_banco) {
		this.id_banco = id_banco;
	}

	
	public String getTokenJuno() {
		return TokenJuno;
	}

	public void setTokenJuno(String tokenJuno) {
		TokenJuno = tokenJuno;
	}

	public String getCodBco() {
		return CodBco;
	}

	public void setCodBco(String codBco) {
		CodBco = codBco;
	}
	
	public String getNomBco() {
		return NomBco;
	}

	public void setNomBco(String nomBco) {
		NomBco = nomBco;
	}

	public List<Duprec> getDuprecs() {
		return duprecs;
	}

	public void setDuprecs(List<Duprec> duprecs) {
		this.duprecs = duprecs;
	}

	public String getCtaPlano() {
		return CtaPlano;
	}

	public void setCtaPlano(String ctaPlano) {
		CtaPlano = ctaPlano;
	}

	public String getIdGalaxPay() {
		return IdGalaxPay;
	}

	public void setIdGalaxPay(String idGalaxPay) {
		IdGalaxPay = idGalaxPay;
	}

	public String getHashGalaxPay() {
		return HashGalaxPay;
	}

	public void setHashGalaxPay(String hashGalaxPay) {
		HashGalaxPay = hashGalaxPay;
	}	
	
}
