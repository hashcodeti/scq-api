/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.forms;

import java.text.ParseException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.bluebudy.SCQ.customannotation.EscalaControle;
import com.bluebudy.SCQ.customannotation.FrequenciaControle;
import com.bluebudy.SCQ.domain.Processo;
import com.bluebudy.SCQ.domain.TarefasDeManutencao;

/**
 *
 * @author Alexandre
 */
public class TarefasDeManutencaoForm {

	@NotBlank @NotNull
    private String nome;
	
	@NotNull
    private Long processoId;
	
	@NotBlank @NotNull
    private String codigoDoDocumento;
	@NotBlank @NotNull
    private String dataExecutada; 
	@NotNull @NotBlank @EscalaControle
	private String escala;
	@NotNull
	@FrequenciaControle
	private Integer frequencia;
    
    
    public TarefasDeManutencao generateTarefasForm() throws ParseException {
       
        Processo processo = new Processo();
        processo.setId(processoId);
        TarefasDeManutencao tarefas = new TarefasDeManutencao();

        tarefas.setCodigoDoDocumento(codigoDoDocumento);
        tarefas.setNome(nome);
       
        tarefas.setProcesso(processo);
        return tarefas;
        
    }
    
    

    public TarefasDeManutencaoForm() {
    }
    
    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getProcessoId() {
        return processoId;
    }

    public void setProcessoId(Long processoId) {
        this.processoId = processoId;
    }



 
    public String getCodigoDoDocumento() {
        return codigoDoDocumento;
    }

    public void setCodigoDoDocumento(String codigoDoDocumento) {
        this.codigoDoDocumento = codigoDoDocumento;
    }

    public String getDataExecutada() {
        return dataExecutada;
    }

    public void setDataExecutada(String dataExecutada) {
        this.dataExecutada = dataExecutada;
    }

   


    public String getEscala() {
		return escala;
	}



	public void setEscala(String escala) {
		this.escala = escala;
	}



	public Integer getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Integer frequencia) {
        this.frequencia = frequencia;
    }
    
   
}
