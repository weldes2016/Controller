package br.com.controller.galaxpay.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BalanceItem {
	
	@JsonProperty("galaxPayId")
    public int galaxPayId;

    @JsonProperty("value")
    public double value;

    @JsonProperty("friendlyDescription")
    public String friendlyDescription;

    @JsonProperty("createdAt")
    public String createdAt;

    @JsonProperty("groupPaymentType")
    public String groupPaymentType;

    @JsonProperty("paymentType")
    public String paymentType;

    @JsonProperty("transactionGalaxPayId")
    public Integer transactionGalaxPayId;
    
    public Double valorAcumulado;
     

	public int getGalaxPayId() {
		return galaxPayId;
	}

	public void setGalaxPayId(int galaxPayId) {
		this.galaxPayId = galaxPayId;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getFriendlyDescription() {
		return friendlyDescription;
	}

	public void setFriendlyDescription(String friendlyDescription) {
		this.friendlyDescription = friendlyDescription;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getGroupPaymentType() {
		return groupPaymentType;
	}

	public void setGroupPaymentType(String groupPaymentType) {
		this.groupPaymentType = groupPaymentType;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getTransactionGalaxPayId() {
		return transactionGalaxPayId;
	}

	public void setTransactionGalaxPayId(Integer transactionGalaxPayId) {
		this.transactionGalaxPayId = transactionGalaxPayId;
	}

	public Double getValorAcumulado() {
		return valorAcumulado;
	}

	public void setValorAcumulado(Double valorAcumulado) {
		this.valorAcumulado = valorAcumulado;
	}
    
    

}
