/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.dtos;

import com.bluebudy.SCQ.domain.MateriaPrima;

/**
 *
 * @author Alexandre
 */
public class MateriaPrimaSearchDTO {
    
    private Long id;
    private String nome;

    public MateriaPrimaSearchDTO(MateriaPrima materiaPrima) {
        this.id = materiaPrima.getId();
        this.nome = materiaPrima.getNome();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
    
    
    
    
}
