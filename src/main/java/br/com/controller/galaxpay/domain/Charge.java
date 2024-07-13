package br.com.controller.galaxpay.domain;

import java.util.ArrayList;

import org.primefaces.rain.domain.Customer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Charge {
	public String myId;
	public int galaxPayId;
	public float value;
	public String paymentLink;
	public String mainPaymentMethodId;
	public String status;
	public String additionalInfo;
	public String createdAt;
	public String updatedAt;
	public boolean payedOutsideGalaxPay;
	public Customer customer;
	public ArrayList<Transaction> transactions;
	public ArrayList<Object> extraFields;
	public PaymentMethodBoleto paymentMethodBoleto;
	
	public String getMyId() {
		return myId;
	}
	public void setMyId(String myId) {
		this.myId = myId;
	}
	public int getGalaxPayId() {
		return galaxPayId;
	}
	public void setGalaxPayId(int galaxPayId) {
		this.galaxPayId = galaxPayId;
	}
	
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public String getPaymentLink() {
		return paymentLink;
	}
	public void setPaymentLink(String paymentLink) {
		this.paymentLink = paymentLink;
	}
	public String getMainPaymentMethodId() {
		return mainPaymentMethodId;
	}
	public void setMainPaymentMethodId(String mainPaymentMethodId) {
		this.mainPaymentMethodId = mainPaymentMethodId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public boolean isPayedOutsideGalaxPay() {
		return payedOutsideGalaxPay;
	}
	public void setPayedOutsideGalaxPay(boolean payedOutsideGalaxPay) {
		this.payedOutsideGalaxPay = payedOutsideGalaxPay;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
	}
	public ArrayList<Object> getExtraFields() {
		return extraFields;
	}
	public void setExtraFields(ArrayList<Object> extraFields) {
		this.extraFields = extraFields;
	}
	public PaymentMethodBoleto getPaymentMethodBoleto() {
		return paymentMethodBoleto;
	}
	public void setPaymentMethodBoleto(PaymentMethodBoleto paymentMethodBoleto) {
		this.paymentMethodBoleto = paymentMethodBoleto;
	}
	
	

}
