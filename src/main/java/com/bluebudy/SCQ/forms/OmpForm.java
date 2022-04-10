/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.forms;

import com.bluebudy.SCQ.domain.OrdemDeManutencao;
import com.bluebudy.SCQ.utils.DateUtils;
import java.text.ParseException;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Alexandre
 */
public class OmpForm {
    
    @NotNull
    private Long processoId;

    @NotNull
	@NotBlank
    private String programadoPara;

    @NotNull
	@NotBlank
    private String emitidoPor;
    
    private Long[] trocasId;
    private Long[] tarefasId;
    
    
    

    public String getProgramadoPara() {
        return programadoPara;
    }

    public void setProgramadoPara(String programadoPara) {
        this.programadoPara = programadoPara;
    }

    public String getEmitidoPor() {
        return emitidoPor;
    }

    public void setEmitidoPor(String emitidoPor) {
        this.emitidoPor = emitidoPor;
    }

    public Long[] getTrocasId() {
        return trocasId;
    }

    public void setTrocasId(Long[] trocasId) {
        this.trocasId = trocasId;
    }

    public Long getProcessoId() {
        return processoId;
    }

    public void setProcessoId(Long processoId) {
        this.processoId = processoId;
    }

    public Long[] getTarefasId() {
        return tarefasId;
    }

    public void setTarefasId(Long[] tarefasId) {
        this.tarefasId = tarefasId;
    }
    
    
    
    public OrdemDeManutencao generateOmp() throws ParseException{
        
        OrdemDeManutencao omp = new OrdemDeManutencao();
        omp.setEmitidoEm(new Date());
        omp.setProgramadoPara(DateUtils.getDateTimeFromString(this.programadoPara));
        StringBuilder sb = new StringBuilder();
        if(trocasId.length!=0){
             for (Long trocaId : trocasId) {
            sb.append(trocaId);
            sb.append(",");
        }
        sb.setLength(sb.length()-1);
        omp.setTrocasId(sb.toString());
        sb = new StringBuilder();
        }

        if(tarefasId.length!=0) {
        	for(Long tarefaId : tarefasId ){
                sb.append(tarefaId);
                sb.append(",");
            }
        	   sb.setLength(sb.length()-1);
               omp.setTarefasManutencaoId(sb.toString());
        }
        
     
        omp.setNotified(Boolean.FALSE);
        omp.setEmitidoPor(emitidoPor);
        omp.setProcessoId(processoId);
       
        return omp;
    }
   
      

    
    
    
}
