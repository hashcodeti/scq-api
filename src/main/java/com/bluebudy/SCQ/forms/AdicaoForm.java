/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.forms;


import com.bluebudy.SCQ.constantes.Unidade;
import com.bluebudy.SCQ.domain.Adicao;
import com.bluebudy.SCQ.domain.MateriaPrima;
import com.bluebudy.SCQ.domain.OrdemDeCorrecao;
import com.bluebudy.SCQ.repository.MateriaPrimaRepository;
import com.bluebudy.SCQ.repository.OrdemDeCorrecaoRepository;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Alexandre
 */
public class AdicaoForm {
    @NotNull
    private Long id;
    @NotNull
    private Double quantidade;
    @NotNull
    private Long ordemId;
    @NotNull
    private Long materiaPrimaId;
    
    @NotNull
    private String unidade;
   

    public AdicaoForm() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Long getOrdemId() {
        return ordemId;
    }

    public void setOrdemId(Long ordemId) {
        this.ordemId = ordemId;
    }

    public Long getMateriaPrimaId() {
        return materiaPrimaId;
    }

    public void setMateriaPrimaId(Long materiaPrimaId) {
        this.materiaPrimaId = materiaPrimaId;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    
  

    public Adicao generateAdicao() {
        Adicao adicao = new Adicao();
        MateriaPrima mp = new MateriaPrima();
        mp.setId(this.materiaPrimaId);
        adicao.setMateriaPrima(mp);
        OrdemDeCorrecao oc = new OrdemDeCorrecao();
        oc.setId(this.ordemId);
        adicao.setUnidade(Unidade.findEnumByValue(unidade));
        adicao.setOrdemDeCorrecao(oc);
        adicao.setStatus(false);
        adicao.setQuantidade(this.quantidade);
 
        return adicao;
    }

    public MateriaPrima findMateriaPrima(MateriaPrimaRepository repository) {
        return repository.findById(this.materiaPrimaId).get();
    }

    public OrdemDeCorrecao findOrdemDeCorrecao(OrdemDeCorrecaoRepository repository) {
        return repository.findById(this.ordemId).get();
    }

}
