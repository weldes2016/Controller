package br.com.controller.galaxpay.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

//import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
//import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
public class Balance {

	@JsonProperty("type")
	public boolean type;

	@JsonProperty("Balances")
	public List<BalanceItem> balances;

	@JsonProperty("Totals")
	public Totals totals;

	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	public List<BalanceItem> getBalances() {
		return balances;
	}

	public void setBalances(List<BalanceItem> balances) {
		this.balances = balances;
	}

	public Totals getTotals() {
		return totals;
	}

	public void setTotals(Totals totals) {
		this.totals = totals;
	}
	
	
}
