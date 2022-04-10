/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.dtos;


import com.bluebudy.SCQ.domain.OrdemDeCorrecao;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Alexandre
 */
public class OrdemDeCorrecaoDTO {
    
    
    private Long id;

    private String linhaNome;
    private String etapaNome;
    private Double resultado;
   
    private boolean statusCorrecao;
    private String responsavel;
    private boolean statusOCP;
    private String observacao;
    private Double pMax;
    private Double pMin;
    private String mpAdicao;
    private Double qtdAdicao;
    private String unidadeAdicao;



    public OrdemDeCorrecaoDTO(OrdemDeCorrecao ordemDeCorrecao) {
        this.id = ordemDeCorrecao.getId();
        this.responsavel = ordemDeCorrecao.getResponsavel();
        this.statusOCP = ordemDeCorrecao.getStatusOCP();
        this.observacao = ordemDeCorrecao.getObservacao();
        
       
       
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
 
    public String getResponsavel() {
        return responsavel;
    }
    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
    public boolean getStatusCorrecao() {
        return statusCorrecao;
    }
    public void setStatusCorrecao(boolean statusCorrecao) {
        this.statusCorrecao = statusCorrecao;
    }
    public boolean getStatusOCP() {
        return statusOCP;
    }
    public void setStatusOCP(boolean statusOCP) {
        this.statusOCP = statusOCP;
    }
    
    public String getObservacao() {
        return observacao;
    }
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getLinhaNome() {
        return linhaNome;
    }

    public void setLinhaNome(String linhaNome) {
        this.linhaNome = linhaNome;
    }

    public String getEtapaNome() {
        return etapaNome;
    }

    public void setEtapaNome(String etapaNome) {
        this.etapaNome = etapaNome;
    }

    public Double getpMax() {
        return pMax;
    }

    public void setpMax(Double pMax) {
        this.pMax = pMax;
    }

    public Double getpMin() {
        return pMin;
    }

    public void setpMin(Double pMin) {
        this.pMin = pMin;
    }

    public Double getResultado() {
        return resultado;
    }

    public void setResultado(Double resultado) {
        this.resultado = resultado;
    }

    public String getMpAdicao() {
        return mpAdicao;
    }

    public void setMpAdicao(String mpAdicao) {
        this.mpAdicao = mpAdicao;
    }

    public Double getQtdAdicao() {
        return qtdAdicao;
    }

    public void setQtdAdicao(Double qtdAdicao) {
        this.qtdAdicao = qtdAdicao;
    }

    public String getUnidadeAdicao() {
        return unidadeAdicao;
    }

    public void setUnidadeAdicao(String unidadeAdicao) {
        this.unidadeAdicao = unidadeAdicao;
    }
    
    public static List<OrdemDeCorrecaoDTO> ordemToOrdemDTO(List<OrdemDeCorrecao> ordens){
        return ordens.stream().map(OrdemDeCorrecaoDTO :: new).collect(Collectors.toList());
        
    }
}
