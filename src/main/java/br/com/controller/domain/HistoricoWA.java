package br.com.controller.domain;

import java.io.Serializable;
import java.util.Date;

public class HistoricoWA implements Serializable{

	private static final long serialVersionUID = 5892634348116632394L;
	private int id_historico;
	private int id_duprec;
	private Date data_historico;
	private String historico;
	private String WhastApp;
	private String duplicata;
	
	public int getId_historico() {
		return id_historico;
	}
	public void setId_historico(int id_historico) {
		this.id_historico = id_historico;
	}
	public int getId_duprec() {
		return id_duprec;
	}
	public void setId_duprec(int id_duprec) {
		this.id_duprec = id_duprec;
	}
	public Date getData_historico() {
		return data_historico;
	}
	public void setData_historico(Date data_historico) {
		this.data_historico = data_historico;
	}
	public String getHistorico() {
		return historico;
	}
	public void setHistorico(String historico) {
		this.historico = historico;
	}
	public String getWhastApp() {
		return WhastApp;
	}
	public void setWhastApp(String whastApp) {
		WhastApp = whastApp;
	}
	public String getDuplicata() {
		return duplicata;
	}
	public void setDuplicata(String duplicata) {
		this.duplicata = duplicata;
	}
	
	
	

}
