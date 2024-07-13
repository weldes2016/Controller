package br.com.controller.galaxpay.domain;

import java.util.Date;

public class Transaction {
	public int galaxPayId;
    public int value;
    public String payday;
    public Date paydayDate;
    public int installment;
    public String status;
    public String statusDescription;
    public String createdAt;
    public String myId;
    public String additionalInfo;
    public int chargeGalaxPayId;
    public String chargeMyId;
    public Boleto boleto;
        
    public int getGalaxPayId() {
		return galaxPayId;
	}
	public void setGalaxPayId(int galaxPayId) {
		this.galaxPayId = galaxPayId;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getPayday() {
		return payday;
	}
	public void setPayday(String payday) {
		this.payday = payday;
	}
	public Date getPaydayDate() {
		return paydayDate;
	}
	public void setPaydayDate(Date paydayDate) {
		this.paydayDate = paydayDate;
	}
	public int getInstallment() {
		return installment;
	}
	public void setInstallment(int installment) {
		this.installment = installment;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusDescription() {
		return statusDescription;
	}
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getMyId() {
		return myId;
	}
	public void setMyId(String myId) {
		this.myId = myId;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public int getChargeGalaxPayId() {
		return chargeGalaxPayId;
	}
	public void setChargeGalaxPayId(int chargeGalaxPayId) {
		this.chargeGalaxPayId = chargeGalaxPayId;
	}
	public String getChargeMyId() {
		return chargeMyId;
	}
	public void setChargeMyId(String chargeMyId) {
		this.chargeMyId = chargeMyId;
	}
	public Boleto getBoleto() {
		return boleto;
	}
	public void setBoleto(Boleto boleto) {
		this.boleto = boleto;
	}
	public Antifraud getAntifraud() {
		return antifraud;
	}
	public void setAntifraud(Antifraud antifraud) {
		this.antifraud = antifraud;
	}
	public Pix getPix() {
		return pix;
	}
	public void setPix(Pix pix) {
		this.pix = pix;
	}
	public Antifraud antifraud;
    public Pix pix;
    
    
    

}
