package br.com.controller.galaxpay.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Address {

	@JsonProperty("zipCode")
	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	String zipCode;

	@JsonProperty("street")
	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	String street;

	@JsonProperty("number")
	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	String number;

	@JsonProperty("complement")
	public String getComplement() {
		return this.complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	String complement;

	@JsonProperty("neighborhood")
	public String getNeighborhood() {
		return this.neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	String neighborhood;

	@JsonProperty("city")
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	String city;

	@JsonProperty("state")
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	String state;
}
