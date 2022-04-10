/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.repository;

import java.util.Date;
import java.util.List;

import com.bluebudy.SCQ.domain.OrdemDeManutencao;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Alexandre
 */
public interface OmpRepository extends JpaRepository<OrdemDeManutencao, Long> ,OmpRepositoryCustom {

    public List<OrdemDeManutencao> findByProcessoIdAndRealizadoEmBetween(Long processoId, Date fromDate, Date toDate);
    
    
}
