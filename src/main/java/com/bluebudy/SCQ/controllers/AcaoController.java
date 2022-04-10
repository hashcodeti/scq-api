package com.bluebudy.SCQ.controllers;

import java.net.URI;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.bluebudy.SCQ.domain.Acao;
import com.bluebudy.SCQ.dtos.AcaoDTO;
import com.bluebudy.SCQ.forms.AnaliseFormComOcpAcao;
import com.bluebudy.SCQ.repository.AcaoRepository;
import com.bluebudy.SCQ.services.NotificationService;

@RestController

public class AcaoController {

    @Autowired
    public AcaoRepository repository;

    @Autowired
    public NotificationService notificationService;
    

    AcaoController(AcaoRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    @GetMapping("/acoes")
    List<AcaoDTO> allAcoes() {
        return AcaoDTO.acoesToAcoesDTO(repository.findAll());
    }

    @PostMapping("/acao")
    @Transactional
    ResponseEntity<AcaoDTO> newAcao(@RequestBody @Valid AnaliseFormComOcpAcao acaoform, UriComponentsBuilder builder) throws ParseException {
        Acao acao = repository.save(acaoform.generateAcao());
        URI uri = builder.path("/acao/{id}").buildAndExpand(acao.getId()).toUri();
        notificationService.updadteUserNotificationUi(List.of("loadNotifications"));
        return ResponseEntity.created(uri).body(new AcaoDTO(acao));

    }

    // Single item
    @GetMapping("/acao/{id}")
    AcaoDTO getOneAcao(@PathVariable Long id) {
        return new AcaoDTO(repository.findById(id).get());
    }

    
    @PutMapping("/acao/corrigir/{id}")
    AcaoDTO aprovaAcao(@PathVariable Long id) {
        
        return repository.findByOrdemDeCorrecaoId(id).map(acao -> {
           
            acao.setStatus(true);
            acao.setNotified(false);
            
            acao.setData(Calendar.getInstance().getTime());
            repository.saveAndFlush(acao);
            notificationService.updadteUserNotificationUi(List.of("loadNotifications"));
            return new AcaoDTO(acao);
        }).get();

    }
    
    @DeleteMapping("/acao/{id}")
    void deleteAcao(@PathVariable Long id) {
        repository.deleteById(id);
    }

 

}
