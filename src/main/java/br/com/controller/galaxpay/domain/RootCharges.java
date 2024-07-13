package br.com.controller.galaxpay.domain;

import java.util.List;

public class RootCharges {

	int totalQtdFoundInPage;
	List<Charge> Charges;
	

	public int getTotalQtdFoundInPage() {
		return totalQtdFoundInPage;
	}
	public void setTotalQtdFoundInPage(int totalQtdFoundInPage) {
		this.totalQtdFoundInPage = totalQtdFoundInPage;
	}
	public List<Charge> getCharges() {
		return Charges;
	}
	public void setCharges(List<Charge> charges) {
		Charges = charges;
	}
	
	

}
