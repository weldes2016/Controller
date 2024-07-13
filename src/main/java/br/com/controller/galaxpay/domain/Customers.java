package br.com.controller.galaxpay.domain;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customers {
	@JsonProperty("myId") 
    public String getMyId() { 
		 return this.myId; } 
    public void setMyId(String myId) { 
		 this.myId = myId; } 
    String myId;
    @JsonProperty("galaxPayId") 
    public int getGalaxPayId() { 
		 return this.galaxPayId; } 
    public void setGalaxPayId(int galaxPayId) { 
		 this.galaxPayId = galaxPayId; } 
    int galaxPayId;
    @JsonProperty("name") 
    public String getName() { 
		 return this.name; } 
    public void setName(String name) { 
		 this.name = name; } 
    String name;
    @JsonProperty("document") 
    public String getDocument() { 
		 return this.document; } 
    public void setDocument(String document) { 
		 this.document = document; } 
    String document;
    @JsonProperty("createdAt") 
    public String getCreatedAt() { 
		 return this.createdAt; } 
    public void setCreatedAt(String createdAt) { 
		 this.createdAt = createdAt; } 
    String createdAt;
    @JsonProperty("updatedAt") 
    public String getUpdatedAt() { 
		 return this.updatedAt; } 
    public void setUpdatedAt(String updatedAt) { 
		 this.updatedAt = updatedAt; } 
    String updatedAt;
    @JsonProperty("status") 
    public String getStatus() { 
		 return this.status; } 
    public void setStatus(String status) { 
		 this.status = status; } 
    String status;
    @JsonProperty("emails") 
    public ArrayList<String> getEmails() { 
		 return this.emails; } 
    public void setEmails(ArrayList<String> emails) { 
		 this.emails = emails; } 
    ArrayList<String> emails;
    @JsonProperty("phones") 
    public ArrayList<String> getPhones() { 
		 return this.phones; } 
    public void setPhones(ArrayList<String> phones) { 
		 this.phones = phones; } 
    ArrayList<String> phones;
    @JsonProperty("Address") 
    public Address getAddress() { 
		 return this.address; } 
    public void setAddress(Address address) { 
		 this.address = address; } 
    Address address;
    @JsonProperty("ExtraFields") 
    public ArrayList<Object> getExtraFields() { 
		 return this.extraFields; } 
    public void setExtraFields(ArrayList<Object> extraFields) { 
		 this.extraFields = extraFields; } 
    ArrayList<Object> extraFields;

}
