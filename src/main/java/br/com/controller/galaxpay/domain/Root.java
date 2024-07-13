package br.com.controller.galaxpay.domain;

import java.util.List;

public class Root {

	int totalQtdFoundInPage;
	List<Customers> Customers;
	
	

	public Root() {
	}

	public Root(int totalQtdFoundInPage, List<Customers> Customers) {

		this.totalQtdFoundInPage = totalQtdFoundInPage;

		this.Customers = Customers;

	}

	public void setTotalQtdFoundInPage(int totalQtdFoundInPage) {

		this.totalQtdFoundInPage = totalQtdFoundInPage;

	}

	public int getTotalQtdFoundInPage() {

		return this.totalQtdFoundInPage;

	}

	public void setCustomers(List<Customers> Customers) {

		this.Customers = Customers;

	}

	public List<Customers> getCustomers() {

		return this.Customers;

	}

}