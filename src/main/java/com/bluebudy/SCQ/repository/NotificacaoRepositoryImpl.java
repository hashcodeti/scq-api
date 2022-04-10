/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.repository;

import com.bluebudy.SCQ.domain.Notificacao;
import com.bluebudy.SCQ.constantes.StatusNotificacao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

/**
 *
 * @author Alexandre
 */


public class NotificacaoRepositoryImpl implements NotificacaoRepositoryCustom {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public List<Notificacao> findNotificacoes() {
     

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Notificacao> cq = cb.createQuery(Notificacao.class);
        Root<Notificacao> notificacao = cq.from(Notificacao.class);

        ParameterExpression<StatusNotificacao> status = cb.parameter(StatusNotificacao.class);
    

        cq.select(notificacao).where(cb.equal(notificacao.get("status"), status));

        TypedQuery<Notificacao> query = em.createQuery(cq);
       
        query.setParameter(status,StatusNotificacao.AGUARDANDO);

        List<Notificacao> results = query.getResultList();
        return results;
    }

	@Override
	public List<Notificacao> findByRefId(Long frequenciaId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Notificacao> cq = cb.createQuery(Notificacao.class);
        Root<Notificacao> notificacao = cq.from(Notificacao.class);

        ParameterExpression<Long> refId = cb.parameter(Long.class);
    

        cq.select(notificacao).where(cb.equal(notificacao.get("refId"), refId));

        TypedQuery<Notificacao> query = em.createQuery(cq);
       
        query.setParameter(refId,frequenciaId);

        List<Notificacao> results = query.getResultList();
		return results;
	}
	
	@Override
	public List<Notificacao> findByRefIdAguardando(Long frequenciaId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Notificacao> cq = cb.createQuery(Notificacao.class);
        Root<Notificacao> notificacao = cq.from(Notificacao.class);

        ParameterExpression<Long> refId = cb.parameter(Long.class);
        ParameterExpression<StatusNotificacao> status = cb.parameter(StatusNotificacao.class);
    

        cq.select(notificacao).where(cb.equal(notificacao.get("refId"), refId),cb.and(cb.equal(notificacao.get("status"),status )));

        TypedQuery<Notificacao> query = em.createQuery(cq);
       
        query.setParameter(refId,frequenciaId);
        query.setParameter(status, StatusNotificacao.AGUARDANDO);
        List<Notificacao> results = query.getResultList();
		return results;
	}

	
    
}
