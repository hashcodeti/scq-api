/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.dtos;

import java.util.List;
import java.util.stream.Collectors;

import com.bluebudy.SCQ.domain.Adicao;
import com.bluebudy.SCQ.utils.DateUtils;

/**
 *
 * @author Alexandre
 */
public class AdicaoDTO {

    private Long id;
    private String nomeMp;
    private Long idMp;
    private Double quantidade;
    private String unidade;
    private Boolean status;
    private Long ocpId;
    private String data;

    public AdicaoDTO(Adicao adicao) {
        this.id = adicao.getId();
        this.nomeMp = adicao.getMateriaPrima().getNome();
        this.idMp = adicao.getMateriaPrima().getId();
        this.unidade = adicao.getMateriaPrima().getUnidade().getUnidade();
        this.quantidade = adicao.getQuantidade();
        this.status = adicao.getStatus();
        this.ocpId = adicao.getOrdemDeCorrecao().getId();
        if(adicao.getData() != null){
            this.data = DateUtils.formatToDateTime(adicao.getData());
        } else {
            this.data =  " ";
        }
       
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

    public Long getOcpId() {
        return ocpId;
    }

    public void setOcpId(Long ocpId) {
        this.ocpId = ocpId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getIdMp() {
        return idMp;
    }

    public void setIdMp(Long idMp) {
        this.idMp = idMp;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getNomeMp() {
        return nomeMp;
    }

    public void setNomeMp(String nomeMp) {
        this.nomeMp = nomeMp;
    }

    public static List<AdicaoDTO> adicoesToAdicoesDTO(List<Adicao> adicoes) {
        return adicoes.stream().map(AdicaoDTO::new).collect(Collectors.toList());
    }

}
