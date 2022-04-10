/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.repository;

import com.bluebudy.SCQ.domain.Acao;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Alexandre
 */
public interface AcaoRepository extends JpaRepository<Acao, Long> , AcaoRepositoryCustom {
    
    public Optional<Acao> findByOrdemDeCorrecaoId(Long ordemId);  
}
