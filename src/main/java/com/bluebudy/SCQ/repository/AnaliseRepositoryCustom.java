package com.bluebudy.SCQ.repository;

import java.util.List;

import com.bluebudy.SCQ.domain.Analise;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnaliseRepositoryCustom {
	
	public Analise LastAnaliseResult(String dataToken);
	
	public List<Analise> listIndicador(String dataInicial, String dataFinal,Long parametroId);
	public Integer getNumberOfAnalises(Long parametroId);
	public Analise findLastAnaliseByParamId(Long parametroId);
	public List<Analise> findAnalisesById(Long id);
	public List<Analise> listaAnaliseByDataRange(String dataInicial, String dataFinal);
	public Page<Analise> listaAnaliseByDataRange(String dataInicial, String dataFinal, Pageable pageable);
}
