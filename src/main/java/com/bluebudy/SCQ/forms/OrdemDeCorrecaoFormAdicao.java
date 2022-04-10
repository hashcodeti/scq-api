/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.forms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;

import com.bluebudy.SCQ.constantes.TiposDeAcao;
import com.bluebudy.SCQ.constantes.Unidade;
import com.bluebudy.SCQ.domain.Adicao;
import com.bluebudy.SCQ.domain.Analise;
import com.bluebudy.SCQ.domain.MateriaPrima;
import com.bluebudy.SCQ.domain.OrdemDeCorrecao;
import com.bluebudy.SCQ.repository.AdicaoRepository;

/**
 *
 * @author Alexandre
 */
public class OrdemDeCorrecaoFormAdicao {
    
    private Long id;
    private Long parametroId;

    @NotBlank @NotNull
    private String responsavel;

    private String observacao;
    
    private Long analiseId;
  
    private String[] mpQtds;
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAnaliseId() {
		return analiseId;
	}

	public void setAnaliseId(Long analiseId) {
		this.analiseId = analiseId;
	}

	public Long getParametroId() {
		return parametroId;
	}

	public void setParametroId(Long parametroId) {
		this.parametroId = parametroId;
	}

	public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
   
    

  
    
    public String[] getMpQtds() {
		return mpQtds;
	}

	public void setMpQtds(String[] mpQtds) {
		this.mpQtds = mpQtds;
	}

	public OrdemDeCorrecao generateOrdemDeCorrecao(Analise analise) {
        OrdemDeCorrecao ordem = new OrdemDeCorrecao();
        ordem.setId(this.id );
        
        ordem.setResponsavel(this.responsavel);
        ordem.setObservacao(this.observacao);
        ordem.setStatusOCP(false);
        ordem.setTipo(TiposDeAcao.PREVENTIVA);
        ordem.setAnalise(analise);
        
        return ordem;
    }
    
    public List<Adicao> generateAdicao(OrdemDeCorrecao ocp) {
		
		List<String> mpQtdList = Arrays.asList(this.mpQtds);
		List<Adicao> newAdicoes = new ArrayList<Adicao>();
   
		mpQtdList.stream().forEach(pairMpQtd -> {
			String[] token = pairMpQtd.split(":");
			
			MateriaPrima mp = new MateriaPrima();
			mp.setId(Long.valueOf(token[0]));
            newAdicoes.add(new Adicao(null,Double.valueOf(token[1]),mp,ocp,Unidade.findEnumByValue(token[2]),false));
        });
		
        return newAdicoes;
    }
    
    public void updateAdicoes(AdicaoRepository addRepo){
    	for (int i = 0; i < mpQtds.length; i++) {
    		String[] tokens = mpQtds[i].split(":");
    		addRepo.findById(Long.valueOf(tokens[0])).map(adicao -> {
    			adicao.setQuantidade(Double.valueOf(tokens[1]));
    			return addRepo.save(adicao);
    		});
		}
    	
    }
	
    
    
    
    
    
}
