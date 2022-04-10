package com.bluebudy.SCQ.notifications;

import com.bluebudy.SCQ.constantes.StatusNotificacao;
import com.bluebudy.SCQ.domain.Acao;
import com.bluebudy.SCQ.domain.Notificacao;
import com.bluebudy.SCQ.repository.AcaoRepository;
import com.bluebudy.SCQ.repository.NotificacaoRepository;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alexandre
 */
public class NotificacaoAcao {
    
    
    


    private Acao acao;

    public NotificacaoAcao() {
    }
   

    
    
    public NotificacaoAcao(Acao acao, NotificacaoRepository repository, AcaoRepository acaoRepo) {
        this.acao = acao;
        criarNotificacao(repository, acaoRepo);
    }

    
    
    
 
    public synchronized void criarNotificacao(NotificacaoRepository repository , AcaoRepository acaoRepo) {
       if(!acao.isNotified()){
       Notificacao notificacao = new Notificacao();
       StringBuilder sb = new StringBuilder();
       sb.append("Acao: Etapa ");
       sb.append(acao.getOrdemDeCorrecao().getAnalise().getParametro().getEtapa().getNome());
       sb.append(" problema de parametro ");
       sb.append(acao.getOrdemDeCorrecao().getAnalise().getParametro().getNome());
       sb.append(" processo ");
       sb.append(acao.getOrdemDeCorrecao().getAnalise().getParametro().getEtapa().getProcesso().getNome());
       sb.append(" esta atrasado ");
       notificacao.setMessagem(sb.toString());
       notificacao.setStatus(StatusNotificacao.AGUARDANDO); 
       notificacao.setRefId(acao.getId());
       notificacao.setTipoNotificacao("acao");
       acao.setNotified(true);
       acaoRepo.save(acao);
       repository.save(notificacao);
       }
      
       
       
      
      
    }
    
}
