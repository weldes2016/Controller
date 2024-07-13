package br.com.controller.galaxpay.domain;

public class Boleto {
	private String pdf;
	private String bankLine;
	private String bankNumber;
	private String barCode;
	private String bankEmissor;
	private String bankAgency;
	private String bankAccount;
	private String paymentLink;
	private int id_duprec;
	private String galaxPayId;
	private float valor;
	private String cliente;
	private String foneCliente;
	private String email;
	private String payDay;
	private String myId;
	private String customerGalaxPayId;
	
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
	public int getId_duprec() {
		return id_duprec;
	}
	public void setId_duprec(int id_duprec) {
		this.id_duprec = id_duprec;
	}
	public String getGalaxPayId() {
		return galaxPayId;
	}
	public void setGalaxPayId(String galaxPayId) {
		this.galaxPayId = galaxPayId;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getFoneCliente() {
		return foneCliente;
	}
	public void setFoneCliente(String foneCliente) {
		this.foneCliente = foneCliente;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPaymentLink() {
		return paymentLink;
	}
	public void setPaymentLink(String paymentLink) {
		this.paymentLink = paymentLink;
	}
	public String getPayDay() {
		return payDay;
	}
	public void setPayDay(String payDay) {
		this.payDay = payDay;
	}
	public String getMyId() {
		return myId;
	}
	public void setMyId(String myId) {
		this.myId = myId;
	}
	public String getCustomerGalaxPayId() {
		return customerGalaxPayId;
	}
	public void setCustomerGalaxPayId(String customerGalaxPayId) {
		this.customerGalaxPayId = customerGalaxPayId;
	}
		
}
