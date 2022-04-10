package com.bluebudy.SCQ.controllers;

import java.util.List;

import javax.validation.Valid;

import com.bluebudy.SCQ.domain.Turno;
import com.bluebudy.SCQ.forms.TurnoForm;
import com.bluebudy.SCQ.repository.TurnoRepository;

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
public class TurnoController {

    @Autowired
    public TurnoRepository repository;

    TurnoController() {
    }

    // Aggregate root

    @GetMapping("/turnos")
    List<Turno> all() {
        return repository.findAll();
    }

    @PostMapping("/turno")
    ResponseEntity<Turno> newForm(@RequestBody @Valid TurnoForm turnoForm) {
        return ResponseEntity.ok(repository.saveAndFlush(turnoForm.generateTurno()));
    }

    @PutMapping("/turno/{turnoId}")
    public ResponseEntity<Turno> replace(@RequestBody @Valid TurnoForm turnoForm, @PathVariable Long turnoId) {
        Turno turno = repository.findById(turnoId).get();
        turno.setNome(turnoForm.getNome());
        turno.setInicio(turnoForm.getInicio());
        turno.setFim(turnoForm.getFim());
        turno.setIsPrimeiroTurno(turnoForm.getIsPrimeiroTurno());
        return ResponseEntity.ok(turno);

    }

    @DeleteMapping("/turno/{id}")
    ResponseEntity<?> deleteEtapa(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("Turno: deletado com sucesso");
    }

}
