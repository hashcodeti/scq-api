package com.bluebudy.SCQ.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bluebudy.SCQ.constantes.StatusNotificacao;
import com.bluebudy.SCQ.domain.Acao;
import com.bluebudy.SCQ.domain.Notificacao;
import com.bluebudy.SCQ.domain.Parametro;
import com.bluebudy.SCQ.domain.TarefasDeManutencao;
import com.bluebudy.SCQ.domain.Troca;
import com.bluebudy.SCQ.dtos.FunctionWsModelDTO;
import com.bluebudy.SCQ.repository.NotificacaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService  {

	@Autowired
	private SimpMessagingTemplate template;

	public void resolveNotifications(String type, JpaRepository repository, Long id,
			NotificacaoRepository notificationRepo) {
		if (type == "Analise") {
			resolveNotificationsByRedId(id, notificationRepo);

		}
		if (type == "Troca") {
			Troca troca = (Troca) repository.findById(id).get();
			resolveNotificationsByRedId(troca.getId(), notificationRepo);
		}

		if (type == "Tarefa") {
			TarefasDeManutencao tarefa = (TarefasDeManutencao) repository.findById(id).get();
			resolveNotificationsByRedId(tarefa.getId(), notificationRepo);
		}
		if (type == "Acao") {
			Acao acao = (Acao) repository.findById(id).get();
			resolveNotificationsByRedId(acao.getId(), notificationRepo);
		}

	}

	public void resolveNotificationsByRedId(Long refId, NotificacaoRepository notificationRepo) {
		List<Notificacao> notifications = notificationRepo.findByRefIdAguardando(refId);
		notifications.forEach(notification -> {
			notification.setStatus(StatusNotificacao.RESOLVIDO);
			notificationRepo.save(notification);
		});
	}

	public void updadteUserNotificationUi(List<String> functionName) {
		FunctionWsModelDTO jsonObje = new FunctionWsModelDTO("function", functionName, null);
		this.template.convertAndSend("/reducer/return", jsonObje);
	}

	public boolean checkIfParameterIsLate(Parametro parametro) {
		if((parametro.getControle().getDataPlanejada() == null) || parametro.getControle().getDataRealizada()  == null) {
			return true;
		}
		Calendar dataParametro = Calendar.getInstance();
		dataParametro.setTime(parametro.getControle().getDataPlanejada());
		Calendar dataAtual = Calendar.getInstance();
		dataAtual.setTime(new Date());
		Long periodo = parametro.getControle().getPeriodo() * 6000;

		Long rangePeriod = dataParametro.getTimeInMillis() - dataAtual.getTimeInMillis();
		if(dataParametro.before(dataAtual)){
			return true;
		} else if(rangePeriod < periodo) {
			return true;
		} else {
			return false;
		}

		
	}

}
