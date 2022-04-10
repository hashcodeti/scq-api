package com.bluebudy.SCQ.domain;

import com.bluebudy.SCQ.constantes.Unidade;
import com.bluebudy.SCQ.forms.ParametroForm;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Parametro {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    private String nome;
    private Double pMax;
    private Double pMin;
    private String formula;
    private Double pMaxT;
    private Double pMinT;
    
    @Enumerated(EnumType.STRING)
    private Unidade unidade;

    private boolean showChart;


    @ManyToOne()
    @JoinColumn(name = "etapa_id")
    private Etapa etapa;

    @OneToMany(mappedBy = "parametro", cascade = CascadeType.REMOVE )
    private List<Analise> analises;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Controle controle;

    

    public Parametro() {

    }

    public Parametro(ParametroForm form) {
       this.unidade = Unidade.findEnumByValue(form.getUnidade());
       
        this.nome = form.getNome();
        this.pMax = form.getpMax();
        this.pMin = form.getpMin();
        this.formula = form.getFormula();
        this.pMaxT = form.getpMaxT();
        this.pMinT = form.getpMinT();
   

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

    public Etapa getEtapa() {
        return etapa;
    }

    public void setEtapa(Etapa etapa) {
        this.etapa = etapa;
    }

    public List<Analise> getAnalises() {
        return analises;
    }

    public void setAnalises(List<Analise> analises) {
        this.analises = analises;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

	

	public Controle getControle() {
		return controle;
	}

	public void setControle(Controle controle) {
		this.controle = controle;
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

    

    
    
    
    
    

}
