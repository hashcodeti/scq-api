/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.repository;

import com.bluebudy.SCQ.domain.TarefasDeManutencao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Alexandre
 */
public interface TarefasDeManutencaoRepository  extends JpaRepository<TarefasDeManutencao, Long>, TarefasDeManutencaoRepositoryCustom{
    
    public List<TarefasDeManutencao> findByProcessoId(Long processoId);
}
