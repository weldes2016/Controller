package br.com.controller.galaxpay.domain;

public class PaymentMethodBoleto {
	public int fine;
	public int interest;
	public String instructions;
	public int deadlineDays;
	public int getFine() {
		return fine;
	}
	public void setFine(int fine) {
		this.fine = fine;
	}
	public int getInterest() {
		return interest;
	}
	public void setInterest(int interest) {
		this.interest = interest;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	public int getDeadlineDays() {
		return deadlineDays;
	}
	public void setDeadlineDays(int deadlineDays) {
		this.deadlineDays = deadlineDays;
	}
	
	
}
