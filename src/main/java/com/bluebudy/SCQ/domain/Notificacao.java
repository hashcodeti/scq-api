/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.domain;

import com.bluebudy.SCQ.constantes.StatusNotificacao;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Alexandre
 */

@Entity
public class Notificacao {
    
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment" , strategy = "increment")  
    protected Long id;
    
    protected StatusNotificacao status;
    protected String messagem;
    protected String tipoNotificacao;
    protected Long refId;

    

    public Notificacao() {
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusNotificacao getStatus() {
        return status;
    }

    public void setStatus(StatusNotificacao status) {
        this.status = status;
    }

    public String getMessagem() {
        return messagem;
    }

    public void setMessagem(String messagem) {
        this.messagem = messagem;
    }

    public Long getRefId() {
        return refId;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }


	public String getTipoNotificacao() {
		return tipoNotificacao;
	}


	public void setTipoNotificacao(String tipoNotificacao) {
		this.tipoNotificacao = tipoNotificacao;
	}
    
    
    

    
}
