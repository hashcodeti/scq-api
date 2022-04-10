package com.bluebudy.SCQ.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import com.bluebudy.SCQ.domain.Parametro;

public class ParametroRepositoryImpl implements ParametroRepositoryCustom {
	@PersistenceContext
    private EntityManager em;

	@Override
	public List<Parametro> findparametroByFrequencia() {
		
		Date dataPlanejada = new Date();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
	    CriteriaQuery<Parametro> cq = cb.createQuery(Parametro.class);
	    
	    Root<Parametro> parametro = cq.from(Parametro.class);
	    
	    ParameterExpression<Date> data = cb.parameter(Date.class);
	    cq.select(parametro).where(cb.lessThan(parametro.get("controle").get("dataPlanejada"), data));
	    
	    
	    
	    
	    TypedQuery<Parametro> query = em.createQuery(cq);
	    query.setParameter(data, dataPlanejada);
	    List<Parametro> results =  query.getResultList();
		return results;
	}

}
