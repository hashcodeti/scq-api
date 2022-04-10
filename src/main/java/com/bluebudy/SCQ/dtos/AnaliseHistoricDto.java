package com.bluebudy.SCQ.dtos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import com.bluebudy.SCQ.domain.Analise;
import com.bluebudy.SCQ.utils.DateUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class AnaliseHistoricDto {

    private String id;
    private String pMax;
    private String pMin;
    private String pMaxT;
    private String pMinT;
    private String nomeParametro;
    private String nomeEtapa;
    private String nomeProcesso;
    private String valor;
    private String unidade;
    private String processoId;
    private String etapaId;
    private String parametroId;
    private String data;
    private List<ListOrdemDeCorrecaoDTO> ocps;

    public AnaliseHistoricDto(Analise analise) {
        this.id = analise.getId().toString();
        this.pMax = analise.getParametro().getpMax().toString();
        this.pMin = analise.getParametro().getpMin().toString();
        this.pMaxT = analise.getParametro().getpMaxT().toString();
        this.pMinT = analise.getParametro().getpMinT().toString();
        this.nomeParametro = analise.getParametro().getNome().toString();
        this.nomeEtapa = analise.getParametro().getEtapa().getNome().toString();
        this.nomeProcesso = analise.getParametro().getEtapa().getProcesso().getNome().toString();
        this.parametroId = analise.getParametro().getId().toString();
        this.etapaId = analise.getParametro().getEtapa().getId().toString();
        this.processoId = analise.getParametro().getEtapa().getProcesso().getId().toString();
        this.valor = new BigDecimal(analise.getResultado()).setScale(2, RoundingMode.HALF_UP).toString();
        this.unidade = analise.getParametro().getUnidade().getUnidade();
        this.data = DateUtils.formatToDateTime(analise.getData());
        this.ocps = ListOrdemDeCorrecaoDTO.ordemToOrdemDTO(analise.getOrdemDeCorrecao());

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpMax() {
        return pMax;
    }

    public void setpMax(String pMax) {
        this.pMax = pMax;
    }

    public String getpMin() {
        return pMin;
    }

    public void setpMin(String pMin) {
        this.pMin = pMin;
    }

    public String getpMaxT() {
        return pMaxT;
    }

    public void setpMaxT(String pMaxT) {
        this.pMaxT = pMaxT;
    }

    public String getpMinT() {
        return pMinT;
    }

    public void setpMinT(String pMinT) {
        this.pMinT = pMinT;
    }

    

    public String getNomeParametro() {
        return nomeParametro;
    }

    public void setNomeParametro(String nomeParametro) {
        this.nomeParametro = nomeParametro;
    }

    public String getNomeEtapa() {
        return nomeEtapa;
    }

    public void setNomeEtapa(String nomeEtapa) {
        this.nomeEtapa = nomeEtapa;
    }

    public String getNomeProcesso() {
        return nomeProcesso;
    }

    public void setNomeProcesso(String nomeProcesso) {
        this.nomeProcesso = nomeProcesso;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getProcessoId() {
        return processoId;
    }

    public void setProcessoId(String processoId) {
        this.processoId = processoId;
    }

    public String getEtapaId() {
        return etapaId;
    }

    public void setEtapaId(String etapaId) {
        this.etapaId = etapaId;
    }

    public String getParametroId() {
        return parametroId;
    }

    public void setParametroId(String parametroId) {
        this.parametroId = parametroId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    
    public List<ListOrdemDeCorrecaoDTO> getOcps() {
        return ocps;
    }

    public void setOcps(List<ListOrdemDeCorrecaoDTO> ocps) {
        this.ocps = ocps;
    }

    public static List<AnaliseHistoricDto> analiseToAnaliseHitoricDto(List<Analise> analises) {
        return analises.stream().map(AnaliseHistoricDto::new).collect(Collectors.toList());

    }

    public static Page<AnaliseHistoricDto> analiseToAnaliseHitoricDto(Page<Analise> analises,Pageable page) {
        return new PageImpl<AnaliseHistoricDto>(analises.stream().map(AnaliseHistoricDto::new).collect(Collectors.toList()),page,analises.getSize());

    }
}
