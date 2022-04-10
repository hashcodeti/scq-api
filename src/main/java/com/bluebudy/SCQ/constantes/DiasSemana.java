/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.constantes;

import java.util.Calendar;

/**
 *
 * @author Alexandre
 */
public enum DiasSemana {
    
    Segunda(Calendar.MONDAY),Terca(Calendar.TUESDAY),Quarta(Calendar.WEDNESDAY),Quinta(Calendar.THURSDAY),Sexta(Calendar.FRIDAY),Sabado(Calendar.SATURDAY),Domingo(Calendar.SUNDAY);
    
    private Integer dia;

    private DiasSemana(Integer dia) {
        this.dia = dia;
    }
    
    
}
