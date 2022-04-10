package com.bluebudy.SCQ.forms;

import java.text.ParseException;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.bluebudy.SCQ.domain.Analise;
import com.bluebudy.SCQ.domain.Parametro;
import com.bluebudy.SCQ.repository.ParametroRepository;
import com.bluebudy.SCQ.utils.DateUtils;

public class AnaliseForm {

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

    public AnaliseForm() {
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getObservacaoAnalise() {
        return observacaoAnalise;
    }

    public void setObservacaoAnalise(String observacaoAnalise) {
        this.observacaoAnalise = observacaoAnalise;
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

    public Analise replaceAnalise() {
        Parametro parametro = new Parametro();
        parametro.setId(this.parametroId);
        Analise analise = new Analise();
        analise.setId(this.getId());
        analise.setAnalista(this.analista);
        analise.setResultado(this.resultado);
        analise.setParametro(parametro);
        analise.setObservcao(this.observacaoAnalise);
        if (status == "fofe") {
            analise.setNeedCorrecao(true);
        } else {
            analise.setNeedCorrecao(false);
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
