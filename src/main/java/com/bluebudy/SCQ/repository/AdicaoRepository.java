/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.repository;

import java.util.Date;
import java.util.List;

import com.bluebudy.SCQ.domain.Adicao;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Alexandre
 */
public interface AdicaoRepository extends JpaRepository<Adicao, Long> {
 
    
    public List<Adicao> findByOrdemDeCorrecaoId(Long ordemId);
    public List<Adicao> findByOrdemDeCorrecaoAnaliseParametroEtapaProcessoIdAndDataBetween(Long processoId, Date fromDate, Date toDate);
}
