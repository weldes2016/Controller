/*
   Copyright 2009-2022 PrimeTek.

   Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.primefaces.rain.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class Customer implements Serializable {

    private int id;
    private String name;
    private Country country;
    private LocalDate date;
    private CustomerStatus status;
    private int activity;
    private Representative representative;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public CustomerStatus getStatus() {
		return status;
	}
	public void setStatus(CustomerStatus status) {
		this.status = status;
	}
	public int getActivity() {
		return activity;
	}
	public void setActivity(int activity) {
		this.activity = activity;
	}
	public Representative getRepresentative() {
		return representative;
	}
	public void setRepresentative(Representative representative) {
		this.representative = representative;
	}

 
}
