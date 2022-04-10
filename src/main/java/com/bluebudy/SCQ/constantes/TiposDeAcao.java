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
public enum TiposDeAcao {
    
    PREVENTIVA("PREVENTIVA"),CORRETIVA("CORRETIVA");
    
    private final String tipo;

    private TiposDeAcao(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
    
    
    
    
    
}
