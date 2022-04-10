/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.repository;

import com.bluebudy.SCQ.domain.MontagemComposition;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Alexandre
 */
public interface MontagemCompositionRepository extends JpaRepository<MontagemComposition, Long>{
 
    
    List<MontagemComposition> findByEtapaId(Long etapaId);
}
