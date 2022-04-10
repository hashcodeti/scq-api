/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.forms;

import com.bluebudy.SCQ.domain.Etapa;
import com.bluebudy.SCQ.domain.MateriaPrima;
import com.bluebudy.SCQ.domain.MontagemComposition;

/**
 *
 * @author Alexandre
 */
public class MontagemCompositionForm {
    
    private double quantidade;
    private Long mpId;
    private Long etapaId;
    private Long id;

    public MontagemCompositionForm() {
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public Long getMpId() {
        return mpId;
    }

    public void setMpId(Long mpId) {
        this.mpId = mpId;
    }

    public Long getEtapaId() {
        return etapaId;
    }

    public void setEtapaId(Long etapaId) {
        this.etapaId = etapaId;
    } 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    public MontagemComposition generateMontagemCompose(){
        Etapa etapa = new Etapa();
        etapa.setId(etapaId);
        MateriaPrima mp = new MateriaPrima();
        mp.setId(mpId);
        MontagemComposition mCompose = new MontagemComposition();
        mCompose.setId(id);
        mCompose.setQuantidade(quantidade);
        mCompose.setMateriaPrima(mp);
        mCompose.setEtapa(etapa);
        return mCompose;
    }
}
