package com.bluebudy.SCQ.dtos;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.bluebudy.SCQ.domain.Etapa;
import com.bluebudy.SCQ.domain.MontagemComposition;
import java.util.LinkedList;

public class EtapaDTO implements Serializable {

    private Long id;
    private String nome;
    private Integer posicao;
    private Long processoId;
    private List<Long> parametrosId;
    private Double volume;
    private List<Long> mpIds;
    private List<String> proportionMps;
    private boolean hasNoParam;

    public EtapaDTO(Etapa etapa, String type) {
        this.id = etapa.getId();
        this.nome = etapa.getNome();
        this.posicao = etapa.getPosicao();
        this.processoId = etapa.getProcesso().getId();
        this.volume = etapa.getVolume();
        this.parametrosId = etapa.getParametros() == null ? null : etapa.getParametros().stream().map(e -> e.getId()).collect(Collectors.toList());
        
        
       
    }
    public EtapaDTO(Etapa etapa) {
        this.hasNoParam = etapa.getParametros().isEmpty();
        this.id = etapa.getId();
        this.nome = etapa.getNome();
        this.posicao = etapa.getPosicao();
        this.processoId = etapa.getProcesso().getId();
        this.volume = etapa.getVolume();
        this.parametrosId = etapa.getParametros() == null ? null : etapa.getParametros().stream().map(e -> e.getId()).collect(Collectors.toList());
        this.mpIds = etapa.getMontagem().stream().map(mc -> mc.getMateriaPrima().getId()).collect(Collectors.toList());
       
        double somaTotal = 0.0;
        for (MontagemComposition mc : etapa.getMontagem()) {
            somaTotal = somaTotal + mc.getQuantidade();   
            
         }
        this.proportionMps = new LinkedList<>();
        for (MontagemComposition mc : etapa.getMontagem()) {
            StringBuilder sb = new StringBuilder();
            sb.append(mc.getMateriaPrima().getId());
            sb.append(":");
            sb.append(mc.getQuantidade()/somaTotal);
            this.proportionMps.add(sb.toString());
        }
        
    }
    
    
    

    public boolean isHasNoParam() {
        return hasNoParam;
    }
    public void setHasNoParam(boolean hasNoParam) {
        this.hasNoParam = hasNoParam;
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

    public Long getProcessoId() {
        return processoId;
    }

    public void setProcessoId(Long processoId) {
        this.processoId = processoId;
    }

   

    public List<Long> getParametrosId() {
        return parametrosId;
    }

    public void setParametrosId(List<Long> parametrosId) {
        this.parametrosId = parametrosId;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public List<Long> getMpIds() {
        return mpIds;
    }

    public void setMpIds(List<Long> mpIds) {
        this.mpIds = mpIds;
    }

    public List<String> getProportionMps() {
        return proportionMps;
    }

    public void setProportionMps(List<String> proportionMps) {
        this.proportionMps = proportionMps;
    }

    
    
    
    


    
    
    

    
    

    public static List<EtapaDTO> etapasToEtapasDTO(List<Etapa> etapas) {
        return etapas.stream().map(EtapaDTO::new).collect(Collectors.toList());
    }

}
