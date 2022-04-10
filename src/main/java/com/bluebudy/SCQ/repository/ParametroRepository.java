package com.bluebudy.SCQ.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.bluebudy.SCQ.domain.Parametro;

public interface ParametroRepository extends JpaRepository<Parametro, Long> , ParametroRepositoryCustom{

	
	
	 @Query("SELECT p FROM Parametro p WHERE p.etapa.id = :etapaId") 
	 public List<Parametro> findParametrosByEtapaId(@Param("etapaId") long etapaId);
	
}
