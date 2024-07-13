package br.com.controller.domain;

import java.io.Serializable;

public class Estado implements Serializable{
	
	private static final long serialVersionUID = 4129602432287524340L;
	private String Sigla;
	private double valor;
	public String getSigla() {
		return Sigla;
	}
	public void setSigla(String sigla) {
		Sigla = sigla;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	

}
