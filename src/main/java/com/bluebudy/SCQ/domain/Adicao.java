/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bluebudy.SCQ.constantes.Unidade;

import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Alexandre
 */

@Entity
public class Adicao{
   
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment" , strategy = "increment")  
    private Long id;
    
    private Double quantidade;
    @Enumerated(EnumType.STRING)
    private Unidade unidade;
    private Boolean status;
    
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date data;
    
    @ManyToOne
    @JoinColumn(name = "ordem_id" )
    private OrdemDeCorrecao ordemDeCorrecao;
    
    @ManyToOne
    @JoinColumn(name = "materiaPrima_id")
    private MateriaPrima materiaPrima;

    public Adicao() {
    }

    

    public Adicao(Long id, Double quantidade, MateriaPrima materiaPrima,OrdemDeCorrecao ordemDeCorrecao,Unidade unidade,boolean status) {

		this.id = id;
		this.quantidade = quantidade;
		this.unidade = unidade;
		this.ordemDeCorrecao = ordemDeCorrecao;
		this.materiaPrima = materiaPrima;
		this.status = status;
	}



	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    
    public OrdemDeCorrecao getOrdemDeCorrecao() {
        return ordemDeCorrecao;
    }

    public void setOrdemDeCorrecao(OrdemDeCorrecao ordemDeCorrecao) {
        this.ordemDeCorrecao = ordemDeCorrecao;
    }

    public MateriaPrima getMateriaPrima() {
        return materiaPrima;
    }

    public void setMateriaPrima(MateriaPrima materiaPrima) {
        this.materiaPrima = materiaPrima;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

 
    
    
    
    
    
    
    
}
