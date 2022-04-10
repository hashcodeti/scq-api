package com.bluebudy.SCQ.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.FetchType;

@Entity
public class Etapa {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    private String nome;

    
    @ManyToOne
    @JoinColumn(name = "processoId")
    @JsonIgnore
    private Processo processo;

    @Column
    private Integer posicao;

    @OneToMany(mappedBy = "etapa", cascade = CascadeType.REMOVE)
    private List<Parametro> parametros;

    @OneToMany(mappedBy = "etapa", cascade = CascadeType.REMOVE)
    private List<Troca> trocas;


    private Double volume;

    @OneToMany(mappedBy = "etapa" ,fetch=FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<MontagemComposition> montagem;

    public Etapa() {

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

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public List<Parametro> getParametros() {
        return parametros;
    }

    public void setParametros(List<Parametro> parametros) {
        this.parametros = parametros;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

   
    
    public List<Troca> getTrocas() {
        return trocas;
    }

    public void setTrocas(List<Troca> trocas) {
        this.trocas = trocas;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public List<MontagemComposition> getMontagem() {
        return montagem;
    }

    public void setMontagem(List<MontagemComposition> montagem) {
        this.montagem = montagem;
    }
    
    



    
    

}
