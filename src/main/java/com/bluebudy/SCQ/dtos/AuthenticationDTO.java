/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.dtos;

/**
 *
 * @author Alexandre
 */
public class AuthenticationDTO {
    
    private String token;
    private String tipo;
    private String userRole;
    private String userName;

    public AuthenticationDTO(String token, String tipo, String userRole, String userName) {
        this.token = token;
        this.tipo = tipo;
        this.userRole = userRole;
        this.userName = userName;
    }

    
    
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
    
}
