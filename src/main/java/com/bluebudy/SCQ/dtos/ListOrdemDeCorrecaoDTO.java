/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.dtos;

import java.util.List;
import java.util.stream.Collectors;

import com.bluebudy.SCQ.domain.OrdemDeCorrecao;
import com.bluebudy.SCQ.utils.DateUtils;

/**
 *
 * @author Alexandre
 */
public class ListOrdemDeCorrecaoDTO {

    private Long id;
    private String processoNome;
    private String etapaNome;
    private Double resultado;

    private String responsavel;
    private boolean statusOCP;
    private boolean statusCorrecao;
    private String observacao;
    private String parametroNome;
    private String unidade;
    private Double pMax;
    private Double pMin;

    private List<AdicaoDTO> adicoesDto;

    private Long analiseId;
    private Boolean analiseStatus;

    private Long acaoId;
    private String acao;
    private String prazo;
    private String dataAcao;

    private Boolean isAdicao;

    public ListOrdemDeCorrecaoDTO(OrdemDeCorrecao ocp) {
        this.id = ocp.getId();
        this.processoNome = ocp.getAnalise().getParametro().getEtapa().getProcesso().getNome();
        this.etapaNome = ocp.getAnalise().getParametro().getEtapa().getNome();
        this.resultado = ocp.getAnalise().getResultado();
        this.responsavel = ocp.getResponsavel();
        this.statusOCP = ocp.getStatusOCP();
        this.observacao = ocp.getAnalise().getObservcao() + " : " + ocp.getObservacao();
        this.parametroNome = ocp.getAnalise().getParametro().getNome();
        this.analiseStatus = ocp.getAnalise().getNeedCorrecao();
        this.analiseId = ocp.getAnalise().getId();
        this.pMax = ocp.getAnalise().getParametro().getpMax();
        this.pMin = ocp.getAnalise().getParametro().getpMin();
        this.unidade = ocp.getAnalise().getParametro().getUnidade().getUnidade();
        this.prazo = DateUtils.formatDateTimeLocal(ocp.getAnalise().getData());
        if (ocp.getAcao() == null) {
            this.isAdicao = true;
            if(ocp.getAdicoes().isEmpty()){
                this.statusCorrecao = true;
            } else {
                ocp.getAdicoes().stream().forEach(action -> {
                    this.statusCorrecao = action.getStatus();
                });
            }
           
        } else {
            this.statusCorrecao = ocp.getAcao().getStatus();
        }

        if (ocp.getAcao() != null) {
            this.acaoId = ocp.getAcao().getId();
            this.acao = ocp.getAcao().getAcao();
            this.dataAcao = DateUtils.formatToDateTime(ocp.getAcao().getData());

        }

        adicoesDto = AdicaoDTO.adicoesToAdicoesDTO(ocp.getAdicoes());

    }

    public String getDataAcao() {
        return dataAcao;
    }

    public void setDataAcao(String dataAcao) {
        this.dataAcao = dataAcao;
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

    public String getProcessoNome() {
        return processoNome;
    }

    public void setProcessoNome(String processoNome) {
        this.processoNome = processoNome;
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

    public String getParametroNome() {
        return parametroNome;
    }

    public void setParametroNome(String parametroNome) {
        this.parametroNome = parametroNome;
    }

    public Boolean getAnaliseStatus() {
        return analiseStatus;
    }

    public void setAnaliseStatus(Boolean analiseStatus) {
        this.analiseStatus = analiseStatus;
    }

    public boolean isStatusCorrecao() {
        return statusCorrecao;
    }

    public void setStatusCorrecao(boolean statusCorrecao) {
        this.statusCorrecao = statusCorrecao;
    }

    public Long getAcaoId() {
        return acaoId;
    }

    public void setAcaoId(Long acaoId) {
        this.acaoId = acaoId;
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

    public List<AdicaoDTO> getAdicoesDto() {
        return adicoesDto;
    }

    public void setAdicoesDto(List<AdicaoDTO> adicoesDto) {
        this.adicoesDto = adicoesDto;
    }

    public Long getAnaliseId() {
        return analiseId;
    }

    public void setAnaliseId(Long analiseId) {
        this.analiseId = analiseId;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public Boolean getIsAdicao() {
        return isAdicao;
    }

    public void setIsAdicao(Boolean isAdicao) {
        this.isAdicao = isAdicao;
    }

    public static List<ListOrdemDeCorrecaoDTO> ordemToOrdemDTO(List<OrdemDeCorrecao> ordens) {
        return ordens.stream().sorted().map(ListOrdemDeCorrecaoDTO::new).collect(Collectors.toList());

    }

}
