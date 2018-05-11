package com.africa.springboot.controller;

import java.io.Serializable;

/**
 * Author: StefanChoo
 * Date: 2018/5/11
 */
public class JwtAuthenticationResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String token;

    public JwtAuthenticationResponse() {
        super();
    }

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
