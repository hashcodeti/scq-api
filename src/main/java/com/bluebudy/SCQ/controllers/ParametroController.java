package com.bluebudy.SCQ.controllers;

import java.net.URI;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.bluebudy.SCQ.constantes.Unidade;
import com.bluebudy.SCQ.domain.Controle;
import com.bluebudy.SCQ.domain.Etapa;
import com.bluebudy.SCQ.domain.Notificacao;
import com.bluebudy.SCQ.domain.OrdemDeCorrecao;
import com.bluebudy.SCQ.domain.Parametro;
import com.bluebudy.SCQ.domain.Turno;
import com.bluebudy.SCQ.dtos.ParametroDTO;
import com.bluebudy.SCQ.forms.ParametroForm;
import com.bluebudy.SCQ.repository.EtapaRepository;
import com.bluebudy.SCQ.repository.NotificacaoRepository;
import com.bluebudy.SCQ.repository.OrdemDeCorrecaoRepository;
import com.bluebudy.SCQ.repository.ParametroRepository;
import com.bluebudy.SCQ.repository.TurnoRepository;
import com.bluebudy.SCQ.services.ControleService;
import com.bluebudy.SCQ.services.NotificationService;

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

@RestController

public class ParametroController {

	@Autowired
	public ParametroRepository repository;

	@Autowired
	public NotificacaoRepository notificacaoRepository;

	@Autowired
	public NotificationService notificationService;

	@Autowired
	public EtapaRepository etapaRepository;

	@Autowired
	public OrdemDeCorrecaoRepository ocpRepository;
	@Autowired
	public ControleService controleService;

	@Autowired
	public TurnoRepository turnRepository;



	ParametroController(ParametroRepository repository) {
		this.repository = repository;
	}

	// Aggregate root

	@GetMapping("/parametros")
	List<ParametroDTO> all() {

		Comparator<ParametroDTO> comparator = new Comparator<ParametroDTO>() {
			@Override
			public int compare(ParametroDTO o1, ParametroDTO o2) {
				return o1.getPosicao() - o2.getPosicao();
			}
		};

		return ParametroDTO.parametrosToParametrosDTO(repository.findAll()).stream().map(parametroDto -> {
			List<OrdemDeCorrecao> ordensOfParametro = ocpRepository.findByAnaliseParametroId(parametroDto.getId());
			if (verifyIfHaveAnyPendentOcp(ordensOfParametro)) {
				parametroDto.setCantBeUsed(true);
			} else {
				parametroDto.setCantBeUsed(false);
			}

			String nomeTurno = getTurno(parametroDto.getDataPlanejada());
			if(parametroDto.getFrequencia().equals(Long.valueOf(0))){
				parametroDto.setTurno("Quando Necessario");
			} else {
				parametroDto.setTurno(nomeTurno);
			}
			
			if(nomeTurno.trim().isEmpty()){
				parametroDto.setAnaliseHoje(false);
			} else {
				parametroDto.setAnaliseHoje(true);
			}
		
			return parametroDto;
		}).sorted(comparator).collect(Collectors.toList());
	}

	

	private String getTurno(Date dataPlanejada) {
		
		Calendar now = Calendar.getInstance();
		Calendar calendarPlanejada = Calendar.getInstance();
		calendarPlanejada.setTime(dataPlanejada);
		List<Turno> turnos = turnRepository.findAll();
		Turno primeiroTurnoRef = turnos.stream().filter(turno -> {
			if(turno.getIsPrimeiroTurno()) {
				return true;
			} else {
				return false;
			}
			
		}).collect(Collectors.toList()).get(0);
		Iterator<Turno> iterator = turnos.iterator();
		while (iterator.hasNext()) {
			 Turno turno =  iterator.next();
			 Date initialFullDate = getNowturnoFullDate(turno.getInicio(),true, primeiroTurnoRef);
			 Date finalFullDate = getNowturnoFullDate(turno.getFim(),false,primeiroTurnoRef);
			 if(calendarPlanejada.get(Calendar.DAY_OF_YEAR) < now.get(Calendar.DAY_OF_YEAR)){
				 return "Atrasado";
			 } else if ( isInsideTurno(initialFullDate, finalFullDate, dataPlanejada)) {
				 return turno.getNome();
			 } 
		
		}
		return "";
		
	}

	//Monta a data do turno com base no dia atual, caso o turno esteja antes do primeiro turno de referencia ascrenta 1 dia na data
	private Date getNowturnoFullDate(String turnoDate, boolean isInicio, Turno firstTurnoRef) {
		Calendar fullTurnoDate = Calendar.getInstance();
		fullTurnoDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(turnoDate.split(":")[0]));
		fullTurnoDate.set(Calendar.MINUTE, Integer.parseInt(turnoDate.split(":")[1]));
		if(isInicio) {
			if(Integer.parseInt(turnoDate.split(":")[0]) < Integer.parseInt(firstTurnoRef.getInicio().split(":")[0])) {
			   fullTurnoDate.add(Calendar.DAY_OF_YEAR, 1);
		   }
		} else {
			if(Integer.parseInt(turnoDate.split(":")[0]) < Integer.parseInt(firstTurnoRef.getFim().split(":")[0])) {
			   fullTurnoDate.add(Calendar.DAY_OF_YEAR, 1);
		   }
		}
		
		return fullTurnoDate.getTime();
	}

	private boolean isInsideTurno(Date inicioTurno, Date fimTurno, Date calendarPlanejada) {
		if((calendarPlanejada.getTime() >= inicioTurno.getTime()) && (calendarPlanejada.getTime() < fimTurno.getTime())){
			return true;
		}
			return false;
	}


	

	

	private boolean verifyIfHaveAnyPendentOcp(List<OrdemDeCorrecao> ordensOfParametro) {
		boolean havePendentOcp = false;
		for (OrdemDeCorrecao ocp : ordensOfParametro) {
			if (ocp.getAnalise().getNeedCorrecao()) {
				havePendentOcp = true;
			}
		}
		return havePendentOcp;
	}

	@PostMapping("/parametro")
	@Transactional
	ResponseEntity<ParametroDTO> newParametro(@RequestBody @Valid ParametroForm parametroform,
			UriComponentsBuilder builder) {

		Controle controle = new Controle();
		try {
			controle = controleService.buildControleService(parametroform);
		} catch (IllegalArgumentException | IllegalAccessException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Parametro newParametro = parametroform.generateParametro();
		newParametro.setControle(controle);
		Parametro parametro = repository.save(newParametro);
		URI uri = builder.path("/parametro/{id}").buildAndExpand(parametro.getId()).toUri();
		return ResponseEntity.created(uri).body(new ParametroDTO(parametro, "new"));

	}

	// Single item

	@GetMapping("/parametro/{id}")
	ParametroDTO one(@PathVariable Long id) {
		return new ParametroDTO(repository.findById(id).get());
	}

	@PutMapping("/parametro/{id}")
	ParametroDTO replaceParametro(@RequestBody @Valid ParametroForm newParametro, @PathVariable Long id) {
		return repository.findById(id).map(parametro -> {
			parametro.setNome(newParametro.getNome());
			Etapa etapa = new Etapa();
			etapa.setId(newParametro.getEtapaId());
			parametro.setEtapa(etapa);
			parametro.getControle().setHabilitado(newParametro.getIsHabilitado());
			parametro.setpMax(newParametro.getpMax());
			parametro.setpMin(newParametro.getpMin());
			parametro.setFormula(newParametro.getFormula());
			parametro.setpMaxT(newParametro.getpMaxT());
			parametro.setpMinT(newParametro.getpMinT());
			parametro.setShowChart(newParametro.isShowChart());
			parametro.setUnidade(Unidade.findEnumByValue(newParametro.getUnidade()));
			try {
				controleService.updateControle(newParametro, parametro.getControle());
			} catch (IllegalArgumentException | IllegalAccessException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ParametroDTO(repository.saveAndFlush(parametro), "edited");
		}).get();

	}

	@DeleteMapping("/parametro/{id}")
	ResponseEntity<String> deleteParametro(@PathVariable Long id) {
		Parametro parametro = repository.findById(id).get();
		List<Notificacao> notificacoes = notificacaoRepository.findByRefId(parametro.getControle().getId());
		for (Notificacao notificacao : notificacoes) {
			String[] tokens = notificacao.getMessagem().split(":");
			if (tokens[0].equals("Analise")) {
				notificacaoRepository.deleteById(notificacao.getId());
			}
		}

		repository.deleteById(id);
		notificationService.updadteUserNotificationUi(List.of("loadParametros","loadEtapas"));
		return ResponseEntity.ok("Parametro: " + id + " deletado com sucesso");
	}

	@GetMapping("/parametros/{etapaId}")
	List<ParametroDTO> findParametrosByEtapaId(@PathVariable(name = "etapaId") Long etapaId) {
		return ParametroDTO.parametrosToParametrosDTO(repository.findParametrosByEtapaId(etapaId));
	}

}
