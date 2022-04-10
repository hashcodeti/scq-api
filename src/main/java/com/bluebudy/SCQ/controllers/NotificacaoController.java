/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import com.bluebudy.SCQ.constantes.StatusNotificacao;
import com.bluebudy.SCQ.domain.Acao;
import com.bluebudy.SCQ.domain.Analise;
import com.bluebudy.SCQ.domain.Controle;
import com.bluebudy.SCQ.domain.Notificacao;
import com.bluebudy.SCQ.domain.Parametro;
import com.bluebudy.SCQ.domain.TarefasDeManutencao;
import com.bluebudy.SCQ.domain.Troca;
import com.bluebudy.SCQ.dtos.AnaliseDTO;
import com.bluebudy.SCQ.notifications.Mails;
import com.bluebudy.SCQ.notifications.NotificacaoAnalise;
import com.bluebudy.SCQ.notifications.NotificacaoTarefa;
import com.bluebudy.SCQ.notifications.NotificacaoTroca;
import com.bluebudy.SCQ.repository.AcaoRepository;
import com.bluebudy.SCQ.repository.AnaliseRepository;
import com.bluebudy.SCQ.repository.ControleRepository;
import com.bluebudy.SCQ.repository.NotificacaoRepository;
import com.bluebudy.SCQ.repository.ParametroRepository;
import com.bluebudy.SCQ.repository.TarefasDeManutencaoRepository;
import com.bluebudy.SCQ.repository.TrocaRepository;
import com.bluebudy.SCQ.services.FileService;
import com.bluebudy.SCQ.services.ScqMailSupportService;

import org.docx4j.org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alexandre
 */

@RestController
public class NotificacaoController {
    
    @Autowired
    private NotificacaoRepository repository;
    
    @Autowired
    private ParametroRepository parametroRepo;
    
    @Autowired
    private AcaoRepository acaoRepo;

    @Autowired
    private AnaliseRepository analiseRepo;
    
    @Autowired
    private TrocaRepository trocaRepository;
    
    @Autowired
    private TarefasDeManutencaoRepository tarefaRepository;
    
    @Autowired
    private ControleRepository frequenciaRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private ScqMailSupportService mailService;
   
    @Autowired
	private SimpMessagingTemplate template;

   
    //"0 59 23 * * MON-FRI"
    
    @Scheduled(cron = "0 59 23 * * MON-FRI")
    public void evalutateNotifications() throws FileNotFoundException, IOException, MessagingException{
        Date date = new Date();
        verifyAnalise(date);
    } 
    
    public void verifyTroca(Date date) {
       List<Troca> trocas = trocaRepository.findTrocaAtrasada();
       trocas.stream().map(freq -> new NotificacaoTroca(freq,repository,trocaRepository)).collect(Collectors.toList());
       System.out.println("Genrou Notificacao Frequencia Troca em: " + date.toString()); 
    }
    
    
    public void verifyAnalise(Date date) throws FileNotFoundException, IOException, MessagingException {
        List<Parametro> parametroExpired = parametroRepo.findparametroByFrequencia();
        List<NotificacaoAnalise> notificacoes =  parametroExpired.stream().map(parametro -> new NotificacaoAnalise(parametro,repository,frequenciaRepository)).collect(Collectors.toList());   
        File file = fileService.generateGenericSpreadSheet(notificacoes);
        FileInputStream out = new FileInputStream(file);
		byte[] documentBody = IOUtils.toByteArray(out);
        System.out.println("Analisou Notificacao Analise  em: " + date.toString()); 
        HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_PDF);
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
		header.setAccessControlExposeHeaders(List.of("Access-Control-Expose-Headers", "Content-Disposition"));
		header.setContentLength(documentBody.length);
        mailService.sendMail(Mails.dailyReporService, "Report diario de Analise nao realizadas", "Este e-mail é gerado automaticamente" + date.toString(), "confirmationUrl", "appUrl", file);
        
    }

    @GetMapping("/notificacao/regulariza")
    public ResponseEntity<List<AnaliseDTO>> regularizaAnalises() {
        Calendar dataAtual = Calendar.getInstance();
        dataAtual.add(Calendar.HOUR_OF_DAY,-24);
        List<Parametro> parametroExpired = parametroRepo.findparametroByFrequencia();
        List<Parametro> parametroToGenerate = parametroExpired.stream().filter(parametro -> {
           return parametro.getControle().getDataPlanejada().before(dataAtual.getTime());
        }).collect(Collectors.toList());
        List<AnaliseDTO> analises = parametroToGenerate.stream().map(param -> {
            Analise analise = new Analise();
            analise.setAnalista("beatriz");
            analise.setData(param.getControle().getDataPlanejada());
            analise.setNeedCorrecao(false);
            analise.setObservcao("ponto de corte");
            analise.setParametro(param);
            Double resultado = (param.getpMax() + param.getpMin() / 2);
            BigDecimal bDResultado = new BigDecimal(resultado);
            bDResultado.setScale(2,RoundingMode.HALF_EVEN);
            analise.setResultado(bDResultado.doubleValue());
            return new AnaliseDTO(analise);
        }).collect(Collectors.toList());
        return ResponseEntity.ok(analises);
    }
    
    private void verifyTarefa(Date date) {
        List<TarefasDeManutencao> tarefas = tarefaRepository.findTarefaOfDay();
        tarefas.stream().map(freq -> new NotificacaoTarefa(freq,repository,tarefaRepository)).collect(Collectors.toList());     
        System.out.println("Analisou Notificacao Tarefa  em: " + date.toString()); 
    }
    
    
    @GetMapping("/notificacoes")
    public List<Notificacao> getNotificacoes(){
        return repository.findNotificacoes();
        
    }
    
    @PutMapping("/notificacao/{id}")
    @ResponseBody
    public ResponseEntity<?> resolveNotificacao(@PathVariable Long id){
    	Date date = new Date();
        Notificacao notificacao = repository.findById(id).get();
        String tipo = notificacao.getTipoNotificacao();
        if(tipo.equals("Analise")){
            Controle freqAnalise = frequenciaRepository.findById(notificacao.getRefId()).get();
            
            if(freqAnalise.getDataPlanejada().getTime()>date.getTime()){
                notificacao.setStatus(StatusNotificacao.RESOLVIDO);
                repository.save(notificacao);
                return ResponseEntity.ok("success:Notificação: " + notificacao.getId()+ " " + tipo + " resolvida com sucesso ");
            } else {
                 return  ResponseEntity.ok("warning:Notificação: " + notificacao.getId() + " Analise ainda esta pendente, favor analisar antes de marcar resolvido");
            }
        
        } 
        if (tipo.equals("Troca")) {
            Troca troca = trocaRepository.findById(notificacao.getRefId()).get();
          
            if(troca.getControle().getDataPlanejada().getTime()>date.getTime()){
                notificacao.setStatus(StatusNotificacao.RESOLVIDO);
                repository.save(notificacao);
                 return ResponseEntity.ok("success:Notificação: " + notificacao.getId()+ " " + tipo + " resolvida com sucesso ");
            } else {
                return ResponseEntity.ok("warning:Notificação: " + notificacao.getId() + " Troca ainda esta pendente favor trocar antes de marcar resolvido");
            }
            
           
        }
        
        if (tipo.equals("Tarefa")) {
            TarefasDeManutencao troca = tarefaRepository.findById(notificacao.getRefId()).get();
          
            if(troca.getControle().getDataPlanejada().getTime()>date.getTime()){
                notificacao.setStatus(StatusNotificacao.RESOLVIDO);
                repository.save(notificacao);
                 return ResponseEntity.ok("success:Notificação: " + notificacao.getId()+ " " + tipo + " resolvida com sucesso ");
            } else {
                return ResponseEntity.ok("warning:Notificação: " + notificacao.getId() + " Tarefa ainda esta pendente favor realizar antes de marcar resolvido");
            }
            
           
        }
        if (tipo.equals("Acao")) {
            Acao acao = acaoRepo.findById(notificacao.getRefId()).get();
           
            if(acao.getPrazo().getTime()>date.getTime()){
                notificacao.setStatus(StatusNotificacao.RESOLVIDO);
                repository.save(notificacao);
                 return ResponseEntity.ok("success:Notificação: " + notificacao.getId()+ " " + tipo + " resolvida com sucesso ");
            } else {
                return ResponseEntity.ok("warning:Notificação: " + notificacao.getId() + " Acao ainda esta pendente favor solucionar antes de marcar resolvido");
            }
            
           
        }
        return ResponseEntity.ok().build();
       
        
    }

    
    
}
