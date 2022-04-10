/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.controllers;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import com.bluebudy.SCQ.doc.OcpDocumentBuilder;
import com.bluebudy.SCQ.domain.Adicao;
import com.bluebudy.SCQ.dtos.AdicaoDTO;
import com.bluebudy.SCQ.forms.AdicaoForm;
import com.bluebudy.SCQ.repository.AdicaoRepository;
import com.bluebudy.SCQ.repository.MateriaPrimaRepository;
import com.bluebudy.SCQ.repository.OrdemDeCorrecaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Alexandre
 */
@RestController

public class AdicaoController {

    @Autowired
    private AdicaoRepository repository;
    
    @Autowired
    private OrdemDeCorrecaoRepository ocpRepo;
    
    @Autowired
    private MateriaPrimaRepository mpRepo;

    AdicaoController(AdicaoRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    @GetMapping("/adicoes")
    List<AdicaoDTO> all() {
        return AdicaoDTO.adicoesToAdicoesDTO(repository.findAll());
    }

    @PostMapping(path = "/adicao")
    public ResponseEntity<String> newAdicao(@RequestBody List<AdicaoForm> adicaoforms, UriComponentsBuilder builder) throws InterruptedException, ExecutionException {
        List<Adicao> adicoes = adicaoforms.stream().map(adicaoform -> repository.saveAndFlush(adicaoform.generateAdicao())).collect(Collectors.toList());
        
        
        OcpDocumentBuilder ocpBuilder = new OcpDocumentBuilder(adicoes,ocpRepo,mpRepo);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<String> callable = () -> {
            return ocpBuilder.buildDocument();
        };

        Future<String> future = executorService.submit(callable);

        executorService.shutdown();
        String fileName = future.get();
        
        return ResponseEntity.ok(fileName);
        
   
    }

    // Single item
    @GetMapping("/adicao/{id}")
    AdicaoDTO one(@PathVariable Long id) {
        return new AdicaoDTO(repository.findById(id).get());
    }

    @PutMapping("/adicao/corrigir/{id}")
    ResponseEntity<?> aprovaAdicao(@PathVariable Long id) {
        List<Adicao> adicoes =  repository.findByOrdemDeCorrecaoId(id);
        adicoes.stream().forEach(adicao -> {
            adicao.setStatus(true);
            adicao.setData(Calendar.getInstance().getTime());
            repository.saveAndFlush(adicao);
            
        });
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/adicao/{id}")
    void deleteAdicao(@PathVariable Long id) {
        repository.deleteById(id);
    }
    
   


}
