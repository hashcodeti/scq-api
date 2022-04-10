package com.bluebudy.SCQ.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bluebudy.SCQ.domain.Analise;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class AnaliseRepositoryImpl implements AnaliseRepositoryCustom {
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Analise LastAnaliseResult(String dataToken) {
		
		Long parametroId = Long.valueOf(StringUtils.removeStart(dataToken, "da"));
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
	    CriteriaQuery<Analise> cq = cb.createQuery(Analise.class);
	    
	    Root<Analise> analise = cq.from(Analise.class);
	    
	    ParameterExpression<Long> p = cb.parameter(Long.class);
	    cq.select(analise).where(cb.equal(analise.get("parametro").get("id"), p));
	    cq.orderBy(cb.desc(analise.get("data")));
	    
	    
	    
	    TypedQuery<Analise> query = em.createQuery(cq);
	    query.setParameter(p, parametroId);
	    query.setMaxResults(1);
	    query.setFirstResult(0);
	    Analise results =  query.getSingleResult();
		return results;
	}

	@Override
	public List<Analise> findAnalisesById(Long id) {
		
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
	    CriteriaQuery<Analise> cq = cb.createQuery(Analise.class);
	    
	    Root<Analise> analise = cq.from(Analise.class);
	    
	    ParameterExpression<Long> p = cb.parameter(Long.class);
	    cq.select(analise).where(cb.equal(analise.get("parametro").get("id"), p));
	    
	    
	    
	    TypedQuery<Analise> query = em.createQuery(cq);
	    query.setParameter(p, id);
	    List<Analise> results =  query.getResultList();
		return results;
	}


	@Override
	public List<Analise> listIndicador(String dataInicial, String dataFinal,Long parametroId) {
		Date inicialdata = null;
		Date finaldata = null;
		try {
			inicialdata = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dataInicial);
			finaldata  = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dataFinal);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaQuery<Analise> cq = cb.createQuery(Analise.class);
	    Root<Analise> analise = cq.from(Analise.class);
	    
	    ParameterExpression<Long> parametro = cb.parameter(Long.class);
	    ParameterExpression<Date> di = cb.parameter(Date.class);
	    ParameterExpression<Date> df = cb.parameter(Date.class);
	    
	    cq.select(analise).where(cb.and(cb.equal(analise.get("parametro").get("id"),parametro),cb.greaterThanOrEqualTo(analise.get("data"),di),cb.lessThanOrEqualTo(analise.get("data"),df)));
	    cq.orderBy(cb.asc(analise.get("data")));
	 
	    
	    
	    TypedQuery<Analise> query = em.createQuery(cq);
	    query.setParameter(parametro, parametroId);
	    query.setParameter(di, inicialdata);
	    query.setParameter(df, finaldata);
	    List<Analise> results = query.getResultList();
		return results;
		

	}



	@Override
	public List<Analise> listaAnaliseByDataRange(String dataInicial, String dataFinal) {
		Date inicialdata = null;
		Date finaldata = null;
		try {
			inicialdata = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dataInicial);
			finaldata  = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dataFinal);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaQuery<Analise> cq = cb.createQuery(Analise.class);
	    Root<Analise> analise = cq.from(Analise.class);
	    
	    ParameterExpression<Date> di = cb.parameter(Date.class);
	    ParameterExpression<Date> df = cb.parameter(Date.class);
	    
	    cq.select(analise).where(cb.and(cb.greaterThanOrEqualTo(analise.get("data"),di),cb.lessThanOrEqualTo(analise.get("data"),df)));
	    cq.orderBy(cb.asc(analise.get("data")));
	 
	    
	    
	    TypedQuery<Analise> query = em.createQuery(cq);
	    query.setParameter(di, inicialdata);
	    query.setParameter(df, finaldata);
	    List<Analise> results = query.getResultList();
		return results;
		

	}


	

	@Override
	public Integer getNumberOfAnalises(Long parametroId) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaQuery<Analise> cq = cb.createQuery(Analise.class);
	    
	    Root<Analise> analise = cq.from(Analise.class);
	    
	    ParameterExpression<Long> p = cb.parameter(Long.class);
	    cq.select(analise).where(cb.equal(analise.get("parametro").get("id"), p));
	    cq.orderBy(cb.desc(analise.get("data")));
	    
	    
	    
	    TypedQuery<Analise> query = em.createQuery(cq);
	    query.setParameter(p, parametroId);
	    List<Analise> results =  query.getResultList();
	    
		return results.size();
		
	}

	@Override
	public Analise findLastAnaliseByParamId(Long parametroId) {
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
	    CriteriaQuery<Analise> cq = cb.createQuery(Analise.class);
	    
	    Root<Analise> analise = cq.from(Analise.class);
	    
	    ParameterExpression<Long> p = cb.parameter(Long.class);
	    cq.select(analise).where(cb.equal(analise.get("parametro").get("id"), p));
	    cq.orderBy(cb.desc(analise.get("data")));
	    
	    
	    
	    TypedQuery<Analise> query = em.createQuery(cq);
	    query.setParameter(p, parametroId);
	    query.setMaxResults(1);
	    query.setFirstResult(0);
	    Analise results =  query.getSingleResult();
		return results;
	}
	


	boolean checkIfIsSameDay(Calendar data1, Calendar data2) {
		if((data1.get(Calendar.YEAR) == data2.get(Calendar.YEAR)) && data1.get(Calendar.DAY_OF_YEAR) == data2.get(Calendar.DAY_OF_YEAR)){
			return true;
		}
			return false;
	}

	@Override
	public Page<Analise> listaAnaliseByDataRange(String dataInicial, String dataFinal, Pageable pageable) {
		Date inicialdata = null;
		Date finaldata = null;
		try {
			inicialdata = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dataInicial);
			finaldata  = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dataFinal);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CriteriaBuilder builder =  em.getCriteriaBuilder();
        CriteriaQuery<Analise> criteria = builder.createQuery(Analise.class);
        Root<Analise> analiseRoot = criteria.from(Analise.class);
        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(builder.greaterThanOrEqualTo(analiseRoot.get("data"), inicialdata));
        predicates.add(builder.lessThanOrEqualTo(analiseRoot.get("data"), finaldata));
        criteria.where(builder.and(predicates.toArray( new Predicate[predicates.size()])));
        criteria.orderBy(builder.asc(analiseRoot.get("data")));

        // This query fetches the Books as per the Page Limit
        List<Analise> result = em.createQuery(criteria).setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();

        // Create Count Query
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<Analise> booksRootCount = countQuery.from(Analise.class);
        countQuery.select(builder.count(booksRootCount)).where(builder.and(predicates.toArray(new Predicate[predicates.size()])));

        // Fetches the count of all Books as per given criteria
        Long count = em.createQuery(countQuery).getSingleResult();

        Page<Analise> result1 = new PageImpl<>(result, pageable, count);
        return result1;
	}


}
