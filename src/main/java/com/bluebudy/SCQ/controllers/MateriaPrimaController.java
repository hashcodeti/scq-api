package com.bluebudy.SCQ.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.bluebudy.SCQ.constantes.Unidade;
import com.bluebudy.SCQ.domain.Etapa;
import com.bluebudy.SCQ.domain.MateriaPrima;
import com.bluebudy.SCQ.dtos.AdicaoChartDTO;
import com.bluebudy.SCQ.dtos.MateriaPrimaDTO;
import com.bluebudy.SCQ.dtos.MateriaPrimaSearchDTO;
import com.bluebudy.SCQ.forms.MateriaPrimaForm;
import com.bluebudy.SCQ.repository.EtapaRepository;
import com.bluebudy.SCQ.repository.MateriaPrimaRepository;
import com.bluebudy.SCQ.repository.ProcessoRepository;
import com.bluebudy.SCQ.services.AdicaoService;
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
public class MateriaPrimaController {

    @Autowired
    public MateriaPrimaRepository repository;

    @Autowired
    public EtapaRepository etapaRepository;

    @Autowired
    public ProcessoRepository processoRepository;

    @Autowired
    public NotificationService notificationService;

    @Autowired
    private AdicaoService adicaoService;

    MateriaPrimaController(MateriaPrimaRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    @GetMapping("/materiaPrimas")
    List<MateriaPrimaDTO> all() {
        return MateriaPrimaDTO.materiasPrimaToMateriasPrimaDTO(repository.findAll());
    }

    @PostMapping("/materiaPrima")
    ResponseEntity<MateriaPrimaDTO> newMateriaPrima(@RequestBody @Valid MateriaPrimaForm materiaPrimaform, UriComponentsBuilder builder) {
        MateriaPrima mp = repository.save(materiaPrimaform.generateMateriaPrima());
        URI uri = builder.path("materiaPrima/{id}").buildAndExpand(mp.getId()).toUri();
        return ResponseEntity.created(uri).body(new MateriaPrimaDTO(mp));
    }

    // Single item
    @GetMapping("/materiaPrima/{id}")
    ResponseEntity<MateriaPrimaDTO> one(@PathVariable Long id) {
        Optional<MateriaPrima> mpOpt = repository.findById(id);
        if (mpOpt.isPresent()) {
            return ResponseEntity.ok(new MateriaPrimaDTO(mpOpt.get()));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/materiaPrima/{id}")
    MateriaPrimaDTO replaceMateriaPrima(@RequestBody MateriaPrimaForm materiaPrimaform, @PathVariable Long id) {
        return repository.findById(id)
                .map(materiaPrima -> {
                    materiaPrima.setNome(materiaPrimaform.getNome());
                    materiaPrima.setFornecedor(materiaPrimaform.getFornecedor());
                    materiaPrima.setFatorTitulometrico(materiaPrimaform.getFatorTitulometrico());
                    materiaPrima.setPreco(materiaPrimaform.getPreco());
                    materiaPrima.setUnidade(Unidade.findEnumByValue(materiaPrimaform.getUnidade()));
                    

                    return new MateriaPrimaDTO(repository.save(materiaPrima));
                }).get();

    }

    @GetMapping("/materiaPrimaByEtapa/{etapaId}")
    ResponseEntity<List<MateriaPrimaDTO>> materiaPrimaByEtapaId(@PathVariable Long etapaId) {
        Etapa etapa = etapaRepository.findById(etapaId).get();
        return ResponseEntity.ok(etapa.getMontagem().stream().map(mc -> new MateriaPrimaDTO(mc.getMateriaPrima())).collect(Collectors.toList()));
    }

    @DeleteMapping("/materiaPrima/{id}")
    void deleteMateriaPrima(@PathVariable Long id) {
        notificationService.updadteUserNotificationUi(List.of("loadMateriasPrima"));
        repository.deleteById(id);
    }

    @GetMapping("/materiaPrima/{dataInicial}/{dataFinal}")
    List<AdicaoChartDTO> loadAdicaoChart(@PathVariable String dataInicial, @PathVariable String dataFinal) {
       return processoRepository.findAll().stream().map(processo -> {
            return adicaoService.loadAdicaoChart(processo, dataInicial, dataFinal);
        }).collect(Collectors.toList());
    }
    
    @GetMapping("/materiaPrima/search/{searchString}")
    ResponseEntity<MateriaPrimaSearchDTO> searchMateriaPrima(@PathVariable String searchString) {
        List<MateriaPrima> materiasPrima = repository.findAll();
        List<MateriaPrima> mpFounded = new ArrayList<>();
        materiasPrima.stream().forEach(mp -> {
            if(mp.getNome().contains(searchString)){
                mpFounded.add(mp);
            } 
        } );
        Collections.sort(mpFounded);
        int index = mpFounded.size();
        index = index -1;
        return ResponseEntity.ok(new MateriaPrimaSearchDTO(mpFounded.get(index)));
    }

}
