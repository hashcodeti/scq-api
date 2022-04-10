package com.bluebudy.SCQ.dtos;

public class AdicaoChartDtoDetails {

    public String nomeMateriaPrima;
    public Double quantidade;
    public String gastoTotal;
    public Long etapaId;
    public String etapaNome;
    public String unidade;
    public String data;
    public Boolean isOcp;


    
    public AdicaoChartDtoDetails(String nomeMateriaPrima, Double quantidade, String gasto, Long etapaId, String etapaNome,
            String unidade, String data, Boolean isOcp) {
        this.nomeMateriaPrima = nomeMateriaPrima;
        this.quantidade = quantidade;
        this.gastoTotal = gasto;
        this.etapaId = etapaId;
        this.etapaNome = etapaNome;
        this.unidade = unidade;
        this.data = data;
        this.isOcp = isOcp;
    }
   
    public String getGastoTotal() {
        return gastoTotal;
    }

    public void setGastoTotal(String gastoTotal) {
        this.gastoTotal = gastoTotal;
    }

    public Boolean getIsOcp() {
        return isOcp;
    }
    public void setIsOcp(Boolean isOcp) {
        this.isOcp = isOcp;
    }
    public String getNomeMateriaPrima() {
        return nomeMateriaPrima;
    }
    public void setNomeMateriaPrima(String nomeMateriaPrima) {
        this.nomeMateriaPrima = nomeMateriaPrima;
    }
    public Double getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
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
    public String getUnidade() {
        return unidade;
    }
    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    
    
    
}
