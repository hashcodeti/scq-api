/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.dtos;

import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.bluebudy.SCQ.domain.OrdemDeManutencao;
import com.bluebudy.SCQ.domain.Processo;
import com.bluebudy.SCQ.repository.ProcessoRepository;


/**
 *
 * @author Alexandre
 */
public class OrdemDeManutencaoDTO {

   
    
    private Long id;
    private String nomeProcesso;
    private String dataPlanejada;
    private String dataRealizada;
    private String emitidoPor;
    private String type;
    private ProcessoRepository processoRepo;
    private Long processoId;
    private String status;

    public OrdemDeManutencaoDTO(List<OrdemDeManutencao> omps, ProcessoRepository pRepo) {
        this.processoRepo = pRepo;
        
    }
    public OrdemDeManutencaoDTO(OrdemDeManutencao omp, ProcessoRepository pRepo) {
       Processo processo = pRepo.findById(omp.getProcessoId()).get();
        this.id = omp.getId();
        this.nomeProcesso = processo.getNome();
        this.dataPlanejada = getTranformedTrocaDate(omp.getProgramadoPara());
        this.dataRealizada = getTranformedTrocaDate(omp.getRealizadoEm() == null ? omp.getProgramadoPara() : omp.getRealizadoEm());
        this.processoId = processo.getId();
        if(omp.getTrocasId() == null || omp.getTrocasId().length() == 0) {
        	this.type = "tarefas";
        } else {
        	 this.type = "limpeza";
        }
        
        
        
        if( Optional.ofNullable(omp.getRealizadoEm()).isEmpty()){
            if(omp.getProgramadoPara().getTime()<Calendar.getInstance().getTime().getTime()-(ChronoUnit.DAYS.getDuration().toMillis()/2)){
                this.status = "atrasado:red";
            } else {
                this.status = "pendente:orange";
            }
         
            
        } else {
            this.status = "concluido:green";
        }
        
        
        this.emitidoPor = omp.getEmitidoPor();
     

        
    }

    
    
    public Long getProcessoId() {
        return processoId;
    }
    public void setProcessoId(Long processoId) {
        this.processoId = processoId;
    }
    public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public OrdemDeManutencaoDTO setBuild(OrdemDeManutencao omp){
        Processo processo = processoRepo.findById(omp.getProcessoId()).get();
        this.id = omp.getId();
        this.nomeProcesso = processo.getNome();
        this.dataPlanejada = getTranformedTrocaDate(omp.getProgramadoPara());
        long dataAtual = Calendar.getInstance().getTime().getTime();
        if(omp.getProgramadoPara().getTime()<dataAtual){
         this.status = "atrasado";
            
        } else {
            this.status = "pendente";
        }
        
        
        this.emitidoPor = omp.getEmitidoPor();
     
        return this;
}
    
    
    public String getTranformedTrocaDate(Date date){
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
           return sdf.format(date);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeProcesso() {
        return nomeProcesso;
    }

    public void setNomeProcesso(String nomeProcesso) {
        this.nomeProcesso = nomeProcesso;
    }

    public String getDataPlanejada() {
        return dataPlanejada;
    }

    public void setDataPlanejada(String dataPlanejada) {
        this.dataPlanejada = dataPlanejada;
    }

    public String getEmitidoPor() {
        return emitidoPor;
    }

    public void setEmitidoPor(String emitidoPor) {
        this.emitidoPor = emitidoPor;
    }
    
     public static  List<OrdemDeManutencaoDTO> ordemToOrdemDTO(List<OrdemDeManutencao> omps,ProcessoRepository processoRepo) {
         List<OrdemDeManutencaoDTO> ordens = new LinkedList<>();
         for (OrdemDeManutencao omp : omps) {
             OrdemDeManutencaoDTO ompDto = new OrdemDeManutencaoDTO(omp, processoRepo);
             ordens.add(ompDto);
         }
        return ordens;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getDataRealizada() {
        return dataRealizada;
    }
    public void setDataRealizada(String dataRealizada) {
        this.dataRealizada = dataRealizada;
    }
    

    
     
    
    
}
