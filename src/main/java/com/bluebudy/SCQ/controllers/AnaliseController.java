package com.bluebudy.SCQ.controllers;

import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import com.bluebudy.SCQ.domain.Acao;
import com.bluebudy.SCQ.domain.Analise;
import com.bluebudy.SCQ.domain.Controle;
import com.bluebudy.SCQ.domain.Etapa;
import com.bluebudy.SCQ.domain.OrdemDeCorrecao;
import com.bluebudy.SCQ.domain.Parametro;
import com.bluebudy.SCQ.dtos.AnaliseChartDTO;
import com.bluebudy.SCQ.dtos.AnaliseDTO;
import com.bluebudy.SCQ.dtos.AnaliseHistoricDto;
import com.bluebudy.SCQ.dtos.OrdemDeCorrecaoDTO;
import com.bluebudy.SCQ.dtos.ReanaliseDTO;
import com.bluebudy.SCQ.forms.AnaliseForm;
import com.bluebudy.SCQ.forms.AnaliseFormComOcpAcao;
import com.bluebudy.SCQ.forms.AnaliseFormComOcpAdicao;
import com.bluebudy.SCQ.repository.AcaoRepository;
import com.bluebudy.SCQ.repository.AdicaoRepository;
import com.bluebudy.SCQ.repository.AnaliseRepository;
import com.bluebudy.SCQ.repository.ControleRepository;
import com.bluebudy.SCQ.repository.EtapaRepository;
import com.bluebudy.SCQ.repository.NotificacaoRepository;
import com.bluebudy.SCQ.repository.OrdemDeCorrecaoRepository;
import com.bluebudy.SCQ.repository.ParametroRepository;
import com.bluebudy.SCQ.repository.ProcessoRepository;
import com.bluebudy.SCQ.services.AnaliseService;
import com.bluebudy.SCQ.services.DependenceAnaliseGenerator;
import com.bluebudy.SCQ.services.NotificationService;
import com.bluebudy.SCQ.utils.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class AnaliseController {



   

    @Autowired
    public AnaliseRepository repository;
    @Autowired
    public ParametroRepository parametroRepository;

    @Autowired
    public EtapaRepository eRepository;
    
    @Autowired
    public ControleRepository freqRepository;
    
    @Autowired
    public NotificacaoRepository notificationRepository;
    
    @Autowired
    public ParametroRepository paramRepo;

    @Autowired
    public ProcessoRepository processoRepository;

    @Autowired
    public OrdemDeCorrecaoRepository ocpRepo;
    
    @Autowired
    public AdicaoRepository adicaoRepo;

    @Autowired
    public AnaliseService analiseService;

    @Autowired
    public DependenceAnaliseGenerator analiseDependenceSerive;
    
    @Autowired
    public AcaoRepository acaoRepo;
    
    @Autowired
    public NotificationService notificationService;
    
    AnaliseController(AnaliseRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    @GetMapping("/analises")
    List<AnaliseDTO> all() {
        return AnaliseDTO.analiseToAnaliseDTO(repository.findAll());
    }

    @PostMapping("/analiseComOcpAdicao")
    @Transactional
    ResponseEntity<OrdemDeCorrecaoDTO>newAnaliseComOcpAdicao(@RequestBody @Valid AnaliseFormComOcpAdicao analiseform,UriComponentsBuilder builder) throws ParseException {
    	

        Analise analise = repository.saveAndFlush(analiseform.generateAnalise());
        analiseDependenceSerive.setAnalise(analise);
        analiseDependenceSerive.resolveParametrosThatNeedAnalise();
        
        Controle controle = paramRepo.findById(analise.getParametro().getId()).get().getControle();
        if(controle.isHabilitado()) {
            controle.updateNextAnalise();
            controle.setNotified(false);
            freqRepository.saveAndFlush(controle);
        }
        
        OrdemDeCorrecao ocp = ocpRepo.saveAndFlush(analiseform.generateOrdemDeCorrecao(analise.getId()));
        
        adicaoRepo.saveAll(analiseform.generateAdicao(ocp.getId()));
        notificationService.resolveNotifications("Analise", freqRepository, controle.getId(), notificationRepository);
        URI uri = builder.path("/analise/{id}").buildAndExpand(analise.getId()).toUri();
        notificationService.updadteUserNotificationUi(List.of("loadNotifications"));
        return ResponseEntity.created(uri).body(new OrdemDeCorrecaoDTO(ocpRepo.getOne(ocp.getId())));

    }
    
    
    @PostMapping("/analiseComOcpAcao")
    @Transactional
    ResponseEntity<AnaliseDTO> newAnaliseComOcpAcao(@RequestBody @Valid AnaliseFormComOcpAcao analiseform ,UriComponentsBuilder builder) throws ParseException {
    	

        Analise analise = repository.saveAndFlush(analiseform.generateAnalise());
        analiseDependenceSerive.setAnalise(analise);
        analiseDependenceSerive.resolveParametrosThatNeedAnalise();
        
        Controle controle = paramRepo.findById(analise.getParametro().getId()).get().getControle();
        if(controle.isHabilitado()) {
            controle.updateNextAnalise();
            controle.setNotified(false);
            freqRepository.saveAndFlush(controle);
        }
      
        OrdemDeCorrecao ocp =  ocpRepo.saveAndFlush(analiseform.generateOrdemDeCorrecao(analise.getId()));
        Acao acao =  analiseform.generateAcao();
        acao.setOrdemDeCorrecao(ocp);
        acaoRepo.save(acao);
        notificationService.resolveNotifications("Analise", freqRepository, controle.getId(), notificationRepository);
        URI uri = builder.path("/analise/{id}").buildAndExpand(analise.getId()).toUri();
        notificationService.updadteUserNotificationUi(List.of("loadNotifications"));
        return ResponseEntity.created(uri).body(new AnaliseDTO(analise));

    }
    
    
    @PostMapping("/analise")
    @Transactional
    ResponseEntity<AnaliseDTO> newAnalise(@RequestBody @Valid AnaliseForm analiseform, UriComponentsBuilder builder) throws ParseException {
        Analise analise = repository.saveAndFlush(analiseform.generateAnalise());   
        analiseDependenceSerive.setAnalise(analise);
        analiseDependenceSerive.resolveParametrosThatNeedAnalise();
        Controle controle = paramRepo.findById(analise.getParametro().getId()).get().getControle();
        if(controle.isHabilitado()) {
            controle.updateNextAnalise();
            controle.setNotified(false);
            freqRepository.saveAndFlush(controle);
        }
        notificationService.resolveNotifications("Analise", freqRepository, controle.getId(), notificationRepository);
        URI uri = builder.path("/analise/{id}").buildAndExpand(analise.getId()).toUri();
        
        notificationService.updadteUserNotificationUi(List.of("loadNotifications"));
        return ResponseEntity.created(uri).body(new AnaliseDTO(analise));

    }

    /* SplitAnalise
    @GetMapping("/splitAnalise")
    void removeEqualsRelacao() {
     
        List<Analise> analisesByParameterId =  repository.findAnaliseByParametroId(Long.valueOf("46"));
        int alternationControl = 0;
        for(Analise analise : analisesByParameterId){
            if(alternationControl == 3) {
                alternationControl = 0;
            }
            if(alternationControl == 1){
                Parametro param = new Parametro();
                param.setId(Long.valueOf("202"));
                analise.setParametro(param);
                repository.save(analise);
            }
            if(alternationControl == 2){
                Parametro param = new Parametro();
                param.setId(Long.valueOf("203"));
                analise.setParametro(param);
                repository.save(analise);
            }
            alternationControl++;
        }
    }
 */

    /*@GetMapping("/deleteAnaliseRelacao")
    Set<Long>  removeEqualsRselacao() {
        List<Analise> analisesRelacao = repository.findAnalisesById(Long.valueOf("19"));
        List<Analise> analiseReferecia = repository.findAnalisesById(Long.valueOf("8"));
        Set<Long> matchedAnalises = new LinkedHashSet<>();
        for(Analise analiseRef : analiseReferecia) {
                Calendar dataAnaliseRef = Calendar.getInstance();
                dataAnaliseRef.setTime(analiseRef.getData());
            for(Analise analiseRelacao : analisesRelacao){
                Calendar dataAnaliseRela = Calendar.getInstance();
                dataAnaliseRela.setTime(analiseRelacao.getData());
                if(dataAnaliseRef.get(Calendar.DAY_OF_MONTH) == dataAnaliseRela.get(Calendar.DAY_OF_MONTH) && dataAnaliseRef.get(Calendar.MINUTE) == dataAnaliseRela.get(Calendar.MINUTE)){
                    matchedAnalises.add(analiseRelacao.getId());
                }
            }
        }

       List<Long> analisesRelacaoIds =  analisesRelacao.stream().map(mapper -> mapper.getId()).collect(Collectors.toList());

       analisesRelacaoIds.forEach(matchAnalise -> {
          if(!matchedAnalises.contains(matchAnalise)){
                repository.deleteById(matchAnalise);
          }
       });

        return matchedAnalises;
    }*/


    // Single item
    @GetMapping("/analise/{id}")
    AnaliseDTO one(@PathVariable Long id) {
        return new AnaliseDTO(repository.findById(id).get());
    }
    
    @GetMapping("/reanalise/{id}")
    ReanaliseDTO findForReanalise(@PathVariable Long id) {
        return new ReanaliseDTO(repository.findById(id).get());
    }

    @PutMapping("/analise/{id}")
    AnaliseDTO replaceAnalise(@RequestBody AnaliseForm analiseform, @PathVariable Long id) {
        return repository.findById(id)
                .map(analise -> {
					analise = analiseform.replaceAnalise();
                    return new AnaliseDTO(repository.save(analise));
                })
                .orElseGet(() -> {
                    analiseform.setId(id);
                    return new AnaliseDTO(repository.save(analiseform.generateAnalise()));
                });
        
    }
    
    
    @PutMapping("/analise/{data}/{id}")
    AnaliseDTO replaceAnaliseData(@PathVariable String data, @PathVariable Long id) {
        return repository.findById(id)
                .map(analise -> {
					try {
						analise.setData(DateUtils.getDateTimeLocal(data));
                        Controle controle = paramRepo.findById(analise.getParametro().getId()).get().getControle();
                        if(!controle.getPeriodo().equals(Long.valueOf(0))){
                            controle.setDataRealizada(DateUtils.getDateTimeLocal(data));
                        }
                        
                        freqRepository.saveAndFlush(controle);
					} catch (ParseException e) { 
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
                    return new AnaliseDTO(repository.save(analise));
                }).get();
                
        
    }

    @DeleteMapping("/analise/{id}")
    @Secured("ROLE_ADMIN")
    void deleteAnalise(@PathVariable Long id) {
        analiseService.deleteAnaliseById(id);
    }



    @GetMapping("/historicoAnalise")
    Page<AnaliseHistoricDto> loadAnaliseHistocial(@RequestParam("dataInicial") String dataInicial, @RequestParam("dataFinal") String dataFinal, Pageable pageable)  throws ParseException {
        Page<Analise> analises = repository.listaAnaliseByDataRange(dataInicial, dataFinal, pageable);
        if(analises.isEmpty()) {
           return Page.empty();
        }
        return AnaliseHistoricDto.analiseToAnaliseHitoricDto(analises,pageable);
    }

    @GetMapping("/analise/{dataInicial}/{dataFinal}/{parametroId}")
    AnaliseChartDTO loadSingleAnaliseChart(@PathVariable String dataInicial, @PathVariable String dataFinal, @PathVariable Long parametroId)  throws ParseException {
        List<Analise> analises = repository.listIndicador(dataInicial, dataFinal, parametroId);
        Parametro parametro = parametroRepository.findById(parametroId).get();
        if(analises.isEmpty()) {
           return new AnaliseChartDTO().emptyAnalisechartDTO(parametro);
        }
        return new AnaliseChartDTO().analisesToAnalisesChartDTO(repository.listIndicador(dataInicial, dataFinal, parametroId),dataInicial,dataFinal);
    }

    @GetMapping("/fullProcessoAnalises/{dataInicial}/{dataFinal}/{processoId}")
    List<AnaliseChartDTO> listFullProcessoAnaliseChart(@PathVariable String dataInicial, @PathVariable String dataFinal, @PathVariable Long processoId){
        Map<Parametro,List<Analise>> mapaParamListAnalises = new HashMap<>();
        processoRepository.findById(processoId).get().getEtapas().stream().forEach(etapa -> {
            etapa.getParametros().stream().forEach(parametro -> {
                mapaParamListAnalises.put(parametro,repository.listIndicador(dataInicial, dataFinal, parametro.getId()));
            });
        });
      
        List<AnaliseChartDTO> analisesChartDto = new ArrayList<>();

        mapaParamListAnalises.forEach((parametro,analises) -> {
            if(!analises.isEmpty()){
                if(parametro.isShowChart()) {
                    try {
                        analisesChartDto.add(new AnaliseChartDTO().analisesToAnalisesChartDTO(analises,dataInicial,dataFinal));
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } else {
                if(parametro.isShowChart()){
                    analisesChartDto.add(new AnaliseChartDTO().emptyAnalisechartDTO(parametro));
                }
                
            }
        });

        return analisesChartDto;

       
    }

    @GetMapping("/fullEtapaAnalises/{dataInicial}/{dataFinal}/{etapaId}")
    Set<AnaliseChartDTO> listFullEtapaAnalisechart(@PathVariable String dataInicial, @PathVariable String dataFinal, @PathVariable Long etapaId)  {
        Map<Parametro,List<Analise>> mapaParamListAnalises = new HashMap<>();
        Etapa etapa = eRepository.findById(etapaId).get();
        
        etapa.getParametros().stream().forEach(parametro -> {
            
                mapaParamListAnalises.put(parametro,repository.listIndicador(dataInicial, dataFinal, parametro.getId())); 
        });



        Set<AnaliseChartDTO> analisesChartDto = new HashSet<>();

        mapaParamListAnalises.forEach((parametro,analises) ->  {
            if(!analises.isEmpty()){
                if(parametro.isShowChart()){
                    try {
                        analisesChartDto.add(new AnaliseChartDTO().analisesToAnalisesChartDTO(analises,dataInicial,dataFinal));
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
              
            } else {
                if(parametro.isShowChart()) {
                    analisesChartDto.add(new AnaliseChartDTO().emptyAnalisechartDTO(parametro));
                }
             
            }
        });

     
        

        return analisesChartDto;

  
        
    }

}
