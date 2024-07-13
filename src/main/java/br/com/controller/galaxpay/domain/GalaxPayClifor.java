package br.com.controller.galaxpay.domain;

import java.io.Serializable;

public class GalaxPayClifor implements Serializable{

	private String myId;
	private String name;
	private String document;
	private GalaxPayEmails emails;
	private String status;
	
	public String getMyId() {
		return myId;
	}
	public void setMyId(String myId) {
		this.myId = myId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public GalaxPayEmails getEmails() {
		return emails;
	}
	public void setEmails(GalaxPayEmails emails) {
		this.emails = emails;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
