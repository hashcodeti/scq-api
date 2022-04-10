package com.bluebudy.SCQ.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.bluebudy.SCQ.domain.Etapa;
import com.bluebudy.SCQ.domain.Processo;
import com.bluebudy.SCQ.repository.ProcessoRepository;

public class EtapaForm {

	@NotNull
	@NotBlank
	private String nome;
	@NotNull
	private Integer posicao;
	@NotNull
	private Long processoId;
	@NotNull
	private Double volume;
	@NotNull
	@NotBlank
	

	public EtapaForm() {

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getPosicao() {
		return posicao;
	}

	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}

	public Long getProcessoId() {
		return processoId;
	}

	public void setProcessoId(Long processoId) {
		this.processoId = processoId;
	}

	
	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Etapa generateEtapa(ProcessoRepository processoRepo) {
		Processo processo = processoRepo.findById(processoId).get();
		Etapa etapa = new Etapa();
		etapa.setNome(nome);
		etapa.setPosicao(posicao);
		etapa.setVolume(volume);

		etapa.setProcesso(processo);

		return etapa;
	}

	public Processo findProcesso(ProcessoRepository repository) {
		return repository.findById(processoId).get();
	}

}
