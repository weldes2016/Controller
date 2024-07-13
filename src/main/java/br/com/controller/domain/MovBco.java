package br.com.controller.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class MovBco implements Serializable {

	private static final long serialVersionUID = -4077379755086624886L;

	public int regauto;
	public Double REG;
	public String DEV;
	public String CRE;
	public String SIM;
	public BigDecimal VLR;
	public int Dia;
	public int Mes;
	public int Ano;
	public String Banco;
	public String Ope;
	public String Duplc;
	public String Letra;

	public String CodCli;
	
	
	public String CCusto;
	public String His;
	public Double Regjur;
	public Double Regdes;
	public String Usuario;
	public Date Gravacao;
	public Double Juros;
	
	public int getRegauto() {
		return regauto;
	}
	public void setRegauto(int regauto) {
		this.regauto = regauto;
	}
	public Double getREG() {
		return REG;
	}
	public void setREG(Double rEG) {
		REG = rEG;
	}
	public String getDEV() {
		return DEV;
	}
	public void setDEV(String dEV) {
		DEV = dEV;
	}
	public String getCRE() {
		return CRE;
	}
	public void setCRE(String cRE) {
		CRE = cRE;
	}
	public String getSIM() {
		return SIM;
	}
	public void setSIM(String sIM) {
		SIM = sIM;
	}
	public BigDecimal getVLR() {
		return VLR;
	}
	public void setVLR(BigDecimal vLR) {
		VLR = vLR;
	}
	public int getDia() {
		return Dia;
	}
	public void setDia(int dia) {
		Dia = dia;
	}
	public int getMes() {
		return Mes;
	}
	public void setMes(int mes) {
		Mes = mes;
	}
	public int getAno() {
		return Ano;
	}
	public void setAno(int ano) {
		Ano = ano;
	}
	public String getBanco() {
		return Banco;
	}
	public void setBanco(String banco) {
		Banco = banco;
	}
	public String getOpe() {
		return Ope;
	}
	public void setOpe(String ope) {
		Ope = ope;
	}
	public String getDuplc() {
		return Duplc;
	}
	public void setDuplc(String duplc) {
		Duplc = duplc;
	}
	public String getLetra() {
		return Letra;
	}
	public void setLetra(String letra) {
		Letra = letra;
	}
	public String getCodCli() {
		return CodCli;
	}
	public void setCodCli(String codCli) {
		CodCli = codCli;
	}
	public String getCCusto() {
		return CCusto;
	}
	public void setCCusto(String cCusto) {
		CCusto = cCusto;
	}
	public String getHis() {
		return His;
	}
	public void setHis(String his) {
		His = his;
	}
	public Double getRegjur() {
		return Regjur;
	}
	public void setRegjur(Double regjur) {
		Regjur = regjur;
	}
	public Double getRegdes() {
		return Regdes;
	}
	public void setRegdes(Double regdes) {
		Regdes = regdes;
	}
	public String getUsuario() {
		return Usuario;
	}
	public void setUsuario(String usuario) {
		Usuario = usuario;
	}
	
	
	
	public Date getGravacao() {
		return Gravacao;
	}
	public void setGravacao(Date gravacao) {
		Gravacao = gravacao;
	}
	public Double getJuros() {
		return Juros;
	}
	public void setJuros(Double juros) {
		Juros = juros;
	}
	
}
