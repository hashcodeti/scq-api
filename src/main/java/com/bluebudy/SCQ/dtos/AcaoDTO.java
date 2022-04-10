/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.dtos;

import com.bluebudy.SCQ.domain.Acao;
import com.bluebudy.SCQ.utils.DateUtils;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Alexandre
 */
public class AcaoDTO {

    
    private Long id;
    private String acao;
    private String prazo;
    private String responsavel;
    private boolean status;
    
    
    public AcaoDTO(Acao acao){
        this.id = acao.getId();
        this.acao = acao.getAcao();
        this.prazo = DateUtils.formatToDateToTime(acao.getPrazo());
        this.responsavel = acao.getOrdemDeCorrecao().getResponsavel();
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

   
    public String getPrazo() {
        return prazo;
    }

    public void setPrazo(String prazo) {
        this.prazo = prazo;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

   

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    
    public static List<AcaoDTO> acoesToAcoesDTO(List<Acao> acoes){
        return acoes.stream().map(AcaoDTO::new).collect(Collectors.toList());
    }
    
    
    
}
