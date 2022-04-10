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
public enum EscalaTempo {
    
    Minuto(1),Hora(60),Dia(1440),Mes(43200);
    
    private final Integer unidade;

    private EscalaTempo(Integer Integer) {
        this.unidade = Integer;
    }

    public  Integer getUnidade() {
        
        return unidade;
    }
    

}
