/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.dtos;

import com.bluebudy.SCQ.domain.OrdemDeManutencao;
import com.bluebudy.SCQ.domain.TarefasDeManutencao;
import com.bluebudy.SCQ.domain.Troca;
import com.bluebudy.SCQ.repository.TarefasDeManutencaoRepository;
import com.bluebudy.SCQ.repository.TrocaRepository;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.math.NumberUtils;

/**
 *
 * @author Alexandre
 */
public class FullOmpDetailDTO {
    
    private List<TrocaDTO> trocas = new LinkedList<>();
    private List<TarefaDTO> tarefas = new LinkedList<>();
    private TarefasDeManutencaoRepository tarefasRepo;
    private TrocaRepository trocasRepo;
    private OrdemDeManutencao omp;
    private Date data;

    public FullOmpDetailDTO(OrdemDeManutencao omp,TarefasDeManutencaoRepository tarefasRepo, TrocaRepository trocasRepo) {
        this.tarefasRepo = tarefasRepo;
        this.trocasRepo = trocasRepo;
        this.omp = omp;
    }
    
    
    public FullOmpDetailDTO buildListOmpDetails(){
        String[] trocasIdToken = {};
        if(omp.getTrocasId()!=null){
             trocasIdToken = omp.getTrocasId().split(",");
              for (int j = 0; j < trocasIdToken.length; j++) {
                String actualId = "";
                if(trocasIdToken[j].contains(":")){
                    String[] pair = trocasIdToken[j].split(":");
                    actualId = pair[0];
                } else {
                    actualId = trocasIdToken[j];
                }
                 if(NumberUtils.isParsable(actualId)){
                    Troca troca = trocasRepo.findById(Long.valueOf(actualId)).get();
                    TrocaDTO trocaDto = new TrocaDTO(troca);
                    trocas.add(trocaDto);
                 }
                
          }   
        }
       
        if(omp.getTarefasManutencaoId()!=null) {
        	 String[] tarefasIdToken = omp.getTarefasManutencaoId().split(",");
        	  for (int i = 0; i < tarefasIdToken.length; i++) {
                  String actualId = "";
                  if(tarefasIdToken[i].contains(":")){
                      String[] pair = tarefasIdToken[i].split(":");
                      actualId = pair[0];
                  }else {
                      actualId = tarefasIdToken[i];
                  }
                  if(NumberUtils.isParsable(actualId)){
                     TarefasDeManutencao tarefa = tarefasRepo.findById(Long.valueOf(actualId)).get();
                     TarefaDTO tarefaDto = new TarefaDTO(tarefa);
                     tarefas.add(tarefaDto);  
                  }
                 
            }  
        }
       
     
          
       
            
             this.data = omp.getRealizadoEm();
        
        
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    
    
    
    
    
    
}
