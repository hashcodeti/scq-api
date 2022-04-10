package com.bluebudy.SCQ.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bluebudy.SCQ.domain.Etapa;

public interface EtapaRepository extends JpaRepository<Etapa,Long> {
	
	
	 @Query("SELECT e FROM Etapa e WHERE e.processo.id = :processoId") 
	 public List<Etapa> findEtapaByProcesso(@Param("processoId") long processoId);
	 public Etapa findEtapaByParametrosId(Long parametroId);

}
