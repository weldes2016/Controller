package br.com.controller.domain;

import java.io.Serializable;

public class ClientesWhatsApp implements Serializable{
	private static final long serialVersionUID = -3053621214364848719L;

	private int codCli;
    private String whatsApp;
	public int getCodCli() {
		return codCli;
	}
	public void setCodCli(int codCli) {
		this.codCli = codCli;
	}
	public String getWhatsApp() {
		return whatsApp;
	}
	public void setWhatsApp(String whatsApp) {
		this.whatsApp = whatsApp;
	}

}
