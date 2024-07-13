package br.com.controller.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity (name = "RecebimentosJuno")
public class RecebimentosJuno implements Serializable{
	
	private static final long serialVersionUID = 454204821358254628L;

	@Id
	private int Id;
	
	@Column
    private String code;
	
	@Column
	private String reference;
    
	@Column
	private Date dueDate;
	
	@Column 
	private Date pagamentoDate;
	
	@Column
	private String link;

	@Column
    private String checkoutUrl;

	@Column
    private String installmentLink;

	@Column
    private String payNumber;

	@Column
    private BigDecimal amount;

	@Column
	private Date date;
		
	@Column (length = 4)
	private String empresa;
	

	@Column (length = 3)
	private String baixado;
	
	@Column
	private Float fee;
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date date) {
		this.dueDate = date;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCheckoutUrl() {
		return checkoutUrl;
	}

	public void setCheckoutUrl(String checkoutUrl) {
		this.checkoutUrl = checkoutUrl;
	}

	public String getInstallmentLink() {
		return installmentLink;
	}

	public void setInstallmentLink(String installmentLink) {
		this.installmentLink = installmentLink;
	}

	public String getPayNumber() {
		return payNumber;
	}

	public void setPayNumber(String payNumber) {
		this.payNumber = payNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getBaixado() {
		return baixado;
	}

	public void setBaixado(String baixado) {
		this.baixado = baixado;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	

	public Date getPagamentoDate() {
		return pagamentoDate;
	}

	public void setPagamentoDate(Date pagamentoDate) {
		this.pagamentoDate = pagamentoDate;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Float getFee() {
		return fee;
	}

	public void setFee(Float fee) {
		this.fee = fee;
	}

	
	

}
