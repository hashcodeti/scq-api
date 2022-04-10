package com.bluebudy.SCQ.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.bluebudy.SCQ.constantes.Unidade;
import com.bluebudy.SCQ.domain.Controle;
import com.bluebudy.SCQ.domain.Parametro;

@SuppressWarnings("serial")
public class ParametroDTO implements Serializable {

    private Long id;
    private String nome;
    private Double pMax;
    private Double pMin;
    private Double pMaxT;
    private Double pMinT;
    private String formula;
    private Long etapaId;
    private List<Long> analisesId;
    private String menuType;
    private String unidade;
    private Long processoId;
    private Long frequencia;
    private String escalaFrequencia;
    private Long frequenciaId;
    private boolean analiseHoje;
    private boolean showChart;
    private String etapaNome;
    private boolean cantBeUsed;
    private String turno;
    private Date dataPlanejada;
    private boolean isHabilitado;
    private Integer posicao;

    public ParametroDTO(Parametro parametro) {

        this.id = parametro.getId();
        this.showChart = parametro.isShowChart();
        this.nome = parametro.getNome();
        this.pMax = parametro.getpMax();
        this.pMin = parametro.getpMin();
        this.formula = parametro.getFormula();
        this.etapaId = parametro.getEtapa().getId();
        this.menuType = validarUnidade(parametro.getUnidade());
        this.processoId = parametro.getEtapa().getProcesso().getId();
        this.unidade = parametro.getUnidade().getUnidade();
        this.etapaNome = parametro.getEtapa().getNome();

        this.dataPlanejada = parametro.getControle().getDataPlanejada() == null ? new Date()
                : parametro.getControle().getDataPlanejada();

        buildFreequenciaDatas(parametro);

        this.isHabilitado = parametro.getControle().isHabilitado();
        if (parametro.getControle().getPeriodo().equals(Long.valueOf(0))) {
            this.isHabilitado = true;
        }
        this.analisesId = parametro.getAnalises() == null ? null
                : parametro.getAnalises().stream().map(e -> e.getId()).collect(Collectors.toList());
        this.pMaxT = parametro.getpMaxT();
        this.pMinT = parametro.getpMinT();
        this.posicao = parametro.getEtapa().getPosicao();

    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public String getEtapaNome() {
        return etapaNome;
    }

    public void setEtapaNome(String etapaNome) {
        this.etapaNome = etapaNome;
    }

    public boolean isHabilitado() {
        return isHabilitado;
    }

    public void setHabilitado(boolean isHabilitado) {
        this.isHabilitado = isHabilitado;
    }

    public ParametroDTO(Parametro parametro, String returnType) {

        this.id = parametro.getId();
        this.nome = parametro.getNome();

    }

    public boolean isCantBeUsed() {
        return cantBeUsed;
    }

    public void setCantBeUsed(boolean cantBeUsed) {
        this.cantBeUsed = cantBeUsed;
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

    public Long getEtapaId() {
        return etapaId;
    }

    public void setEtapaId(Long etapaId) {
        this.etapaId = etapaId;
    }

    public List<Long> getAnalisesId() {
        return analisesId;
    }

    public void setAnalisesId(List<Long> analisesId) {
        this.analisesId = analisesId;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public Long getProcessoId() {
        return processoId;
    }

    public void setProcessoId(Long processoId) {
        this.processoId = processoId;
    }

    public Long getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Long frequencia) {
        this.frequencia = frequencia;
    }

    public String getEscalaFrequencia() {
        return escalaFrequencia;
    }

    public void setEscalaFrequencia(String escalaFrequencia) {
        this.escalaFrequencia = escalaFrequencia;
    }

    public Long getFrequenciaId() {
        return frequenciaId;
    }

    public void setFrequenciaId(Long frequenciaId) {
        this.frequenciaId = frequenciaId;
    }

    public boolean isShowChart() {
        return showChart;
    }

    public void setShowChart(boolean showChart) {
        this.showChart = showChart;
    }

    public boolean isAnaliseHoje() {
        return analiseHoje;
    }

    public void setAnaliseHoje(boolean analiseHoje) {
        this.analiseHoje = analiseHoje;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Date getDataPlanejada() {
        return dataPlanejada;
    }

    public String validarUnidade(Unidade unidade) {
        if ((unidade != Unidade.GramaPorLitro) && (unidade != Unidade.Porcentagem) && (unidade != Unidade.Militro)
                && (unidade != Unidade.ph)) {
            return "Acao";
        } else {
            return "Adicao";
        }
    }

    public static List<ParametroDTO> parametrosToParametrosDTO(List<Parametro> parametros) {
        return parametros.stream().map(ParametroDTO::new).collect(Collectors.toList());
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

    public void setDataPlanejada(Date dataPlanejada) {
        this.dataPlanejada = dataPlanejada;
    }

    public void buildFreequenciaDatas(Parametro parametro) {
        boolean assertControl = false;
        Controle frequencia = parametro.getControle();
        Long periodo = frequencia.getPeriodo();
        if (periodo % 60 == 0) {
            assertControl = true;
            escalaFrequencia = "Hora";
            this.frequencia = frequencia.getPeriodo() / 60;
        }
        if (periodo % 1440 == 0) {
            assertControl = true;
            escalaFrequencia = "Dia";
            this.frequencia = frequencia.getPeriodo() / 1440;
        }
        if (periodo % 43200 == 0) {
            assertControl = true;
            escalaFrequencia = "Mes";
            this.frequencia = frequencia.getPeriodo() / 43200;
        }
        if (assertControl == false) {
            escalaFrequencia = "Minuto";
            this.frequencia = frequencia.getPeriodo();
        }

        frequenciaId = frequencia.getId();

    }

}
