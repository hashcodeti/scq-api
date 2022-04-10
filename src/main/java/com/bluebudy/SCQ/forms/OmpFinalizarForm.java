/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.forms;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.bluebudy.SCQ.repository.NotificacaoRepository;
import com.bluebudy.SCQ.repository.OmpRepository;
import com.bluebudy.SCQ.repository.TarefasDeManutencaoRepository;
import com.bluebudy.SCQ.repository.TrocaRepository;
import com.bluebudy.SCQ.services.NotificationService;
import com.bluebudy.SCQ.utils.DateUtils;

/**
 *
 * @author Alexandre
 */
public class OmpFinalizarForm {

    private Long id;
    private Long[] tarefasId;
    private Long[] trocasId;
    private String data;


    public void updateTrocasAndTarefas(OmpRepository repository, TrocaRepository trocaRepo, TarefasDeManutencaoRepository tarefaRepo, NotificationService notificationService, NotificacaoRepository notificationRepository) {
        repository.findById(id).map(omp -> {
            try {
                omp.setRealizadoEm(DateUtils.getDateTimeFromString(data));
                if(omp.getTarefasManutencaoId()!=null) {
                	  omp.setTarefasManutencaoId(updateTarefaId(omp.getTarefasManutencaoId()));
                }
              
                if(omp.getTrocasId()!=null){
                     omp.setTrocasId(updateTrocasId(omp.getTrocasId()));
                }
               
            } catch (ParseException ex) {
                Logger.getLogger(OmpFinalizarForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            return omp;
        }
        );

        for (Long tarefaId : tarefasId) {
            tarefaRepo.findById(tarefaId).map(tarefa -> {
                Calendar calendar = Calendar.getInstance();
                try {
                    calendar.setTime(data != null ? DateUtils.getDateTimeFromString(data) : new Date());
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
				tarefa.getControle().setDataRealizada(calendar.getTime());
				calendar.add(Calendar.MINUTE, tarefa.getControle().getPeriodo().intValue());
				Date newDataPlanejada = calendar.getTime();
                
				tarefa.getControle().setDataPlanejada(newDataPlanejada);
				tarefa.getControle().setNotified(false);
                notificationService.resolveNotifications("Tarefa", tarefaRepo, tarefaId, notificationRepository);
                return tarefaRepo.saveAndFlush(tarefa);
            }).get();
        }

        for (Long trocaId : trocasId) {
            trocaRepo.findById(trocaId).map(troca -> {
                Calendar calendar = Calendar.getInstance();
                try {
                    calendar.setTime(data != null ? DateUtils.getDateTimeFromString(data) : new Date());
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
				troca.getControle().setDataRealizada(calendar.getTime());
				calendar.add(Calendar.MINUTE, troca.getControle().getPeriodo().intValue());
				Date newDataPlanejada = calendar.getTime();
   				troca.getControle().setDataPlanejada(newDataPlanejada);
				troca.getControle().setNotified(false);
                notificationService.resolveNotifications("Troca", trocaRepo, trocaId, notificationRepository);
                return trocaRepo.saveAndFlush(troca);
            }).get();
        }

        notificationService.updadteUserNotificationUi(List.of("loadNotifications","loadTrocas","loadTarefasDeManutencao"));
        updateTrocaTarefaArrays();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long[] getTarefasId() {
        return tarefasId;
    }

    public void setTarefasId(Long[] tarefasId) {
        this.tarefasId = tarefasId;
    }

    public Long[] getTrocasId() {
        return trocasId;
    }

    public void setTrocasId(Long[] trocasId) {
        this.trocasId = trocasId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private void updateTrocaTarefaArrays() {

    }

    private String updateTarefaId(String tarefasManutencaoId) {
        boolean contains = false;
        List<Long> ompTarefasId = buildLongId(tarefasManutencaoId);
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < ompTarefasId.size(); i++) {
            for (int j = 0; j < tarefasId.length; j++) {
                if (ompTarefasId.get(i) == tarefasId[j]) {
                    contains = true;
                }
            }
            if (contains) {
                sbf.append(ompTarefasId.get(i));
                sbf.append(":");
                sbf.append("OK");
                sbf.append(",");

            } else {
                sbf.append(ompTarefasId.get(i));
                sbf.append(":");
                sbf.append("NOK");
                sbf.append(",");
            }
            contains=false;
            
        }
         sbf.setLength(sbf.length()-1);
        return sbf.toString();
    }

    private List<Long> buildLongId(String string) {
        String[] stringTokens = string.split(",");
        return Arrays.asList(stringTokens).stream().map(token -> {
            return Long.valueOf(token);
        }).collect(Collectors.toList());
    }

    private String updateTrocasId(String oldtrocasId) {
       boolean contains = false;
        List<Long> ompTrocasId = buildLongId(oldtrocasId);
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < ompTrocasId.size(); i++) {
            for (int j = 0; j < trocasId.length; j++) {
                if (ompTrocasId.get(i) == trocasId[j]) {
                    contains = true;
                }
            }
            if (contains) {
                sbf.append(ompTrocasId.get(i));
                sbf.append(":");
                sbf.append("OK");
                sbf.append(",");

            } else {
                sbf.append(ompTrocasId.get(i));
                sbf.append(":");
                sbf.append("NOK");
                sbf.append(",");
            }
            contains=false;
            
        }
        sbf.setLength(sbf.length()-1);
        return sbf.toString();
    }

}
