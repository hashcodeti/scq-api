package com.bluebudy.SCQ.services;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.bluebudy.SCQ.domain.OrdemDeManutencao;
import com.bluebudy.SCQ.domain.Processo;
import com.bluebudy.SCQ.domain.TarefasDeManutencao;
import com.bluebudy.SCQ.domain.Troca;
import com.bluebudy.SCQ.dtos.OmpChartDto;
import com.bluebudy.SCQ.repository.OmpRepository;
import com.bluebudy.SCQ.repository.ProcessoRepository;
import com.bluebudy.SCQ.repository.TarefasDeManutencaoRepository;
import com.bluebudy.SCQ.repository.TrocaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OmpService {

    @Autowired
    OmpRepository ompRepo;

    @Autowired
    TarefasDeManutencaoRepository tarefasRepo;

    @Autowired
    TrocaRepository trocaRepo;

    @Autowired
    ControleService controleService;

    @Autowired
    ProcessoRepository processoRepo;

    public void updadteTarefaOmp(Long tarefaId) {

    }

    public OmpChartDto loadOmpChart(Long processoId, Date fromDate,  Date toDate) {

        
        Processo processo = processoRepo.findById(processoId).get();
        List<OrdemDeManutencao> ordemDeManutencao = ompRepo.findByProcessoIdAndRealizadoEmBetween(processoId, fromDate, toDate);
        List<TarefasDeManutencao> tarefas = tarefasRepo.findByProcessoId(processoId);
        List<Troca> trocas = trocaRepo.findAll().stream().filter(troca -> troca.getEtapa().getProcesso().getId().equals(processoId)).collect(Collectors.toList());

        
        OmpChartDto ompChartDto = new OmpChartDto();
        
         //Havalia tarefas executadas coletando o que deveria ser trocado e o que foi
        tarefas.stream().forEach(tarefa -> {
            verifyTarefExecution(ompChartDto, ordemDeManutencao, tarefa);
            ompChartDto.setNumberOfTarefasShouldBeExecuted(ompChartDto.getNumberOfTarefasShouldBeExecuted() + controleService.countsShouldHave(tarefa.getControle().getPeriodo(),fromDate, toDate)); 
        });

        //Havalia trocas executadas coletando o que deveria ser trocado e o que foi
        trocas.stream().forEach(troca -> {
            verifyTrocaExecution(ompChartDto, ordemDeManutencao, troca);
            ompChartDto.setNumberOfTrocasShouldBeExecuted(ompChartDto.getNumberOfTrocasShouldBeExecuted() + controleService.countsShouldHave(troca.getControle().getPeriodo(),fromDate, toDate)); 
        });

        ompChartDto.setProcessName(processo.getNome());
        ompChartDto.setProcessId(processo.getId().intValue());
        
        return ompChartDto;
    }


    public Integer verifyTarefExecution(OmpChartDto ompChartDto, List<OrdemDeManutencao> ompI, TarefasDeManutencao tarefa){
        Integer tarefasExecuted = 0;

        for (OrdemDeManutencao omp : ompI) {
            if(omp.getTarefasManutencaoId() != null) {
                String[] tarefaToken = omp.getTarefasManutencaoId().split(",");
                for(String stringPair : Arrays.asList(tarefaToken)){
                    if(stringPair.contains("OK")){
                        String[] pairToken = stringPair.split(":");
                        if((pairToken[1].equals("OK")) && (pairToken[0].equals(tarefa.getId().toString()))){
                            ompChartDto.setNumberOfTarefasExecuted(ompChartDto.getNumberOfTarefasExecuted()+1);
                            tarefasExecuted++;
                        }

                    }
                }
                
            }
           
        }
        return tarefasExecuted;
    }


    public Integer verifyTrocaExecution(OmpChartDto ompChartDto, List<OrdemDeManutencao> ompI, Troca troca){
        Integer trocasExecuted = 0;
        for (OrdemDeManutencao omp : ompI) {
            if(omp.getTrocasId() != null) {
                String[] trocatoken = omp.getTrocasId().split(",");
                for(String stringPair : Arrays.asList(trocatoken)){
                    if(stringPair.contains("OK")){
                        String[] pairToken = stringPair.split(":");
                        if((pairToken[1].equals("OK")) && (pairToken[0].equals(troca.getId().toString()))){
                            ompChartDto.setNumberOfTrocasExecuted(ompChartDto.getNumberOfTrocasExecuted()+1);
                            trocasExecuted++;
                        } 
                    }
                }
                
            }
           
        }
        return trocasExecuted;
    }

}
