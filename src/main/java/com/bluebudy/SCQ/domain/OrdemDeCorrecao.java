/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.domain;

import com.bluebudy.SCQ.constantes.TiposDeAcao;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Alexandre
 */
@Entity
public class OrdemDeCorrecao implements Comparable<OrdemDeCorrecao>  {


    @Id	  
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment" , strategy = "increment") 
    private Long id;

    
    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "analise_id" )
    private Analise analise;

    private TiposDeAcao tipo;
    
    private String responsavel;
    private boolean statusOCP;
    
    @OneToMany(mappedBy = "ordemDeCorrecao", cascade = CascadeType.REMOVE)
    private List<Adicao> adicoes;
    
    @OneToOne(mappedBy = "ordemDeCorrecao")
    private Acao acao;
    
    private Double oldAnaliseValue;
    private String observacao;
   
    
  


    public OrdemDeCorrecao() {

    }
    
  
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Analise getAnalise() {
        return analise;
    }
    public void setAnalise(Analise analise) {
        this.analise = analise;
    }

    public String getResponsavel() {
        return responsavel;
    }
    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
 
    public boolean getStatusOCP() {
        return statusOCP;
    }
    public void setStatusOCP(boolean statusOCP) {
        this.statusOCP = statusOCP;
    }
    public TiposDeAcao getTipo() {
        return tipo;
    }
    public void setTipo(TiposDeAcao tipo) {
        this.tipo = tipo;
    }
    public String getObservacao() {
        return observacao;
    }
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public List<Adicao> getAdicoes() {
        return adicoes;
    }

    public void setAdicoes(List<Adicao> adicoes) {
        this.adicoes = adicoes;
    }

   
    public Acao getAcao() {
        return acao;
    }

    public void setAcao(Acao acao) {
        this.acao = acao;
    }

    


    public Double getOldAnaliseValue() {
        return oldAnaliseValue;
    }


    public void setOldAnaliseValue(Double oldAnaliseValue) {
        this.oldAnaliseValue = oldAnaliseValue;
    }


    @Override
    public int compareTo(OrdemDeCorrecao o) {
        if(this.analise.getData().before(o.getAnalise().getData())){
            return -1;
        } else if (this.analise.getData().after(o.getAnalise().getData())){
            return 1;
        } else {
            return 0;
        }
     
    }
    

   
    
    






}

