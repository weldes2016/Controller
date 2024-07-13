package br.com.controller.util;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseModel implements Serializable {

    private static final long serialVersionUID = 3002331522621860077L;

    @JsonProperty
    private String id;

    public String getId() {
        return id;
    }

}