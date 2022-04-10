/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.notifications;

import com.bluebudy.SCQ.constantes.StatusNotificacao;
import com.bluebudy.SCQ.domain.Notificacao;
import com.bluebudy.SCQ.domain.TarefasDeManutencao;
import com.bluebudy.SCQ.repository.NotificacaoRepository;
import com.bluebudy.SCQ.repository.TarefasDeManutencaoRepository;

/**
 *
 * @author Alexandre
 */


public class NotificacaoTarefa  {

    
    private TarefasDeManutencao tarefa;

    public NotificacaoTarefa() {
    }
   

    
    
    public NotificacaoTarefa(TarefasDeManutencao tarefa, NotificacaoRepository repository, TarefasDeManutencaoRepository trocaRepo) {
        this.tarefa = tarefa;
        criarNotificacao(repository,trocaRepo);
    }

    
    

    public synchronized void criarNotificacao(NotificacaoRepository repository, TarefasDeManutencaoRepository tarefaRepo) {
       
       if(!tarefa.getControle().isNotified()){
       Notificacao notificacao = new Notificacao();
       StringBuilder sb = new StringBuilder();
       sb.append("Tarefa: do processo ");
       sb.append(tarefa.getProcesso().getNome());
       sb.append(" codigo da instruçao de trabalho ");
       sb.append(tarefa.getCodigoDoDocumento());
       notificacao.setMessagem(sb.toString());
       notificacao.setStatus(StatusNotificacao.AGUARDANDO);
       notificacao.setTipoNotificacao("Tarefa");
       notificacao.setRefId(tarefa.getId());
       tarefa.getControle().setNotified(Boolean.TRUE);
       tarefaRepo.saveAndFlush(tarefa);
       repository.save(notificacao);
     
       }
     
    }
    
    
    
}
