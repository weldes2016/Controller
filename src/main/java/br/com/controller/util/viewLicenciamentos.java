package br.com.controller.util;

import java.sql.Date;

public class viewLicenciamentos {
	
	private int codpro;
	private String descrser; 
	private float vlrunit;
	private Date inicio;
	private String codcli;
	private String nom;
	private Date emissao;
	private Date vencimento;
	private int atraso;
	private float aberto;
	private String chave;
	
	public int getCodpro() {
		return codpro;
	}
	public void setCodpro(int codpro) {
		this.codpro = codpro;
	}
	public String getDescrser() {
		return descrser;
	}
	public void setDescrser(String descrser) {
		this.descrser = descrser;
	}
	public float getVlrunit() {
		return vlrunit;
	}
	public void setVlrunit(float vlrunit) {
		this.vlrunit = vlrunit;
	}
	public Date getInicio() {
		return inicio;
	}
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	
	public String getCodcli() {
		return codcli;
	}
	public void setCodcli(String codcli) {
		this.codcli = codcli;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Date getEmissao() {
		return emissao;
	}
	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}
	public int getAtraso() {
		return atraso;
	}
	public void setAtraso(int atraso) {
		this.atraso = atraso;
	}
	public Date getVencimento() {
		return vencimento;
	}
	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}
	public float getAberto() {
		return aberto;
	}
	public void setAberto(float aberto) {
		this.aberto = aberto;
	}
	public String getChave() {
		return chave;
	}
	public void setChave(String chave) {
		this.chave = chave;
	}
	
	

}
