package com.bluebudy.SCQ.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.bluebudy.SCQ.constantes.Unidade;
import com.bluebudy.SCQ.customannotation.ControleHabilitado;
import com.bluebudy.SCQ.customannotation.EscalaControle;
import com.bluebudy.SCQ.customannotation.FrequenciaControle;
import com.bluebudy.SCQ.domain.Etapa;
import com.bluebudy.SCQ.domain.Parametro;
import com.bluebudy.SCQ.repository.EtapaRepository;

public class ParametroForm {


	@NotNull @NotBlank
	private String nome;
	@NotNull
	private Double pMax;
	@NotNull
	private Double pMin;
	@NotNull
	private Double pMaxT;
	@NotNull
	private Double pMinT;
	@NotNull @NotBlank
	private String formula;
	@NotNull
	private Long etapaId;
	@NotNull @NotBlank
	private String unidade;
	@NotNull @NotBlank @EscalaControle
	private String escala;
	@NotNull @FrequenciaControle
	private Integer frequencia;

    @ControleHabilitado
    private Boolean isHabilitado;

    private Boolean showChart;


    public ParametroForm() {
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public Long getEtapaId() {
        return etapaId;
    }

    public void setEtapaId(Long etapaId) {
        this.etapaId = etapaId;
    }
    
    

    public String getEscala() {
		return escala;
	}


	public void setEscala(String escala) {
		this.escala = escala;
	}


	public Integer getFrequencia() {
		return frequencia;
	}



	public void setFrequencia(Integer frequencia) {
		this.frequencia = frequencia;
	}

	public Parametro generateParametro() {
        Etapa etapa = new Etapa();
        etapa.setId(this.etapaId);
        Parametro parametro = new Parametro();
        parametro.setUnidade(Unidade.findEnumByValue(this.unidade));
        parametro.setNome(this.nome);
        parametro.setpMax(this.pMax);
        parametro.setpMin(this.pMin);
        parametro.setFormula(this.formula);
        parametro.setpMaxT(this.pMaxT);
        parametro.setpMinT(this.pMinT);
        parametro.setEtapa(etapa);
        parametro.setShowChart(this.showChart);
        return parametro;
    }

    public Etapa findEtapa(EtapaRepository repository) {
        return repository.findById(this.etapaId).get();
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public Double getpMaxT() {
        return pMaxT;
    }

    public void setpMaxT(Double pMaxT) {
        this.pMaxT = pMaxT;
    }

    public Double getpMinT() {
        return pMinT;
    }

    public void setpMinT(Double pMinT) {
        this.pMinT = pMinT;
    }


    public boolean isShowChart() {
        return showChart;
    }


    public void setShowChart(boolean showChart) {
        this.showChart = showChart;
    }


    public Boolean getIsHabilitado() {
        return isHabilitado;
    }


    public void setIsHabilitado(Boolean isHabilitado) {
        this.isHabilitado = isHabilitado;
    }


    public Boolean getShowChart() {
        return showChart;
    }


    public void setShowChart(Boolean showChart) {
        this.showChart = showChart;
    }


   

    
    
    

}
