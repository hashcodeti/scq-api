/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.bluebudy.SCQ.domain.MontagemComposition;
import com.bluebudy.SCQ.dtos.MontagemCompositionDTO;
import com.bluebudy.SCQ.forms.MontagemCompositionForm;
import com.bluebudy.SCQ.repository.MontagemCompositionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alexandre
 */
@RestController

public class MontagemCompositionController {
    
    @Autowired
    private MontagemCompositionRepository repository;
    
        @PostMapping("/montagem")
	  ResponseEntity<List<MontagemComposition>> newForm(@RequestBody List<MontagemCompositionForm> forms) {
            List<MontagemComposition> mComposes =  forms.stream().map(mc -> mc.generateMontagemCompose()).collect(Collectors.toList());
            List<MontagemComposition> newComposes = mComposes.stream().map(compose -> repository.saveAndFlush(compose)).collect(Collectors.toList());
           
	     return ResponseEntity.ok(newComposes);
	  }
          
          @PutMapping("/montagem")
          ResponseEntity<?> deletemCompose(@RequestBody List<Long> forms) {
            forms.stream().forEach(action -> repository.deleteById(action));
            return ResponseEntity.ok().build();
	  }
          
         
          
          @GetMapping("/montagens/{etapaId}")
	  ResponseEntity<List<MontagemCompositionDTO>> findMontagem(@PathVariable Long etapaId) {
                List<MontagemComposition> mcs = repository.findByEtapaId(etapaId);
                return ResponseEntity.ok(MontagemCompositionDTO.mcToMcDTO(mcs));
	  }
    
}
