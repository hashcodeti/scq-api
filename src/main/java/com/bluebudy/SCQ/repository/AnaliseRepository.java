package com.bluebudy.SCQ.repository;

import java.util.List;

import com.bluebudy.SCQ.domain.Analise;

import org.springframework.data.jpa.repository.JpaRepository;


public interface AnaliseRepository extends JpaRepository<Analise,Long> , AnaliseRepositoryCustom {
	
    public List<Analise> findAnaliseByParametroId(Long parametroId);
    
}
