package com.bluebudy.SCQ.domain;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Analise {

    /*
     * @Id
     * 
     * @GeneratedValue(generator = "increment")
     * 
     * @GenericGenerator(name="increment" , strategy = "increment")
     */
    /*
     * @Id
     * 
     * @GeneratedValue(strategy = GenerationType.IDENTITY)
     */
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;
    private String analista;
    private Double resultado;
    private Boolean needCorrecao;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date data = Date.from(Instant.now());

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parametro_id")
    private Parametro parametro;

    @OneToMany(mappedBy = "analise", cascade = CascadeType.REMOVE)
    private List<OrdemDeCorrecao> ordemDeCorrecao;

    private String observcao;

    public Analise() {

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

    public Parametro getParametro() {
        return parametro;
    }

    public void setParametro(Parametro parametro) {
        this.parametro = parametro;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Boolean getNeedCorrecao() {
        return needCorrecao;
    }

    public void setNeedCorrecao(Boolean needCorrecao) {
        this.needCorrecao = needCorrecao;
    }

    public String getObservcao() {
        return observcao;
    }

    public void setObservcao(String observcao) {
        this.observcao = observcao;
    }

    public List<OrdemDeCorrecao> getOrdemDeCorrecao() {
        return ordemDeCorrecao;
    }

    public void setOrdemDeCorrecao(List<OrdemDeCorrecao> ordemDeCorrecao) {
        this.ordemDeCorrecao = ordemDeCorrecao;
    }

}
