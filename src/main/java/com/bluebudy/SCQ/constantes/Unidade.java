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
public enum Unidade {
    
    Kg("Kg"),Lts("Lts"),Ml("ml"),Gr("gr"),GramaPorLitro("g/l"),MiligramaPorLitro("mg/l"),Porcentagem("%"),Militro("ml/l"),C("C°"),
    uS("uS"),uM("um"),HV("HV"),ph("pH"),Volts("V"),Ampere("A"),Ppm("ppm"),Pontos("pontos"),Tempo("s");
    
    private final String unidade;

    private Unidade(String tipo) {
        this.unidade = tipo;
    }

    public  String getUnidade() {
        return unidade;
    }
    
    public static Unidade findEnumByValue(String unidade){
            Unidade uni = null;
            for (Unidade u : Unidade.values()) {
            if(u.getUnidade().equals(unidade)){
               uni = u;
         }
       
        }
             return uni ;
    }
        
    
    
    
    
    
    
    
}
