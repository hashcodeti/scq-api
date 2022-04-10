package com.bluebudy.SCQ.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;

import com.bluebudy.SCQ.domain.MateriaPrima;

public class MateriaPrimaRepositoryImpl implements MateriaPrimaRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public MateriaPrima searchMateriaPrima(String search) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MateriaPrima> cq = cb.createQuery(MateriaPrima.class);
        ParameterExpression<String> searchText = cb.parameter(String.class);
        TypedQuery<MateriaPrima> query = em.createQuery(cq);
        query.setParameter(searchText, search);
        MateriaPrima materiaPrima = query.getSingleResult();
        return materiaPrima;
    }

}
