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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Alexandre
 */
@Entity
public class OrdemDeManutencao {
    
    @Id	  
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment" , strategy = "increment")
    private Long id;
    
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date emitidoEm;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date programadoPara;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date realizadoEm;
    private Boolean notified;
    private String emitidoPor;
    private Long processoId;
    private String trocasId;
    private String tarefasManutencaoId;
    
    
    public OrdemDeManutencao() {
    }
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEmitidoEm() {
        return emitidoEm;
    }

    public void setEmitidoEm(Date emitidoEm) {
        this.emitidoEm = emitidoEm;
    }

    public Date getProgramadoPara() {
        return programadoPara;
    }

    public void setProgramadoPara(Date programadoPara) {
        this.programadoPara = programadoPara;
    }

    public String getEmitidoPor() {
        return emitidoPor;
    }

    public void setEmitidoPor(String emitidoPor) {
        this.emitidoPor = emitidoPor;
    }

    public String getTrocasId() {
        return trocasId;
    }

    public void setTrocasId(String trocasId) {
        this.trocasId = trocasId;
    }

    public Date getRealizadoEm() {
        return realizadoEm;
    }

    public void setRealizadoEm(Date realizadoEm) {
        this.realizadoEm = realizadoEm;
    }

    public Boolean getNotified() {
        return notified;
    }

    public void setNotified(Boolean notified) {
        this.notified = notified;
    }

    public Long getProcessoId() {
        return processoId;
    }

    public void setProcessoId(Long processoId) {
        this.processoId = processoId;
    }

    public String getTarefasManutencaoId() {
        return tarefasManutencaoId;
    }

    public void setTarefasManutencaoId(String tarefasManutencaoId) {
        this.tarefasManutencaoId = tarefasManutencaoId;
    }
    
    
    
    
    
    

   
    
    
    
    
}
