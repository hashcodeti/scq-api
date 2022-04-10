package com.bluebudy.SCQ.dtos;

import java.util.List;

public class AdicaoChartDTO {

    public String processoNome;
    public Double totalGastosOcp;
    public Double totalGastosOmp;
    public List<AdicaoChartDtoDetails> adicaoDetails;
   
    public String getProcessoNome() {
        return processoNome;
    }
    public void setProcessoNome(String processoNome) {
        this.processoNome = processoNome;
    }
    public Double getTotalGastosOcp() {
        return totalGastosOcp;
    }
    public void setTotalGastosOcp(Double totalGastosOcp) {
        this.totalGastosOcp = totalGastosOcp;
    }
    public Double getTotalGastosOmp() {
        return totalGastosOmp;
    }
    public void setTotalGastosOmp(Double totalGastosOmp) {
        this.totalGastosOmp = totalGastosOmp;
    }
   
    
    public List<AdicaoChartDtoDetails> getAdicaoDetails() {
        return adicaoDetails;
    }
    public void setAdicaoDetails(List<AdicaoChartDtoDetails> adicaoDetails) {
        this.adicaoDetails = adicaoDetails;
    }
  

    
    
}
