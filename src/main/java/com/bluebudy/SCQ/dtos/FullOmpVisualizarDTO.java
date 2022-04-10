/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.dtos;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.bluebudy.SCQ.domain.OrdemDeManutencao;
import com.bluebudy.SCQ.domain.TarefasDeManutencao;
import com.bluebudy.SCQ.domain.Troca;
import com.bluebudy.SCQ.repository.TarefasDeManutencaoRepository;
import com.bluebudy.SCQ.repository.TrocaRepository;

import org.apache.commons.lang3.math.NumberUtils;

/**
 *
 * @author Alexandre
 */
public class FullOmpVisualizarDTO {
    
    private List<TrocaDTO> trocas = new LinkedList<>();
    private List<TarefaDTO> tarefas = new LinkedList<>();
    private TarefasDeManutencaoRepository tarefasRepo;
    private TrocaRepository trocasRepo;
    private OrdemDeManutencao omp;
    private String data;
    private Long ompId;

    public FullOmpVisualizarDTO(OrdemDeManutencao omp,TarefasDeManutencaoRepository tarefasRepo, TrocaRepository trocasRepo) {
        this.tarefasRepo = tarefasRepo;
        this.trocasRepo = trocasRepo;
        this.omp = omp;
    }
    
    
    public FullOmpVisualizarDTO buildListOmpDetails(){
    	
    	
    	
        String[] trocasIdToken = Optional.ofNullable(omp.getTrocasId()).orElse("").split(",");
        String[] tarefasIdToken = Optional.ofNullable(omp.getTarefasManutencaoId()).orElse("").split(",");
      
    
            for (int i = 0; i < tarefasIdToken.length; i++) {
                String actualId = "";
                boolean tarefaActualStatus = false ;
                if(tarefasIdToken[i].contains(":")){
                    String[] pair = tarefasIdToken[i].split(":");
                    actualId = pair[0];
                    tarefaActualStatus = pair[1].equals("NOK") ? false : true;
                }else {
                    actualId = tarefasIdToken[i];
                }
                if(NumberUtils.isParsable(actualId)){
                   TarefasDeManutencao tarefa = tarefasRepo.findById(Long.valueOf(actualId)).get();
                   TarefaDTO tarefaDto = new TarefaDTO(tarefa,tarefaActualStatus);
                   tarefas.add(tarefaDto);  
                }
               
          }  
       
             for (int j = 0; j < trocasIdToken.length; j++) {
                String actualId = "";
                   boolean trocaActualStatus = false ;
                if(trocasIdToken[j].contains(":")){
                    String[] pair = trocasIdToken[j].split(":");
                    actualId = pair[0];
                    trocaActualStatus = pair[1].equals("NOK") ? false : true;
                } else {
                    actualId = trocasIdToken[j];
                }
                 if(NumberUtils.isParsable(actualId)){
                    Troca troca = trocasRepo.findById(Long.valueOf(actualId)).get();
                    TrocaDTO trocaDto = new TrocaDTO(troca,trocaActualStatus);
                    trocas.add(trocaDto);
                 }
                
          }   
             this.ompId = omp.getId();
             SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
             this.data = sdf.format(omp.getRealizadoEm());
        
        
        return this;
        
    }

    public List<TrocaDTO> getTrocas() {
        return trocas;
    }

    public void setTrocas(List<TrocaDTO> trocas) {
        this.trocas = trocas;
    }

    public List<TarefaDTO> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<TarefaDTO> tarefas) {
        this.tarefas = tarefas;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public Long getOmpId() {
        return ompId;
    }


    public void setOmpId(Long ompId) {
        this.ompId = ompId;
    }

    
   
    
    
    
    
    
    
}
