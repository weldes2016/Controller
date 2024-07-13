package br.com.controller.galaxpay.domain;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RootBalance {

		public boolean type;
		@JsonProperty("Balances")
		public ArrayList<Balance> balances;
		@JsonProperty("Totals")
		public ArrayList<Totals> totals;
		public boolean isType() {
			return type;
		}
		public void setType(boolean type) {
			this.type = type;
		}
		public ArrayList<Balance> getBalances() {
			return balances;
		}
		public void setBalances(ArrayList<Balance> balances) {
			this.balances = balances;
		}
		public ArrayList<Totals> getTotals() {
			return totals;
		}
		public void setTotals(ArrayList<Totals> totals) {
			this.totals = totals;
		}
		
		
}
