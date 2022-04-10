package com.bluebudy.SCQ.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bluebudy.SCQ.domain.OrdemDeCorrecao;


public interface OrdemDeCorrecaoRepository extends JpaRepository<OrdemDeCorrecao,Long>{
	
	 @Query("SELECT o FROM OrdemDeCorrecao o WHERE o.analise.needCorrecao = true OR o.statusOCP = false") 
	 public List<OrdemDeCorrecao> listOcps();

	 public List<OrdemDeCorrecao> findByAnaliseParametroId(Long parametroId);

}
