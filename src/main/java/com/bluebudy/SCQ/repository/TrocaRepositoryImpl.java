/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.repository;

import com.bluebudy.SCQ.domain.Troca;
import java.util.Calendar;
import java.util.Date;
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
public class TrocaRepositoryImpl implements TrocaRepositoryCustom{

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public List<Troca> findTrocaAtrasada() {
        
        Calendar cal = Calendar.getInstance();
        Date dataAtual = cal.getTime();
        
        
   

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Troca> cq = cb.createQuery(Troca.class);
        Root<Troca> troca = cq.from(Troca.class);

        ParameterExpression<Date> di = cb.parameter(Date.class);
       

        cq.select(troca).where(cb.lessThan(troca.get("controle").get("dataPlanejada"), di));
        

        TypedQuery<Troca> query = em.createQuery(cq);
       
        query.setParameter(di, dataAtual);

        List<Troca> results = query.getResultList();
        return results;
      
      
    }

    @Override
    public List<Troca> findTrocaByProcesso(Long paramId) {
     
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Troca> cq = cb.createQuery(Troca.class);
        Root<Troca> troca = cq.from(Troca.class);

        ParameterExpression<Long> processoId = cb.parameter(Long.class);
       

        cq.select(troca).where(cb.greaterThan(troca.get("etapa").get("processo").get("id"), processoId));

        TypedQuery<Troca> query = em.createQuery(cq);
       
        query.setParameter(processoId, paramId);

        List<Troca> results = query.getResultList();
        return results;
      
      
    }

    @Override
    public Troca findTrocaByEtapa(Long etapaId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Troca> cq = cb.createQuery(Troca.class);
        Root<Troca> troca = cq.from(Troca.class);

        ParameterExpression<Long> etapId = cb.parameter(Long.class);
       

        cq.select(troca).where(cb.equal(troca.get("etapa").get("id"), etapId));

        TypedQuery<Troca> query = em.createQuery(cq);
       
        query.setParameter(etapId, etapaId);

        Troca results = query.getSingleResult();
        return results; 
    }
    
    
    
    
    
}
