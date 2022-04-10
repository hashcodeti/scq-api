/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.dtos;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import com.bluebudy.SCQ.domain.TarefasDeManutencao;
import com.bluebudy.SCQ.utils.DateUtils;

/**
 *
 * @author Alexandre
 */
public class TarefaDTO {

    private Long id;
    private String nome;
    private String codigo;
    private Boolean pendente;
    private Long processoId;
    private Long frequencia;
    private String escalaFrequencia;
    private String dataPlanejada;
    private String dataRealizada;

    public TarefaDTO(TarefasDeManutencao tarefa) {

        this.id = tarefa.getId();
        this.nome = tarefa.getNome();
        this.codigo = tarefa.getCodigoDoDocumento();
        if (tarefa.getControle().getDataPlanejada() == null) {
            this.pendente = false;
            this.dataPlanejada = "1900-01-01";
        } else {
            if (tarefa.getControle().getDataPlanejada().getTime() < Calendar.getInstance().getTime().getTime()) {
                this.pendente = true;
            }
            this.dataPlanejada = DateUtils.formatToDate(tarefa.getControle().getDataPlanejada());
        }

        if(tarefa.getControle().getDataRealizada() != null) {
            this.dataRealizada = DateUtils.formatToDate(tarefa.getControle().getDataRealizada());
        } else {
            this.dataRealizada = "1900-01-01";
        }
        this.processoId = tarefa.getProcesso().getId();
        buildFreequenciaDatas(tarefa);

    }

    public TarefaDTO(TarefasDeManutencao tarefa, boolean status) {

        this.id = tarefa.getId();
        this.nome = tarefa.getNome();
        this.codigo = tarefa.getCodigoDoDocumento();
        this.pendente = !status;
        this.processoId = tarefa.getProcesso().getId();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    

    public String getDataRealizada() {
        return dataRealizada;
    }

    public void setDataRealizada(String dataRealizada) {
        this.dataRealizada = dataRealizada;
    }

    public Boolean getPendente() {
        return pendente;
    }

    public void setPendente(Boolean pendente) {
        this.pendente = pendente;
    }

    public static List<TarefaDTO> tarefasToTarefasDTO(List<TarefasDeManutencao> tarefas) {
        return tarefas.stream().map(TarefaDTO::new).collect(Collectors.toList());

    }

    public Long getProcessoId() {
        return processoId;
    }

    public void setProcessoId(Long processoId) {
        this.processoId = processoId;
    }

    public void buildFreequenciaDatas(TarefasDeManutencao tarefa) {
        boolean assertControl = false;

        Long periodo = tarefa.getControle().getPeriodo();
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

    public String getDataPlanejada() {
        return dataPlanejada;
    }

    public void setDataPlanejada(String dataPlanejada) {
        this.dataPlanejada = dataPlanejada;
    }

}
