package br.com.controller.domain;

import java.io.Serializable;

public class Parametros implements Serializable {

	private static final long serialVersionUID = -6790512896165654942L;
	
	private String pathGravacao;
	private String gravacao;
	private int ultimaNota;
	private String driver;
	private String banco;
	private String instacia;
	private String host;
	private String porta;
	private int modo;
	private String whatsApp;
	private String email;
	private String login;
	private String password;
	
	//----------------CADUSU-----------------//
	
	private String udriver;
	private String ubanco;
	private String uhost;
	private String uporta;
	private String ulogin;
	private String upassword;
	
	// ----------------- MSSQL -----------------//
	

	private String minstance;
	private String mhost;
	private String mbanco;
	private String mloginBanco;
	private String mdriver;
	private String mpassword;
	private String mporta;
	
	
	public int getModo() {
		return modo;
	}
	public void setModo(int modo) {
		this.modo = modo;
	}
	public String getPathGravacao() {
		return pathGravacao;
	}
	public void setPathGravacao(String pathGravacao) {
		this.pathGravacao = pathGravacao;
	}
	public String getGravacao() {
		return gravacao;
	}
	public void setGravacao(String gravacao) {
		this.gravacao = gravacao;
	}
	public int getUltimaNota() {
		return ultimaNota;
	}
	public void setUltimaNota(int ultimaNota) {
		this.ultimaNota = ultimaNota;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	public String getInstacia() {
		return instacia;
	}
	public void setInstacia(String instacia) {
		this.instacia = instacia;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPorta() {
		return porta;
	}
	public void setPorta(String porta) {
		this.porta = porta;
	}
	public String getWhatsApp() {
		return whatsApp;
	}
	public void setWhatsApp(String whatsApp) {
		this.whatsApp = whatsApp;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUdriver() {
		return udriver;
	}
	public void setUdriver(String udriver) {
		this.udriver = udriver;
	}
	public String getUbanco() {
		return ubanco;
	}
	public void setUbanco(String ubanco) {
		this.ubanco = ubanco;
	}
	public String getUhost() {
		return uhost;
	}
	public void setUhost(String uhost) {
		this.uhost = uhost;
	}
	public String getUporta() {
		return uporta;
	}
	public void setUporta(String uporta) {
		this.uporta = uporta;
	}
	public String getUlogin() {
		return ulogin;
	}
	public void setUlogin(String ulogin) {
		this.ulogin = ulogin;
	}
	public String getUpassword() {
		return upassword;
	}
	public void setUpassword(String upassword) {
		this.upassword = upassword;
	}
	public String getMinstance() {
		return minstance;
	}
	public void setMinstance(String minstance) {
		this.minstance = minstance;
	}
	public String getMhost() {
		return mhost;
	}
	public void setMhost(String mhost) {
		this.mhost = mhost;
	}
	public String getMbanco() {
		return mbanco;
	}
	public void setMbanco(String mbanco) {
		this.mbanco = mbanco;
	}
	public String getMloginBanco() {
		return mloginBanco;
	}
	public void setMloginBanco(String mloginBanco) {
		this.mloginBanco = mloginBanco;
	}
	public String getMdriver() {
		return mdriver;
	}
	public void setMdriver(String mdriver) {
		this.mdriver = mdriver;
	}
	public String getMpassword() {
		return mpassword;
	}
	public void setMpassword(String mpassword) {
		this.mpassword = mpassword;
	}
	public String getMporta() {
		return mporta;
	}
	public void setMporta(String mporta) {
		this.mporta = mporta;
	}
	
	
	
}
