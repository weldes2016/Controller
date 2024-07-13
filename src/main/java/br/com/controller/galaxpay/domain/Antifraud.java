package br.com.controller.galaxpay.domain;

public class Antifraud {
	
	public Object ip;
    public Object sessionId;
    public boolean sent;
    public Object approved;
	public Object getIp() {
		return ip;
	}
	public void setIp(Object ip) {
		this.ip = ip;
	}
	public Object getSessionId() {
		return sessionId;
	}
	public void setSessionId(Object sessionId) {
		this.sessionId = sessionId;
	}
	public boolean isSent() {
		return sent;
	}
	public void setSent(boolean sent) {
		this.sent = sent;
	}
	public Object getApproved() {
		return approved;
	}
	public void setApproved(Object approved) {
		this.approved = approved;
	}
    

}
