package com.bluebudy.SCQ.forms;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.bluebudy.SCQ.constantes.TiposDeAcao;
import com.bluebudy.SCQ.constantes.Unidade;
import com.bluebudy.SCQ.domain.Adicao;
import com.bluebudy.SCQ.domain.Analise;
import com.bluebudy.SCQ.domain.MateriaPrima;
import com.bluebudy.SCQ.domain.OrdemDeCorrecao;
import com.bluebudy.SCQ.domain.Parametro;
import com.bluebudy.SCQ.repository.ParametroRepository;
import com.bluebudy.SCQ.utils.DateUtils;

public class AnaliseFormComOcpAdicao {

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

    // Adicao
    private String[] mpQtds;

    // Acao

    public AnaliseFormComOcpAdicao() {
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

    public String[] getMpQtds() {
        return mpQtds;
    }

    public void setMpQtds(String[] mpQtds) {
        this.mpQtds = mpQtds;
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

    public List<Adicao> generateAdicao(Long ocpId) {

        List<String> mpQtdList = Arrays.asList(this.mpQtds);
        List<Adicao> newAdicoes = new ArrayList<Adicao>();

        mpQtdList.stream().forEach(pairMpQtd -> {
            String[] token = pairMpQtd.split(":");
            OrdemDeCorrecao ocp = new OrdemDeCorrecao();
            ocp.setId(ocpId);
            MateriaPrima mp = new MateriaPrima();
            mp.setId(Long.valueOf(token[0]));
            newAdicoes
                    .add(new Adicao(null, Double.valueOf(token[1]), mp, ocp, Unidade.findEnumByValue(token[2]), false));
        });

        return newAdicoes;
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
