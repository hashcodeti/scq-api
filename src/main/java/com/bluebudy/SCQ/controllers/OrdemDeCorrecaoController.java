package com.bluebudy.SCQ.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.validation.Valid;

import com.bluebudy.SCQ.doc.OcpDocumentBuilder;
import com.bluebudy.SCQ.domain.Analise;
import com.bluebudy.SCQ.domain.OrdemDeCorrecao;
import com.bluebudy.SCQ.dtos.ListOrdemDeCorrecaoDTO;
import com.bluebudy.SCQ.dtos.OrdemDeCorrecaoDTO;
import com.bluebudy.SCQ.forms.OrdemDeCorrecaoFormAcao;
import com.bluebudy.SCQ.forms.OrdemDeCorrecaoFormAdicao;
import com.bluebudy.SCQ.repository.AcaoRepository;
import com.bluebudy.SCQ.repository.AdicaoRepository;
import com.bluebudy.SCQ.repository.AnaliseRepository;
import com.bluebudy.SCQ.repository.MateriaPrimaRepository;
import com.bluebudy.SCQ.repository.OrdemDeCorrecaoRepository;
import com.bluebudy.SCQ.services.NotificationService;

import org.docx4j.org.apache.poi.util.IOUtils;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class OrdemDeCorrecaoController {

    @Autowired
    public OrdemDeCorrecaoRepository repository;
    @Autowired
    public AdicaoRepository adicaoRepository;
    @Autowired
    public AcaoRepository acaoRepository;
    @Autowired
    public AnaliseRepository analiseRepository;
    @Autowired
    public MateriaPrimaRepository mpRepo;
    @Autowired
    public NotificationService notificationService;

    OrdemDeCorrecaoController(OrdemDeCorrecaoRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    @GetMapping("/ocps")
    List<OrdemDeCorrecaoDTO> all() {
        return OrdemDeCorrecaoDTO.ordemToOrdemDTO(repository.findAll());
    }

    @PostMapping("/ocp")
    ResponseEntity<OrdemDeCorrecaoDTO> newOcpAdicaoWithOutAnalise(
            @RequestBody @Valid OrdemDeCorrecaoFormAdicao correcaoForm) {
        Analise analise;
        if (correcaoForm.getAnaliseId() == null) {
            analise = analiseRepository.findLastAnaliseByParamId(correcaoForm.getParametroId());
        } else {
            analise = analiseRepository.findById(correcaoForm.getAnaliseId()).get();
        }

        OrdemDeCorrecao ocp = repository.saveAndFlush(correcaoForm.generateOrdemDeCorrecao(analise));
        adicaoRepository.saveAll(correcaoForm.generateAdicao(ocp));

        return ResponseEntity.ok(new OrdemDeCorrecaoDTO(ocp));
    }

    @PostMapping("/ocpAcao")
    ResponseEntity<OrdemDeCorrecaoDTO> newOcpAcaoWithOutAnalise(
            @RequestBody @Valid OrdemDeCorrecaoFormAcao correcaoForm) throws ParseException {
        Analise analise;
        if (correcaoForm.getAnaliseId() == null) {
            analise = analiseRepository.findLastAnaliseByParamId(correcaoForm.getParametroId());
        } else {
            analise = analiseRepository.findById(correcaoForm.getAnaliseId()).get();
        }
        OrdemDeCorrecao ocp = repository.saveAndFlush(correcaoForm.generateOrdemDeCorrecao(analise));
        acaoRepository.save(correcaoForm.generateAcao(ocp));

        return ResponseEntity.ok(new OrdemDeCorrecaoDTO(ocp));
    }

    @GetMapping(path = "/downloadOcp/{ocpId}")
    public HttpEntity<byte[]> downloadOcp(@PathVariable Long ocpId)
            throws InterruptedException, ExecutionException, IOException {

        OrdemDeCorrecao ocp = repository.findById(ocpId).get();

        OcpDocumentBuilder ocpBuilder = new OcpDocumentBuilder(ocp.getAdicoes(), repository, mpRepo);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<String> callable = () -> {
            return ocpBuilder.buildDocument();
        };

        Future<String> future = executorService.submit(callable);

        executorService.shutdown();
        String fileName = future.get();
        File file = new File(fileName);
        FileInputStream out = new FileInputStream(file);
        byte[] documentBody = IOUtils.toByteArray(out);
        out.close();
        file.delete();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + fileName);
        header.setAccessControlExposeHeaders(List.of("Access-Control-Expose-Headers", "Content-Disposition"));
        header.setContentLength(documentBody.length);

        return new HttpEntity<byte[]>(documentBody, header);

    }

    // Single item
    @GetMapping("/ocp/{id}")
    OrdemDeCorrecaoDTO one(@PathVariable Long id) {
        return new OrdemDeCorrecaoDTO(repository.findById(id).get());
    }

    @PutMapping("/ocp/aprovar/{id}")
    @Secured("ROLE_ADMIN")
    OrdemDeCorrecaoDTO aproveOCP(@PathVariable Long id) {
        return new OrdemDeCorrecaoDTO(repository.findById(id).map(ocp -> {
            ocp.setStatusOCP(true);
            return repository.save(ocp);
        }).get());

    }

    @PutMapping("/ocp/editarAdicao/{id}")
    OrdemDeCorrecaoDTO editarOcp(@RequestBody @Valid OrdemDeCorrecaoFormAdicao correcaoForm, @PathVariable Long id) {
        correcaoForm.updateAdicoes(adicaoRepository);
        return new OrdemDeCorrecaoDTO(repository.findById(id).map(ocp -> {
            ocp.setResponsavel(correcaoForm.getResponsavel());
            ocp.setObservacao(correcaoForm.getObservacao());
            return repository.save(ocp);
        }).get());

    }

    @PutMapping("/ocp/editarAcao/{id}")
    OrdemDeCorrecaoDTO editarOcp(@RequestBody @Valid OrdemDeCorrecaoFormAcao correcaoForm, @PathVariable Long id) {
        correcaoForm.updateAcao(acaoRepository);
        return new OrdemDeCorrecaoDTO(repository.findById(id).map(ocp -> {
            ocp.setResponsavel(correcaoForm.getResponsavel());
            ocp.setObservacao(correcaoForm.getObservacao());
            return repository.save(ocp);
        }).get());

    }

    @DeleteMapping("/ocp/{id}")
    ResponseEntity<?> deleteOCP(@PathVariable Long id) {
        JSONArray messages = new JSONArray();
        notificationService.updadteUserNotificationUi(List.of("loadParametros","loadOcps"));
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/ocpsList")
    List<ListOrdemDeCorrecaoDTO> listarOrdemDeCorrecao() {
        return ListOrdemDeCorrecaoDTO.ordemToOrdemDTO(repository.findAll());
    }

    @GetMapping("/fullOcp/{id}")
    ListOrdemDeCorrecaoDTO fullOneOcp(@PathVariable Long id) {
        return new ListOrdemDeCorrecaoDTO(repository.findById(id).get());
    }

}
