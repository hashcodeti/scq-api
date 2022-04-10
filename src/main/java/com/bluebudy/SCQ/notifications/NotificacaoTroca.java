/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.notifications;

import com.bluebudy.SCQ.constantes.StatusNotificacao;
import com.bluebudy.SCQ.domain.Notificacao;
import com.bluebudy.SCQ.domain.Troca;
import com.bluebudy.SCQ.repository.NotificacaoRepository;
import com.bluebudy.SCQ.repository.TrocaRepository;

/**
 *
 * @author Alexandre
 */


public class NotificacaoTroca  {

    
   
    private Troca troca;

    public NotificacaoTroca() {
    }
   

    
    
    public NotificacaoTroca(Troca troca, NotificacaoRepository repository, TrocaRepository trocaRepo) {
        this.troca = troca;
        criarNotificacao(repository,trocaRepo);
    }

    
    
    
 
    public synchronized void criarNotificacao(NotificacaoRepository repository, TrocaRepository trocaRepo) {
       
       if(!troca.getControle().isNotified()){
       Notificacao notificacao = new Notificacao();
       StringBuilder sb = new StringBuilder();
       sb.append("Troca: da etapa ");
       sb.append(troca.getEtapa().getNome());
       sb.append(" Tanque ");
       sb.append(troca.getEtapa().getPosicao());
       sb.append(" processo " + troca.getEtapa().getProcesso().getNome());
       sb.append(" Motado com: ");
       troca.getEtapa().getMontagem().stream().forEach(mc -> {sb.append(" - "); sb.append(mc.getMateriaPrima().getNome()); sb.append(" "); sb.append(mc.getQuantidade());});
       notificacao.setMessagem(sb.toString());
       notificacao.setStatus(StatusNotificacao.AGUARDANDO);
       notificacao.setTipoNotificacao("Troca");
       notificacao.setRefId(troca.getId());
       troca.getControle().setNotified(Boolean.TRUE);
       trocaRepo.saveAndFlush(troca);
       repository.save(notificacao);
      
       }
     
    }
    
    
    
}
