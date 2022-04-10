/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.dtos;

import com.bluebudy.SCQ.domain.MontagemComposition;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Alexandre
 */
public class MontagemCompositionDTO {
    
    private double quantidade;
    private String mpNome;
    private Long mpId;
    private String unidade;
    private Long id;

    public MontagemCompositionDTO(MontagemComposition mc) {
       this.quantidade = mc.getQuantidade();
       this.mpNome = mc.getMateriaPrima().getNome();
       this.id = mc.getId();
       this.mpId = mc.getMateriaPrima().getId();
       this.unidade = mc.getMateriaPrima().getUnidade().getUnidade();
              
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public String getMpNome() {
        return mpNome;
    }

    public void setMpNome(String mpNome) {
        this.mpNome = mpNome;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMpId() {
        return mpId;
    }

    public void setMpId(Long mpId) {
        this.mpId = mpId;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    
  
    public static List<MontagemCompositionDTO> mcToMcDTO(List<MontagemComposition> mcs){
        return mcs.stream().map(MontagemCompositionDTO::new).collect(Collectors.toList());
    }
   
    
}
