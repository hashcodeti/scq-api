package com.bluebudy.SCQ.repository;

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

import com.bluebudy.SCQ.domain.TarefasDeManutencao;

public class TarefasDeManutencaoRepositoryImpl implements TarefasDeManutencaoRepositoryCustom {
	
	@PersistenceContext
    private EntityManager em;
	
	   @Override
	    public List<TarefasDeManutencao> findTarefaOfDay() {
	        
	        Calendar cal = Calendar.getInstance();
	        Date dataAtual = cal.getTime();
	        
	        
	   

	        CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<TarefasDeManutencao> cq = cb.createQuery(TarefasDeManutencao.class);
	        Root<TarefasDeManutencao> tarefa = cq.from(TarefasDeManutencao.class);

	        ParameterExpression<Date> di = cb.parameter(Date.class);
	       

	        cq.select(tarefa).where(cb.lessThan(tarefa.get("controle").get("dataPlanejada"), di));
	        

	        TypedQuery<TarefasDeManutencao> query = em.createQuery(cq);
	       
	        query.setParameter(di, dataAtual);

	        List<TarefasDeManutencao> results = query.getResultList();
	        return results;
	      
	      
	    }

}
