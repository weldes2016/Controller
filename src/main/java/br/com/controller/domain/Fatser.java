package br.com.controller.domain;

import java.io.Serializable;

public class Fatser implements Serializable {

	private static final long serialVersionUID = 6570762995144898561L;
	private String stringField;
	private double doubleField;
	private String codVerificacao;
	private String numNfSer;
	private String numDup;
	private String sequencia;
	private String descricao;
	private String inscMunicipal;
	private String filial;
	private String coditemfatser;

	public Fatser(String stringField, double doubleField) {
		this.stringField = stringField;
		this.doubleField = doubleField;
		}

	public Fatser() {
		// TODO Auto-generated constructor stub
	}

	public String getStringField() {
		return stringField;
	}

	public void setStringField(String stringField) {
		this.stringField = stringField;
	}

	public double getDoubleField() {
		return doubleField;
	}

	public void setDoubleField(double doubleField) {
		this.doubleField = doubleField;
	}

	public String getCodVerificacao() {
		return codVerificacao;
	}

	public void setCodVerificacao(String codVerificacao) {
		this.codVerificacao = codVerificacao;
	}

	public String getNumNfSer() {
		return numNfSer;
	}

	public void setNumNfSer(String numNfSer) {
		this.numNfSer = numNfSer;
	}

	public String getNumDup() {
		return numDup;
	}

	public void setNumDup(String numDup) {
		this.numDup = numDup;
	}

	public String getSequencia() {
		return sequencia;
	}

	public void setSequencia(String sequencia) {
		this.sequencia = sequencia;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getInscMunicipal() {
		return inscMunicipal;
	}

	public void setInscMunicipal(String inscMunicipal) {
		this.inscMunicipal = inscMunicipal;
	}

	public String getFilial() {
		return filial;
	}

	public void setFilial(String filial) {
		this.filial = filial;
	}

	public String getCoditemfatser() {
		return coditemfatser;
	}

	public void setCoditemfatser(String coditemfatser) {
		this.coditemfatser = coditemfatser;
	}	
	
	
	
}
