/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.controllers;

import java.net.URI;
import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import com.bluebudy.SCQ.domain.Controle;
import com.bluebudy.SCQ.domain.Processo;
import com.bluebudy.SCQ.domain.TarefasDeManutencao;
import com.bluebudy.SCQ.dtos.TarefaDTO;
import com.bluebudy.SCQ.forms.TarefasDeManutencaoForm;
import com.bluebudy.SCQ.forms.TarefasDeManutencaoFormEdit;
import com.bluebudy.SCQ.repository.TarefasDeManutencaoRepository;
import com.bluebudy.SCQ.services.ControleService;
import com.bluebudy.SCQ.services.NotificationService;

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

public class TarefasDeManutencaoController {

  @Autowired
  TarefasDeManutencaoRepository repository;
  @Autowired
  ControleService controleService;

  @Autowired
  NotificationService notificationService;

  @PostMapping("/tarefa")
  ResponseEntity<TarefaDTO> newTarefa(@RequestBody @Valid TarefasDeManutencaoForm form, UriComponentsBuilder builder)
      throws ParseException {

    Controle controle = new Controle();
    try {
      controle = controleService.buildControleService(form);
    } catch (IllegalArgumentException | IllegalAccessException | ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    TarefasDeManutencao newTarefa = form.generateTarefasForm();
    newTarefa.setControle(controle);
    TarefasDeManutencao tarefa = repository.saveAndFlush(newTarefa);
    URI uri = builder.path("/tarefa/{id}").buildAndExpand(tarefa.getId()).toUri();
    return ResponseEntity.created(uri).body(new TarefaDTO(tarefa));

  }

  @GetMapping("/tarefas")
  ResponseEntity<List<TarefaDTO>> getTarefas() throws ParseException {
    return ResponseEntity.ok(TarefaDTO.tarefasToTarefasDTO(repository.findAll()));
  }

  @GetMapping("/tarefa/find/{processoId}")
  ResponseEntity<List<TarefaDTO>> getTarefaByProcesso(@PathVariable Long processoId) throws ParseException {
    return ResponseEntity.ok(TarefaDTO.tarefasToTarefasDTO(repository.findByProcessoId(processoId)));
  }

  @GetMapping("/tarefa/{tarefaId}")
  ResponseEntity<TarefaDTO> findOne(@PathVariable Long tarefaId) throws ParseException {
    return ResponseEntity.ok(new TarefaDTO(repository.findById(tarefaId).get()));
  }

  @PutMapping("/tarefa/update/{tarefaId}")
  ResponseEntity<TarefaDTO> updateTarefa(@RequestBody @Valid TarefasDeManutencaoFormEdit tarefaForm,
      @PathVariable Long tarefaId) throws ParseException {
    TarefasDeManutencao tarefa = repository.findById(tarefaId).get();
    Processo processo = new Processo();
    processo.setId(tarefaForm.getProcessoId());

    tarefa.setNome(tarefaForm.getNome());
    tarefa.getControle().setPeriodo(tarefaForm.buildFrequencia());
    tarefa.setCodigoDoDocumento(tarefaForm.getCodigoDoDocumento());
    tarefa.setProcesso(processo);
    try {
      controleService.updateControle(tarefaForm, tarefa.getControle());
    } catch (IllegalArgumentException | IllegalAccessException | ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return ResponseEntity.ok(new TarefaDTO(repository.save(tarefa)));
  }

  @DeleteMapping("/tarefa/{id}")
  ResponseEntity<?> deleteTarefas(@PathVariable Long id) {
    repository.deleteById(id);
    notificationService.updadteUserNotificationUi(List.of("loadTarefasDeManutencao"));
    return ResponseEntity.ok("Tarefa: " + id + " deletado com sucesso");
  }

}
