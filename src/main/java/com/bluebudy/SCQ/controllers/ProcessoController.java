package com.bluebudy.SCQ.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.bluebudy.SCQ.domain.Processo;
import com.bluebudy.SCQ.dtos.ProcessoDTO;
import com.bluebudy.SCQ.forms.ProcessoForm;
import com.bluebudy.SCQ.repository.ProcessoRepository;
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


@RestController

public class ProcessoController {
	
	
	@Autowired
	public ProcessoRepository repository;

	@Autowired
	public NotificationService notificationService;

	  ProcessoController(ProcessoRepository repository) {
	    this.repository = repository;
	  }

	  // Aggregate root

	  @GetMapping("/processos")
	  List<ProcessoDTO> all() {
	    return ProcessoDTO.processosToProcessosDTO(repository.findAll());
	  }

	  @PostMapping( path = "/processo")
	  public ResponseEntity<ProcessoDTO> newLinha(@RequestBody @Valid ProcessoForm processoform,UriComponentsBuilder builder) {
		  Processo processo = repository.save(processoform.generatreProcesso());
		  URI uri = builder.path("/linha/{id}").buildAndExpand(processo.getId()).toUri();
		  return ResponseEntity.created(uri).body(new ProcessoDTO(processo));	  
		  }

	  // Single item

	  @GetMapping("/processo/{id}")
	  ProcessoDTO one(@PathVariable Long id) {
	    return new ProcessoDTO(repository.findById(id).get());
	  }

	  @PutMapping("/processo/{id}")
	  ProcessoDTO replaceProcesso(@RequestBody @Valid ProcessoForm processoform, @PathVariable Long id) {
	   return repository.findById(id).map(processo -> {
		   processo.setNome(processoform.getNome());
		   repository.save(processo);
		   return new ProcessoDTO(processo);
	   }).get();
	   
	  }
		 
	     

	  @DeleteMapping("/processo/{id}")
	  ResponseEntity<?> deleteProcesso(@PathVariable Long id) {
             repository.deleteById(id);
			 notificationService.updadteUserNotificationUi(List.of("loadProcessos"));
	    return ResponseEntity.ok("Processo: " + id + " deletado com sucesso");
	  }

}
