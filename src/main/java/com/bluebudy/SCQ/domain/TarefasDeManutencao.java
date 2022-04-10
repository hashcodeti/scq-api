/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Alexandre
 */

@Entity
public class TarefasDeManutencao {
    
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;
    private String nome;
    
    @ManyToOne()
    @JoinColumn(name =  "processo_id")
    private Processo processo;
    
    private String codigoDoDocumento;
    
    
    @OneToOne(cascade = CascadeType.ALL)
    private Controle controle;

    public TarefasDeManutencao() {
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

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

  
   

    public Controle getControle() {
		return controle;
	}



	public void setControle(Controle controle) {
		this.controle = controle;
	}



	public String getCodigoDoDocumento() {
        return codigoDoDocumento;
    }

    public void setCodigoDoDocumento(String codigoDoDocumento) {
        this.codigoDoDocumento = codigoDoDocumento;
    }
    
    
    
    
    
    
    
    
}
