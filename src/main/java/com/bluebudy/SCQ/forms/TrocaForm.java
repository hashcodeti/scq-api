/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.bluebudy.SCQ.customannotation.AreaPlanejadaControle;
import com.bluebudy.SCQ.customannotation.DataPlanejadaControle;
import com.bluebudy.SCQ.customannotation.EscalaControle;
import com.bluebudy.SCQ.customannotation.FrequenciaControle;
import com.bluebudy.SCQ.customannotation.NumeroGrupoAreaControle;
import com.bluebudy.SCQ.domain.Etapa;
import com.bluebudy.SCQ.domain.Troca;

/**
 *
 * @author Alexandre
 */
public class TrocaForm  {

	

	

	@NotNull 
    private Long etapaId;
	
	@NotNull @NotBlank @EscalaControle
	private String escala;
	@NotNull
	@FrequenciaControle
	private Integer frequencia;
	@NotNull
	@NotBlank
	@DataPlanejadaControle
	private String dataPlanejada;

	@AreaPlanejadaControle
	private Double areaPlanejada;
   
	@NumeroGrupoAreaControle
	private Integer numeroGrupoArea;



    

	public Double getAreaPlanejada() {
		return areaPlanejada;
	}

	public void setAreaPlanejada(Double areaPlanejada) {
		this.areaPlanejada = areaPlanejada;
	}

	public Integer getNumeroGrupoArea() {
		return numeroGrupoArea;
	}

	public void setNumeroGrupoArea(Integer numeroGrupoArea) {
		this.numeroGrupoArea = numeroGrupoArea;
	}

	public String getDataPlanejada() {
        return dataPlanejada;
    }

    public Integer getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Integer frequencia) {
        this.frequencia = frequencia;
    }


    public Long getEtapaId() {
        return etapaId;
    }

    
    
    public String getEscala() {
		return escala;
	}

	public void setEscala(String escala) {
		this.escala = escala;
	}

	public void setEtapaId(Long etapaId) {
		this.etapaId = etapaId;
	}

	public void setDataPlanejada(String dataPlanejada) {
		this.dataPlanejada = dataPlanejada;
	}

	public Troca generateTroca() {
        Troca troca = new Troca();
        Etapa etapa = new Etapa();
        etapa.setId(etapaId);
        troca.setEtapa(etapa);
        return troca;
    }


    

}
