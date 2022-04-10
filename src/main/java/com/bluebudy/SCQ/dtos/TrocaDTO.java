/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.dtos;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.bluebudy.SCQ.domain.Etapa;
import com.bluebudy.SCQ.domain.MontagemComposition;
import com.bluebudy.SCQ.domain.Troca;
import com.bluebudy.SCQ.repository.EtapaRepository;
import com.bluebudy.SCQ.utils.DateUtils;

/**
 *
 * @author Alexandre
 */

public class TrocaDTO implements Comparable<TrocaDTO> {

    private Long id;
    private Long etapaId;
    private Long processoId;
    private Integer posicao;
    private String etapaNome;
    private String processoNome;
    private String dataPlanejada;
    private String ultimaTroca;
    private Double areaPlanejada;
    private Double areaRealizada;
    private Integer numeroGrupoArea;
    private List<String[]> listaMontagens;
    private Boolean pendente;
    private Boolean programada;

    private Long frequencia;
    private String escalaFrequencia;

    private Etapa etapa;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    
    public TrocaDTO(Troca troca) {
        

        Date date = new Date();
        this.id = troca.getId();
        this.etapaNome = troca.getEtapa().getNome();
        this.processoNome = troca.getEtapa().getProcesso().getNome();
       
        List<MontagemComposition> mcs = troca.getEtapa().getMontagem();
        listaMontagens = new LinkedList<>();
        for (MontagemComposition mc : mcs) {
            String nomeMp = mc.getMateriaPrima().getNome();
            String quantidade = String.valueOf(mc.getQuantidade());
            String unidade = mc.getMateriaPrima().getUnidade().getUnidade();
            String[] pair = { nomeMp, quantidade, unidade };
            listaMontagens.add(pair);
        }
        if(troca.getControle().getDataPlanejada() != null) {
            this.dataPlanejada = DateUtils.formatToDate(troca.getControle().getDataPlanejada());
            if (troca.getControle().getDataPlanejada().getTime() < date.getTime()) {
                this.pendente = true;
            }  else {
                this.pendente = false;
            }
        } else {
            this.dataPlanejada = "1900-01-01";
            this.pendente = false;
        }

        if(troca.getControle().getDataRealizada() != null){
            this.ultimaTroca =  DateUtils.formatToDate(troca.getControle().getDataRealizada());
        } else {
            this.ultimaTroca = "1900-01-01";
        }

        Double planejada = troca.getControle().getAreaPlanejada() == null ?   0.0 : troca.getControle().getAreaPlanejada();
        Double realizada = troca.getControle().getAreaRealizada() == null ?  0.0  : troca.getControle().getAreaRealizada();

        if(realizada > planejada) {
            this.pendente = true;
        }

        this.areaPlanejada = troca.getControle().getAreaPlanejada();
        this.areaRealizada = troca.getControle().getAreaRealizada();
        this.etapaId = troca.getEtapa().getId();
        this.numeroGrupoArea = troca.getControle().getNumeroGrupoArea();
        this.processoId = troca.getEtapa().getProcesso().getId();
        this.posicao = troca.getEtapa().getPosicao();
        buildFreequenciaDatas(troca);
    }

    public TrocaDTO(Troca troca, String action) {
        this.id = troca.getId();
    }

    public TrocaDTO(Troca troca, boolean status) {
        this.id = troca.getId();
        this.etapaNome = troca.getEtapa().getNome();
        this.processoNome = troca.getEtapa().getProcesso().getNome();
        this.dataPlanejada = DateUtils.formatToDate(troca.getControle().getDataPlanejada());
        List<MontagemComposition> mcs = troca.getEtapa().getMontagem();
        listaMontagens = new LinkedList<>();
        for (MontagemComposition mc : mcs) {
            String nomeMp = mc.getMateriaPrima().getNome();
            String quantidade = String.valueOf(mc.getQuantidade());
            String unidade = mc.getMateriaPrima().getUnidade().getUnidade();
            String[] pair = { nomeMp, quantidade, unidade };
            listaMontagens.add(pair);
        }

        this.etapaId = troca.getEtapa().getId();
        this.processoId = troca.getEtapa().getProcesso().getId();
        this.posicao = troca.getEtapa().getPosicao();
        this.pendente = !status;
    }

    public TrocaDTO(Troca troca, EtapaRepository etapaRepo) {

        this.etapa = etapaRepo.findById(troca.getEtapa().getId()).get();

        Date date = new Date();
        this.id = troca.getId();
        this.etapaNome = etapa.getNome();
        this.processoNome = etapa.getProcesso().getNome();
        this.dataPlanejada = DateUtils.formatToDate(troca.getControle().getDataPlanejada());
        List<MontagemComposition> mcs = etapa.getMontagem();
        listaMontagens = new LinkedList<>();
        for (MontagemComposition mc : mcs) {
            String nomeMp = mc.getMateriaPrima().getNome();
            String quantidade = String.valueOf(mc.getQuantidade());
            String unidade = mc.getMateriaPrima().getUnidade().getUnidade();
            String[] pair = { nomeMp, quantidade, unidade };

            listaMontagens.add(pair);
        }

        if (troca.getControle().getDataPlanejada().getTime() < date.getTime()) {
            this.pendente = true;
        } else {
            this.pendente = false;
        }

        this.etapaId = etapa.getId();
        this.processoId = etapa.getProcesso().getId();
        this.posicao = etapa.getPosicao();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEtapaNome() {
        return etapaNome;
    }

    public void setEtapaNome(String etapaNome) {
        this.etapaNome = etapaNome;
    }

    public static List<TrocaDTO> trocaToTrocaDTO(List<Troca> trocas) {
        return trocas.stream().map(TrocaDTO::new).collect(Collectors.toList());

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

    public void setDataPlanejada(String dataPlanejada) {
        this.dataPlanejada = dataPlanejada;
    }

    public String getProcessoNome() {
        return processoNome;
    }

    public void setProcessoNome(String processoNome) {
        this.processoNome = processoNome;
    }

    public List<String[]> getListaMontagens() {
        return listaMontagens;
    }

    public void setListaMontagens(List<String[]> listaMontagens) {
        this.listaMontagens = listaMontagens;
    }

    public Boolean getPendente() {
        return pendente;
    }

    public void setPendente(Boolean pendente) {
        this.pendente = pendente;
    }

    public Long getEtapaId() {
        return etapaId;
    }

    public void setEtapaId(Long etapaId) {
        this.etapaId = etapaId;
    }

    public Long getProcessoId() {
        return processoId;
    }

    public void setProcessoId(Long processoId) {
        this.processoId = processoId;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public Long getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Long frequencia) {
        this.frequencia = frequencia;
    }

    public String getEscalaFrequencia() {
        return escalaFrequencia;
    }

    public void setEscalaFrequencia(String escalaFrequencia) {
        this.escalaFrequencia = escalaFrequencia;
    }

    public Boolean getProgramada() {
        return programada;
    }

    public void setProgramada(Boolean programada) {
        this.programada = programada;
    }

    public void buildFreequenciaDatas(Troca troca) {
        boolean assertControl = false;

        Long periodo = troca.getControle().getPeriodo();
        if ((periodo < 1440) && (periodo % 60 == 0)) {
            assertControl = true;
            this.escalaFrequencia = "Hora";
            this.frequencia = periodo / 60;
        }
        if ((periodo < 43200) && (periodo % 1440 == 0)) {
            assertControl = true;
            this.escalaFrequencia = "Dia";
            this.frequencia = periodo / 1440;
        }
        if (periodo % 43200 == 0) {
            assertControl = true;
            this.escalaFrequencia = "Mes";
            this.frequencia = periodo / 43200;
        }
        if (assertControl == false) {
            this.escalaFrequencia = "Minuto";
            this.frequencia = periodo;
        }

    }

    

    public Double getAreaPlanejada() {
        return areaPlanejada;
    }

    public void setAreaPlanejada(Double areaPlanejada) {
        this.areaPlanejada = areaPlanejada;
    }

    public Double getAreaRealizada() {
        return areaRealizada;
    }

    public void setAreaRealizada(Double areaRealizada) {
        this.areaRealizada = areaRealizada;
    }

    public static DecimalFormat getDf() {
        return df;
    }

    public Etapa getEtapa() {
        return etapa;
    }

    public void setEtapa(Etapa etapa) {
        this.etapa = etapa;
    }

    public String getUltimaTroca() {
        return ultimaTroca;
    }

    public void setUltimaTroca(String ultimaTroca) {
        this.ultimaTroca = ultimaTroca;
    }

    @Override
    public int compareTo(TrocaDTO o) {
        if(this.posicao < o.getPosicao()){
            return -1;
        } else if(this.posicao > o.getPosicao()){
            return 1;
        } else {
            return 0;
        }
        
    }

}
