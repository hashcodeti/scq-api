/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.forms;

import com.bluebudy.SCQ.constantes.EscalaTempo;
import com.bluebudy.SCQ.customannotation.DataPlanejadaControle;
import com.bluebudy.SCQ.customannotation.EscalaControle;
import com.bluebudy.SCQ.customannotation.FrequenciaControle;
import com.bluebudy.SCQ.domain.Processo;
import com.bluebudy.SCQ.domain.TarefasDeManutencao;
import com.bluebudy.SCQ.utils.DateUtils;
import java.text.ParseException;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Alexandre
 */
public class TarefasDeManutencaoFormEdit {

    private String nome;
    private Long processoId;
    @NotNull @NotBlank @EscalaControle
	private String escala;
	@NotNull
	@FrequenciaControle
	private Integer frequencia;
	@NotNull
	@NotBlank
	@DataPlanejadaControle
	private String dataPlanejada;
   

    private String codigoDoDocumento;

    
    public TarefasDeManutencao generateTarefasForm() throws ParseException {
        Processo processo = new Processo();
        processo.setId(processoId);
        TarefasDeManutencao tarefas = new TarefasDeManutencao();

        tarefas.setCodigoDoDocumento(codigoDoDocumento);
        tarefas.setNome(nome);
     
        tarefas.setProcesso(processo);
        return tarefas;
        
    }
    
    

    public TarefasDeManutencaoFormEdit() {
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

    public String getDataPlanejada() {
        return dataPlanejada;
    }

    public void setDataPlanejada(String dataPlanejada) {
        this.dataPlanejada = dataPlanejada;
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
    
    public Long buildFrequencia(){
        int minutes = EscalaTempo.valueOf(this.escala).getUnidade()*this.frequencia;
        return Duration.ofMinutes(Long.valueOf(minutes)).toMinutes();
    }

    private Date builDataPlanejada() throws ParseException {
        int minutes = EscalaTempo.valueOf(this.escala).getUnidade()*this.frequencia;
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(DateUtils.getDateFromString(this.dataPlanejada)); // sets calendar time/date
        cal.add(Calendar.MINUTE,minutes); // adds one hour
        
        return cal.getTime();
    }
    
    
    
}
