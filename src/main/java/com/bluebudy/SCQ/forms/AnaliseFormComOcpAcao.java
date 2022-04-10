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
import com.bluebudy.SCQ.domain.Parametro;
import com.bluebudy.SCQ.repository.ParametroRepository;
import com.bluebudy.SCQ.utils.DateUtils;

/**
 *
 * @author Alexandre
 */
public class AnaliseFormComOcpAcao {
    // Analise

    private Long id;
    @NotNull
    @NotBlank
    private String analista;
    @NotNull
    private Double resultado;
    @NotNull
    private String status;
    @NotNull
    private Long parametroId;

    private Long ocpId;

    private String data;

    private String observacaoAnalise;

    // Ordem de correçao
    @NotNull
    @NotBlank
    private String responsavel;

    private String observacao;
    private boolean statusAnalise;

    // Acao
    @NotBlank
    @NotNull
    private String acao;

    @NotBlank
    @NotNull
    private String prazo;

    public AnaliseFormComOcpAcao() {

    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnalista() {
        return analista;
    }

    public void setAnalista(String analista) {
        this.analista = analista;
    }

    public Double getResultado() {
        return resultado;
    }

    public void setResultado(Double resultado) {
        this.resultado = resultado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public boolean isStatusAnalise() {
        return statusAnalise;
    }

    public void setStatusAnalise(boolean statusAnalise) {
        this.statusAnalise = statusAnalise;
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

    public String getObservacaoAnalise() {
        return observacaoAnalise;
    }

    public void setObservacaoAnalise(String observacaoAnalise) {
        this.observacaoAnalise = observacaoAnalise;
    }

    public Analise generateAnalise() {
        Parametro parametro = new Parametro();
        parametro.setId(this.parametroId);
        Analise analise = new Analise();
        analise.setId(this.getId());
        analise.setAnalista(this.analista);
        analise.setResultado(this.resultado);
        analise.setParametro(parametro);
        analise.setObservcao(this.observacaoAnalise);
        if (status == "deft") {
            analise.setNeedCorrecao(false);
        } else {
            analise.setNeedCorrecao(true);
        }

        if (this.data != null) {
            try {
                analise.setData(DateUtils.getDateTimeLocal(data));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return analise;
    }

    public OrdemDeCorrecao generateOrdemDeCorrecao(Long analiseId) {
        Analise analise = new Analise();
        analise.setId(analiseId);
        OrdemDeCorrecao ordem = new OrdemDeCorrecao();
        ordem.setId(this.id);
        ordem.setResponsavel(this.responsavel);
        ordem.setObservacao(this.observacao);
        ordem.setStatusOCP(false);
        ordem.setOldAnaliseValue(this.resultado);
        if (this.statusAnalise) {
            ordem.setTipo(TiposDeAcao.CORRETIVA);
        } else {
            ordem.setTipo(TiposDeAcao.PREVENTIVA);
        }
        ordem.setAnalise(analise);
        return ordem;
    }

    public Acao generateAcao() {

        Acao acao = new Acao();

        acao.setAcao(this.acao);
        try {
            acao.setPrazo(DateUtils.getDateTimeLocal(this.prazo));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (this.data != null) {
            try {
                acao.setData(DateUtils.getDateTimeLocal(this.data));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            acao.setData(new Date());
        }
        acao.setNotified(false);
        acao.setStatus(false);

        return acao;
    }

    public Parametro findParametro(ParametroRepository repository) {
        return repository.findById(this.parametroId).get();
    }

    public Date getData() {
        Date date = new Date();
        return date;
    }

    public Long getOcpId() {
        return ocpId;
    }

    public void setOcpId(Long ocpId) {
        this.ocpId = ocpId;
    }

}
