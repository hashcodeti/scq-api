/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.domain;


import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Alexandre
 */

@Entity
public class Acao{
    
    @Id	  
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment" , strategy = "increment")  
    private Long id;
    private String acao;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date prazo;

    
    private Boolean status;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date data;
   
    private boolean notified;
    
    
    @OneToOne
    @JoinColumn(name = "ordem_id")
    private OrdemDeCorrecao ordemDeCorrecao;

    public Acao(){
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public Date getPrazo() {
        return prazo;
    }

    public void setPrazo(Date prazo) {
        this.prazo = prazo;
    }

    

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public OrdemDeCorrecao getOrdemDeCorrecao() {
        return ordemDeCorrecao;
    }

    public void setOrdemDeCorrecao(OrdemDeCorrecao ordemDeCorrecao) {
        this.ordemDeCorrecao = ordemDeCorrecao;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
