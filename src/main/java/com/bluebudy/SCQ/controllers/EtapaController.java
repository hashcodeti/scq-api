
package com.bluebudy.SCQ.controllers;

import java.util.List;

import javax.validation.Valid;

import com.bluebudy.SCQ.domain.Etapa;
import com.bluebudy.SCQ.domain.Processo;
import com.bluebudy.SCQ.dtos.EtapaDTO;
import com.bluebudy.SCQ.forms.EtapaForm;
import com.bluebudy.SCQ.repository.EtapaRepository;
import com.bluebudy.SCQ.repository.ProcessoRepository;
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

@RestController

public class EtapaController {

	@Autowired
	public EtapaRepository repository;
	@Autowired
	public ProcessoRepository processoRepo;
	@Autowired
	public ControleService controleService;
	@Autowired
	public NotificationService notificationService;

	EtapaController(EtapaRepository repository) {
		this.repository = repository;
	}

	// Aggregate root

	@GetMapping("/etapas")
	List<EtapaDTO> all() {
		return EtapaDTO.etapasToEtapasDTO(repository.findAll());
	}

	@PostMapping("/etapa")
	ResponseEntity<EtapaDTO> newForm(@RequestBody @Valid EtapaForm etapaform) {

		Etapa etapa = repository.saveAndFlush(etapaform.generateEtapa(processoRepo));

		return ResponseEntity.ok(new EtapaDTO(etapa, "new"));
	}

	// Single item

	@GetMapping("/etapa/{id}")
	EtapaDTO manyByLinha(@PathVariable Long id) {
		return new EtapaDTO(repository.findById(id).get());
	}

	@PutMapping("/etapa/update/{etapaId}")
	public ResponseEntity<EtapaDTO> replace(@RequestBody @Valid EtapaForm form, @PathVariable Long etapaId) {
		Etapa etapa = repository.findById(etapaId).get();

		etapa.setNome(form.getNome());
		etapa.setPosicao(form.getPosicao());
		Processo processo = new Processo();
		processo.setId(form.getProcessoId());
		etapa.setProcesso(processo);
		etapa.setVolume(form.getVolume());
		repository.save(etapa);
		return ResponseEntity.ok(new EtapaDTO(etapa));

	}

	@DeleteMapping("/etapa/{id}")
	ResponseEntity<?> deleteEtapa(@PathVariable Long id) {
		repository.deleteById(id);
		notificationService.updadteUserNotificationUi(List.of("loadEtapas"));
		return ResponseEntity.ok("Etapa: " + id + " deletada com sucesso");
	}

	@GetMapping("/etapas/{processoId}")
	List<EtapaDTO> findEtapaByLinhaId(@PathVariable(name = "processoId") Long processoId) {
		return EtapaDTO.etapasToEtapasDTO(repository.findEtapaByProcesso(processoId));
	}

}
