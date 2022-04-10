/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.constantes;

/**
 *
 * @author Alexandre
 */
public enum StatusNotificacao {
    
    
    AGUARDANDO("Aguardando"),RESOLVIDO("Resolvido");
    
    private String status;

    private StatusNotificacao(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    
    
}
