package com.africa.springboot.controller;

import java.io.Serializable;

/**
 * Author: StefanChoo
 * Date: 2018/5/11
 */
public class JwtAuthenticationRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
