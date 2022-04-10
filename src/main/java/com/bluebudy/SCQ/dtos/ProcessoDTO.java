package com.bluebudy.SCQ.dtos;


import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;


import com.bluebudy.SCQ.domain.Processo;


public class ProcessoDTO implements Serializable {
	
	private Long id;
	private String nome;
	private List<Long> etapasId;
	
	public ProcessoDTO(Processo processo) {
		this.id = processo.getId();
		this.nome = processo.getNome();
               
		this.etapasId = processo.getEtapas()==null ? null : processo.getEtapas().stream().map(e -> e.getId()).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Long> getEtapasId() {
		return etapasId;
	}

	public void setEtapasId(List<Long> etapasId) {
		this.etapasId = etapasId;
	}
	
	public static List<ProcessoDTO> processosToProcessosDTO(List<Processo> processos) {
		return processos.stream().map(ProcessoDTO::new).sorted((dto1,dto2) -> dto1.getNome().compareTo(dto2.getNome())).collect(Collectors.toList());
	}
	
	
}
