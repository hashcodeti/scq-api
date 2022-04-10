/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.bluebudy.SCQ.dtos.TrocaDTO;
import com.bluebudy.SCQ.forms.TrocaForm;
import com.bluebudy.SCQ.repository.EtapaRepository;
import com.bluebudy.SCQ.repository.MateriaPrimaRepository;
import com.bluebudy.SCQ.repository.NotificacaoRepository;
import com.bluebudy.SCQ.repository.OmpRepository;
import com.bluebudy.SCQ.repository.TrocaRepository;
import com.bluebudy.SCQ.services.ControleService;
import com.bluebudy.SCQ.services.NotificationService;
import com.bluebudy.SCQ.services.TrocaService;

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

/**
 *
 * @author Alexandre
 */
@RestController

public class TrocaController {

    @Autowired
    TrocaRepository repository;

    @Autowired
    NotificacaoRepository notificacaoRepository;

    @Autowired
    MateriaPrimaRepository mpRepository;

    @Autowired
    EtapaRepository etapaRepository;

    @Autowired
    OmpRepository ompRepo;

    @Autowired
    ControleService controleService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    TrocaService trocaService;

    TrocaController(TrocaRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/troca")
    @Transactional
    ResponseEntity<TrocaDTO> newTroca(@RequestBody @Valid TrocaForm trocaForm, UriComponentsBuilder builder) {
        TrocaDTO troca =  trocaService.newTroca(trocaForm);
        URI uri = builder.path("/troca/{id}").buildAndExpand(troca.getId()).toUri();
        return ResponseEntity.created(uri).body(troca);
    }

    @GetMapping("/trocas")
    ResponseEntity<List<TrocaDTO>> findAll() {
        return ResponseEntity.ok(trocaService.findAll());
    }

    @GetMapping("/troca/{parametroId}")
    ResponseEntity<List<TrocaDTO>> findByProcessoId(Long parametroId) {
        return ResponseEntity.ok(trocaService.findByProcessoId(parametroId));
    }

    @GetMapping("/troca/find/{etapaId}")
    ResponseEntity<TrocaDTO> findByEtapaId(@PathVariable Long etapaId) {
        return ResponseEntity.ok(trocaService.findByEtapaId(etapaId));
    }

    @PutMapping("/troca/edit/{trocaId}")
    ResponseEntity<TrocaDTO> updateTroca(@RequestBody TrocaForm trocaForm, @PathVariable Long trocaId) {
        return ResponseEntity.ok(trocaService.updateTroca(trocaForm, trocaId));
    }

    @DeleteMapping("/delete/troca/{trocaId}")
    ResponseEntity<?> deleteTroca(@PathVariable Long trocaId) {
        trocaService.deleteTroca(trocaId);
        return ResponseEntity.ok("Troca: " + trocaId + " deletada com sucesso");
    }

}
