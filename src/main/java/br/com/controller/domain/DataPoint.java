package br.com.controller.domain;

public class DataPoint {
	  private String data;
	  private Double valor;

	  public DataPoint(String data, Double valor) {
	    this.data = data;
	    this.valor = valor;
	  }

	  public String getData() {
	    return data;
	  }

	  public Double getValor() {
	    return valor;
	  }
	}
