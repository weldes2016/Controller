package br.com.controller.galaxpay.domain;

import java.util.List;

public class GalaxPayDuprec {

	private String myId;
	private float value;
	private String additionalInfo;
	private String payday;
	private GalaxPayClifor customer = new GalaxPayClifor();
	private List<RetornoGalaxPay> boletos;
	
	
	
	public List<RetornoGalaxPay> getBoletos() {
		return boletos;
	}

	public void setBoletos(List<RetornoGalaxPay> boletos) {
		this.boletos = boletos;
	}

	public String getMyId() {
		return myId;
	}

	public void setMyId(String myId) {
		this.myId = myId;
	}

	

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getPayday() {
		return payday;
	}

	public void setPayday(String payday) {
		this.payday = payday;
	}

	public GalaxPayClifor getCustomer() {
		return customer;
	}

	public void setCustomer(GalaxPayClifor customer) {
		this.customer = customer;
	}

}
