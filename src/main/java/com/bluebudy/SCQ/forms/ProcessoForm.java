package com.bluebudy.SCQ.forms;


import com.bluebudy.SCQ.domain.Processo;
import java.io.Serializable;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class ProcessoForm implements Serializable {
	

	
	@NotBlank @NotNull
	private String nome;
	

	private Integer idEtapa;
	
	public ProcessoForm() {
		
	}


	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getIdEtapa() {
		return idEtapa;
	}

	public void setIdEtapa(Integer idEtapa) {
		this.idEtapa = idEtapa;
	}
        
        public  Processo generatreProcesso(){
           Processo processo = new Processo();
           processo.setNome(nome);

           return processo;
        }

	
	
}
