package br.com.controller.galaxpay.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Totals {

	@JsonProperty("initial")
    public double sdinitial;

    @JsonProperty("final")
    public double sdfinal;
    
}
