/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.bluebudy.SCQ.doc.DocumentBuilder;
import com.bluebudy.SCQ.doc.OmpDocumentBuilder;
import com.bluebudy.SCQ.doc.OmpDocumentBuilderTarefas;
import com.bluebudy.SCQ.domain.OrdemDeManutencao;
import com.bluebudy.SCQ.dtos.FullOmpDetailDTO;
import com.bluebudy.SCQ.dtos.FullOmpVisualizarDTO;
import com.bluebudy.SCQ.dtos.OmpChartDto;
import com.bluebudy.SCQ.dtos.OrdemDeManutencaoDTO;
import com.bluebudy.SCQ.forms.OmpFinalizarForm;
import com.bluebudy.SCQ.forms.OmpForm;
import com.bluebudy.SCQ.repository.NotificacaoRepository;
import com.bluebudy.SCQ.repository.OmpRepository;
import com.bluebudy.SCQ.repository.ProcessoRepository;
import com.bluebudy.SCQ.repository.TarefasDeManutencaoRepository;
import com.bluebudy.SCQ.repository.TrocaRepository;
import com.bluebudy.SCQ.services.NotificationService;
import com.bluebudy.SCQ.services.OmpService;

import org.docx4j.org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alexandre
 */
@RestController
public class OmpController {

	@Autowired
	public OmpRepository repository;
	@Autowired
	public ProcessoRepository pRepo;
	@Autowired
	public TrocaRepository trocaRepo;
	@Autowired
	public TarefasDeManutencaoRepository tarefasRepo;

	@Autowired
	public NotificationService notificationService;

	@Autowired
	private NotificacaoRepository notificationRepository;

	@Autowired
	public OmpService ompService;

	OmpController(OmpRepository repository) {
		this.repository = repository;
	}

	// Aggregate root
	@GetMapping("/omps")
	List<OrdemDeManutencaoDTO> all() {

		return OrdemDeManutencaoDTO.ordemToOrdemDTO(repository.findAll(), pRepo);
	}

	@GetMapping("/omps/{ompId}")
	ResponseEntity<FullOmpDetailDTO> loadFullOmpDetails(@PathVariable Long ompId) {

		FullOmpDetailDTO fullDetailOmp = new FullOmpDetailDTO(repository.findById(ompId).get(), tarefasRepo, trocaRepo);
		return ResponseEntity.ok(fullDetailOmp.buildListOmpDetails());
	}

	@GetMapping("/omps/historico/{ompId}")
	ResponseEntity<FullOmpVisualizarDTO> vizualizarOmp(@PathVariable Long ompId) {

		FullOmpVisualizarDTO fullVizuDetail = new FullOmpVisualizarDTO(repository.findById(ompId).get(), tarefasRepo,
				trocaRepo);
		return ResponseEntity.ok(fullVizuDetail.buildListOmpDetails());
	}

	// Single item
	@GetMapping("/omp/{id}")
	OrdemDeManutencaoDTO one(@PathVariable Long id) {
		return new OrdemDeManutencaoDTO(repository.findById(id).get(), pRepo);
	}

	@DeleteMapping("/omp/{id}")
	ResponseEntity<?> deleOmp(@PathVariable Long id) {
		repository.deleteById(id);
		notificationService.updadteUserNotificationUi(List.of("loadOrdensDeManutencao"));
		return ResponseEntity.ok().build();
	}

	@PostMapping(value = "/omp")
	public @ResponseBody ResponseEntity<Long> generateOmp(@Valid @RequestBody OmpForm form)
			throws IOException, ParseException, InterruptedException, ExecutionException {
		Long id = repository.save(form.generateOmp()).getId();
		return ResponseEntity.ok(id);

	}

	@PutMapping("/omp/tarefa/{tarefaId}")
	public @ResponseBody ResponseEntity<Long> updateTarefa(@RequestParam Long tarefaId) {

		return null;
	}

	@PutMapping("/omp/troca/{trocaId}")
	public @ResponseBody ResponseEntity<Long> updateTroca(@RequestParam Long trocaId) {
		return null;
	}

	@PostMapping(value = "/omp/finalizar")
	public @ResponseBody ResponseEntity<String> finalizarOmp(@RequestBody OmpFinalizarForm form)
			throws IOException, ParseException, InterruptedException, ExecutionException {
		form.updateTrocasAndTarefas(repository, trocaRepo, tarefasRepo, notificationService, notificationRepository);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/downloadOmp/{type}/{ompId}")
	public HttpEntity<byte[]> download(@PathVariable String type, @PathVariable Long ompId) throws IOException {
		DocumentBuilder ompBuilder = null;
		OrdemDeManutencao omp = repository.findById(ompId).get();
		if (type.equals("limpeza")) {
			ompBuilder = new OmpDocumentBuilder(omp, pRepo, trocaRepo, tarefasRepo);
		} else {
			ompBuilder = new OmpDocumentBuilderTarefas(omp, pRepo, trocaRepo, tarefasRepo);
		}

		final DocumentBuilder finalBuilder = ompBuilder;

		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Callable<String> callable = () -> {
			return finalBuilder.buildDocument();
		};

		Future<String> future = executorService.submit(callable);
		String fileName = "";
		try {
			fileName = future.get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executorService.shutdown();

		File file = new File(fileName);
		FileInputStream out = new FileInputStream(file);
		byte[] documentBody = IOUtils.toByteArray(out);
		out.close();
		file.delete();

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_PDF);
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		header.setAccessControlExposeHeaders(List.of("Access-Control-Expose-Headers", "Content-Disposition"));
		header.setContentLength(documentBody.length);

		return new HttpEntity<byte[]>(documentBody, header);

	}

	@GetMapping("/ompChart/{dataInicial}/{dataFinal}")
	List<OmpChartDto> lodOmpchart(@PathVariable String dataInicial, @PathVariable String dataFinal) throws ParseException {
		Date inicialdata = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dataInicial);
		Date finaldata = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dataFinal);
		return pRepo.findAll().stream()
				.map(processo -> ompService.loadOmpChart(processo.getId(), inicialdata, finaldata))
				.collect(Collectors.toList());

	}

}
