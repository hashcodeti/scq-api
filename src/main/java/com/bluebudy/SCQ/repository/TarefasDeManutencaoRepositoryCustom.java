package com.bluebudy.SCQ.repository;

import java.util.List;

import com.bluebudy.SCQ.domain.TarefasDeManutencao;

public interface TarefasDeManutencaoRepositoryCustom {
	
	List<TarefasDeManutencao> findTarefaOfDay();

}
