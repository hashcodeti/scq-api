package com.bluebudy.SCQ.repository;

import java.util.Date;
import java.util.List;

import com.bluebudy.SCQ.domain.OrdemDeManutencao;

public interface OmpRepositoryCustom {

	List<OrdemDeManutencao> findByRealizadoEm(Date dataRealizada);
}
