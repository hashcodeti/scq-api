/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.dtos;

import com.bluebudy.SCQ.constantes.Unidade;
import com.bluebudy.SCQ.domain.Analise;

/**
 *
 * @author Alexandre
 */
public class ReanaliseDTO {

    private Long id;
    private String analista;
    private Double resultado;
    private Long processoId;
    private String processoNome;
    private Long etapaId;
    private String etapaNome;
    private Long parametroId;
    private String parametroNome;
    private String menuType;
    private Double pMax;
    private Double pMin;
    private String formula;
    private Double pMinT;
    private Double pMaxT;
    private String unidade;
    
    

    public ReanaliseDTO(Analise analise) {
        this.id = analise.getId();
        this.analista = analise.getAnalista();
        this.resultado = analise.getResultado();
        this.processoId = analise.getParametro().getEtapa().getProcesso().getId();
        this.processoNome = analise.getParametro().getEtapa().getProcesso().getNome();
        this.etapaId = analise.getParametro().getEtapa().getId();
        this.etapaNome = analise.getParametro().getEtapa().getNome();
        this.parametroId = analise.getParametro().getId();
        this.parametroNome = analise.getParametro().getNome();
        this.menuType = this.validarUnidade(analise.getParametro().getUnidade());
        this.pMax = analise.getParametro().getpMax();
        this.pMin = analise.getParametro().getpMin();
        this.formula = analise.getParametro().getFormula();
        this.pMinT = analise.getParametro().getpMinT();
        this.pMaxT = analise.getParametro().getpMaxT();
        this.unidade = analise.getParametro().getUnidade().getUnidade();
      

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

    public Long getProcessoId() {
        return processoId;
    }

    public void setProcessoId(Long processoId) {
        this.processoId = processoId;
    }

    public String getProcessoNome() {
        return processoNome;
    }

    public void setProcessoNome(String processoNome) {
        this.processoNome = processoNome;
    }

    public Long getEtapaId() {
        return etapaId;
    }

    public void setEtapaId(Long etapaId) {
        this.etapaId = etapaId;
    }

    public String getEtapaNome() {
        return etapaNome;
    }

    public void setEtapaNome(String etapaNome) {
        this.etapaNome = etapaNome;
    }

    public Long getParametroId() {
        return parametroId;
    }

    public void setParametroId(Long parametroId) {
        this.parametroId = parametroId;
    }

    public String getParametroNome() {
        return parametroNome;
    }

    public void setParametroNome(String parametroNome) {
        this.parametroNome = parametroNome;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public Double getpMax() {
        return pMax;
    }

    public void setpMax(Double pMax) {
        this.pMax = pMax;
    }

    public Double getpMin() {
        return pMin;
    }

    public void setpMin(Double pMin) {
        this.pMin = pMin;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Double getpMinT() {
        return pMinT;
    }

    public Double getpMaxT() {
        return pMaxT;
    }

    public String getUnidade() {
        return unidade;
    }
    
    
    
    
    
    
    
    
    public String validarUnidade(Unidade unidade){
        if((unidade != Unidade.GramaPorLitro) && (unidade != Unidade.Porcentagem) && (unidade != Unidade.Militro) && (unidade != Unidade.ph)){
            return "Acao";
        } else {
            return "Adicao";
        }
    }

    

   public double generateCorrecao(Analise analise) {
        Double resultado = analise.getResultado();
        Double pMax = analise.getParametro().getpMax();
        Double pMin = analise.getParametro().getpMin();
        Double nominal = (pMax + pMin) / 2;
        if (resultado < nominal) {
            return (nominal - resultado) * analise.getParametro().getEtapa().getVolume();
        } else {
            return 0;
        }
    }

   

}
