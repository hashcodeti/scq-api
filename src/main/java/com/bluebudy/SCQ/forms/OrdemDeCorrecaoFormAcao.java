/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.forms;

import java.text.ParseException;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.bluebudy.SCQ.constantes.TiposDeAcao;
import com.bluebudy.SCQ.domain.Acao;
import com.bluebudy.SCQ.domain.Analise;
import com.bluebudy.SCQ.domain.OrdemDeCorrecao;
import com.bluebudy.SCQ.repository.AcaoRepository;
import com.bluebudy.SCQ.utils.DateUtils;

/**
 *
 * @author Alexandre
 */
public class OrdemDeCorrecaoFormAcao {

    private Long id;
    private Long parametroId;

    @NotBlank @NotNull
    private String responsavel;

    @NotBlank @NotNull
    private String observacao;
    
    private Long analiseId;
    
    private Long acaoId;
    
    //Acao
    @NotBlank @NotNull
    private String acao;
    
    @NotBlank @NotNull
    private String prazo;
    

   
    

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

    public Long getAcaoId() {
		return acaoId;
	}

	public void setAcaoId(Long acaoId) {
		this.acaoId = acaoId;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public String getPrazo() {
		return prazo;
	}

	public void setPrazo(String prazo) {
		this.prazo = prazo;
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

    

	public void updateAcao(AcaoRepository acaoRepository) {
		acaoRepository.save(acaoRepository.findById(acaoId).map(oldAcao -> {
			oldAcao.setAcao(this.acao);
			try {
				oldAcao.setPrazo(DateUtils.getDateTimeLocal(this.prazo));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return oldAcao;
		}).get());
		
	}
    

    public Acao generateAcao(OrdemDeCorrecao ocp) throws ParseException {
        Acao acao = new Acao();
        acao.setAcao(this.acao);
        acao.setData(new Date());
        acao.setNotified(false);
        acao.setPrazo(DateUtils.getDateTimeFromString(this.prazo));
        acao.setOrdemDeCorrecao(ocp);
        acao.setStatus(false);
        return acao;
    }
  
    
    
    
    
}
