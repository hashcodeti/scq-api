/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.repository;

import com.bluebudy.SCQ.domain.Acao;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Alexandre
 */
public class AcaoRepositoryImpl implements  AcaoRepositoryCustom {
    
    
    @Autowired
    private EntityManager em;

    @Override
    public List<Acao> findFrequenciaToDay(Date date) {
        Date dataPlanejada = new Date();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Acao> cq = cb.createQuery(Acao.class);
        Root<Acao> acao = cq.from(Acao.class);

        ParameterExpression<Date> di = cb.parameter(Date.class);
       

        cq.select(acao).where(cb.lessThan(acao.get("prazo"), di));

        TypedQuery<Acao> query = em.createQuery(cq);
       
        query.setParameter(di, dataPlanejada);
       
        List<Acao> results = query.getResultList();
        
        
        
        return results;
    }
    
   
}
