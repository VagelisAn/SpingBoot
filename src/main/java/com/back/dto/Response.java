package com.back.dto;

import java.io.Serializable;

public class Response implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private final String role;

    public Response(String jwttoken, String role) {
        this.jwttoken = jwttoken;
        this.role = role;
    }

    public String getToken() {
        return this.jwttoken;
    }

    public String getRole() {
        return this.role;
    }
}