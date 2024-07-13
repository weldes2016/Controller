package br.com.controller.galaxpay.domain;

public class RetornoGalaxPay {
	private String pdf;
	private String bankLine;
	private String bankNumber;
	private String barCode;
	private String bankEmissor;
	private String bankAgency;
	private String bankAccount;
	public String getPdf() {
		return pdf;
	}
	public void setPdf(String pdf) {
		this.pdf = pdf;
	}
	public String getBankLine() {
		return bankLine;
	}
	public void setBankLine(String bankLine) {
		this.bankLine = bankLine;
	}
	public String getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getBankEmissor() {
		return bankEmissor;
	}
	public void setBankEmissor(String bankEmissor) {
		this.bankEmissor = bankEmissor;
	}
	public String getBankAgency() {
		return bankAgency;
	}
	public void setBankAgency(String bankAgency) {
		this.bankAgency = bankAgency;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	
}
