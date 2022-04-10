/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.repository;

import java.util.Date;
import java.util.List;

import com.bluebudy.SCQ.domain.Troca;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Alexandre
 */
public interface TrocaRepository  extends JpaRepository<Troca, Long>, TrocaRepositoryCustom{

    public List<Troca> findByEtapaProcessoId(Long processoId);
    public List<Troca> findByEtapaProcessoIdAndControleDataRealizadaBetween(Long processoId, Date fromDate, Date toDate);
    
}
