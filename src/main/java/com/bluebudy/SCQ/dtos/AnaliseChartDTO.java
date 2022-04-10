/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.dtos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bluebudy.SCQ.domain.Analise;
import com.bluebudy.SCQ.domain.OrdemDeCorrecao;
import com.bluebudy.SCQ.domain.Parametro;
import com.bluebudy.SCQ.utils.DateUtils;
import com.google.common.collect.Iterables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alexandre
 */
public class AnaliseChartDTO {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnaliseChartDTO.class);

    private List<Long> analisesId = new LinkedList<>();
    private List<String> analistas = new LinkedList<>();
    private List<String> observacoesAnalise = new LinkedList<>();
    private List<String> observacoesOcp = new LinkedList<>();
    private Map<String, Double> resultados = new LinkedHashMap<>();
    private ArrayList<Double> statisticas = new ArrayList<Double>();
    private Map<Long, List<ListOrdemDeCorrecaoDTO>> ocps = new LinkedHashMap<>();
    private Double pMax;
    private Double pMin;
    private Double pMaxT;
    private Double pMinT;
    private Double limiteMax;
    private Double limiteMin;
    private Double yRangeMax;
    private Double yRangeMin;
    private String nomeParam;
    private String nomeEtapa;
    private String nomeProcesso;
    private String unidade;
    private Long processoId;
    private Long etapaId;
    private Long parametroId;
    private String numbersOfYellow;
    private String numbersOfRed;
    private String numbersOfGreen;
    private String numbersOfInsideFrequency;
    private Double totalNumbers;

    public AnaliseChartDTO() {

    }

    public Map<Long, List<ListOrdemDeCorrecaoDTO>> getOcps() {
        return ocps;
    }

    public void setOcps(Map<Long, List<ListOrdemDeCorrecaoDTO>> ocps) {
        this.ocps = ocps;
    }

    public Double getyRangeMax() {
        return yRangeMax;
    }

    public void setyRangeMax(Double yRangeMax) {
        this.yRangeMax = yRangeMax;
    }

    public Double getyRangeMin() {
        return yRangeMin;
    }

    public void setyRangeMin(Double yRangeMin) {
        this.yRangeMin = yRangeMin;
    }

    public Double getLimiteMax() {
        return limiteMax;
    }

    public void setLimiteMax(Double limiteMax) {
        this.limiteMax = limiteMax;
    }

    public Double getLimiteMin() {
        return limiteMin;
    }

    public void setLimiteMin(Double limiteMin) {
        this.limiteMin = limiteMin;
    }

    public List<Long> getAnalisesId() {
        return analisesId;
    }

    public void setAnalisesId(List<Long> analisesId) {
        this.analisesId = analisesId;
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

    public String getNomeEtapa() {
        return nomeEtapa;
    }

    public void setNomeEtapa(String nomeEtapa) {
        this.nomeEtapa = nomeEtapa;
    }

    public String getNomeProcesso() {
        return nomeProcesso;
    }

    public void setNomeProcesso(String nomeProcesso) {
        this.nomeProcesso = nomeProcesso;
    }

    public List<String> getAnalistas() {
        return analistas;
    }

    public void setAnalistas(List<String> analistas) {
        this.analistas = analistas;
    }

    public Long getProcessoId() {
        return processoId;
    }

    public void setProcessoId(Long processoId) {
        this.processoId = processoId;
    }

    public Long getEtapaId() {
        return etapaId;
    }

    public void setEtapaId(Long etapaId) {
        this.etapaId = etapaId;
    }

    public Long getParametroId() {
        return parametroId;
    }

    public void setParametroId(Long parametroId) {
        this.parametroId = parametroId;
    }

    public Map<String, Double> getResultados() {
        return resultados;
    }

    public void setResultados(Map<String, Double> resultados) {
        this.resultados = resultados;
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

    public String getNomeParam() {
        return nomeParam;
    }

    public void setNomeParam(String nomeParam) {
        this.nomeParam = nomeParam;
    }

    public String getNumbersOfGreen() {
        return numbersOfGreen;
    }

    public void setNumbersOfGreen(String numbersOfGreen) {
        this.numbersOfGreen = numbersOfGreen;
    }

    public Double getTotalNumbers() {
        return totalNumbers;
    }

    public void setTotalNumbers(Double totalNumbers) {
        this.totalNumbers = totalNumbers;
    }

    public String getTranformedTrocaDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return sdf.format(date);
    }

    public AnaliseChartDTO analisesToAnalisesChartDTO(List<Analise> analises, String dataInicial, String dataFinal)
            throws ParseException {

        Long totalPeriod = DateUtils.getDateFromString(dataFinal).getTime()
                - DateUtils.getDateFromString(dataInicial).getTime();
        List<Double> maxAndMinLimits = new ArrayList<>();
        Double resultadoTotal = 0.0;
        for (Analise analise : analises) {
            ocps.put(analise.getId(), ListOrdemDeCorrecaoDTO.ordemToOrdemDTO(analise.getOrdemDeCorrecao()));
            resultados.put(getTranformedTrocaDate(analise.getData()), analise.getResultado());
            maxAndMinLimits.add(analise.getResultado());
            resultadoTotal = resultadoTotal + analise.getResultado();
            observacoesAnalise.add(analise.getObservcao());
            if (analise.getOrdemDeCorrecao().size() > 0) {
                observacoesOcp
                        .add(analise.getOrdemDeCorrecao().get(analise.getOrdemDeCorrecao().size() - 1).getObservacao());
            } else {
                observacoesOcp.add("");
            }

            analisesId.add(analise.getId());
            analistas.add(analise.getAnalista());
        }
        this.pMax = analises.get(0).getParametro().getpMax();
        this.pMin = analises.get(0).getParametro().getpMin();
        this.pMinT = analises.get(0).getParametro().getpMinT();
        this.pMaxT = analises.get(0).getParametro().getpMaxT();

        List<Double> valoresOrdenados = resultados.values().stream().sorted().collect(Collectors.toList());
        statisticas.add(BigDecimal.valueOf(valoresOrdenados.get(0)).setScale(2,RoundingMode.HALF_UP).doubleValue());
        statisticas.add(BigDecimal.valueOf(valoresOrdenados.get(valoresOrdenados.size() - 1)).setScale(2,RoundingMode.HALF_UP).doubleValue());
        statisticas.add(BigDecimal.valueOf(resultadoTotal / valoresOrdenados.size()).setScale(2,RoundingMode.HALF_UP).doubleValue());
        statisticas.add(calculaCpk(valoresOrdenados));

        this.unidade = analises.get(0).getParametro().getUnidade().getUnidade();
        this.nomeProcesso = analises.get(0).getParametro().getEtapa().getProcesso().getNome();
        this.nomeEtapa = analises.get(0).getParametro().getEtapa().getNome();
        this.nomeParam = analises.get(0).getParametro().getNome();
        this.processoId = analises.get(0).getParametro().getEtapa().getProcesso().getId();
        this.etapaId = analises.get(0).getParametro().getEtapa().getId();
        this.parametroId = analises.get(0).getParametro().getId();
       
        this.yRangeMax = this.pMax * 1.1;
        this.yRangeMax = Math.floor(this.yRangeMax);
        this.yRangeMin = this.pMin * 0.9;
        this.yRangeMin = Math.floor(this.yRangeMin);
        getRedYellows(analises, this.pMax, this.pMaxT, this.pMinT, this.pMin);
        if (analises.get(0).getParametro().getControle().getPeriodo().equals(Long.valueOf(0))) {
            this.numbersOfInsideFrequency = "100.00";
            this.totalNumbers = 1.0;
        } else {
            this.numbersOfInsideFrequency = getInsideFrequency(analises, totalPeriod);
            this.totalNumbers = getTotalNumbers(analises.get(0).getParametro(), totalPeriod);
        }

        return this;
    }

    private Double calculaCpk(List<Double> valoresOrdenados) {
        Double somatoria = 0.0;
        Double media = statisticas.get(2);
        for (Double valor1 : valoresOrdenados) {
            double valorMenosMedia = valor1 - media;
            somatoria += Math.pow((valorMenosMedia), 2);
        }
        Double desvio = BigDecimal.valueOf(Math.sqrt((somatoria / valoresOrdenados.size()))).setScale(2, RoundingMode.HALF_UP).doubleValue();
        Double cpkInferior = (media - this.pMin ) / (3*desvio);
        if(!cpkInferior.isNaN()){
            cpkInferior = cpkInferior.isInfinite()  ? 0.0 :  BigDecimal.valueOf(cpkInferior).setScale(2, RoundingMode.HALF_UP).doubleValue();
        } else {
            cpkInferior = 0.0;
        }
        Double cpkSuperior = (this.pMax - media ) / (3*desvio);
        if(!cpkSuperior.isNaN()){
            cpkSuperior = cpkSuperior.isInfinite()  ? 0.0 :  BigDecimal.valueOf(cpkSuperior).setScale(2, RoundingMode.HALF_UP).doubleValue();
        } else{
            cpkSuperior = 0.0;
        }
        return cpkInferior.compareTo(cpkSuperior) < 0 ? cpkInferior : cpkSuperior;
    }

    private Double getTotalNumbers(Parametro parametro, Long totalPeriod) {
        Long totalNumbers = totalPeriod / parametro.getControle().getPeriodo();
        return totalNumbers.doubleValue();
    }

    private String getInsideFrequency(List<Analise> analises, Long totalPeriod) {
        Long qtdAnaliseShouldHave = totalPeriod / (analises.get(0).getParametro().getControle().getPeriodo() * 60000);
        Double porcentegeAnaliseThatHave = (Double.valueOf(analises.size()) / qtdAnaliseShouldHave.doubleValue()) * 100;
        String frequencyRate = "0.00";
        try {
            frequencyRate = new BigDecimal(porcentegeAnaliseThatHave).setScale(2, RoundingMode.HALF_UP).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage() + " - " + analises.get(0).getParametro().getNome() + " - "
                    + analises.get(0).getParametro().getEtapa().getNome());
        }
        return frequencyRate;

    }

    private void getRedYellows(List<Analise> analises, Double pMax2, Double pMaxT2, Double pMinT2, Double pMin2) {
        Integer reds = 0;
        Integer greens = 0;
        Iterator<Analise> analiseIterator = analises.iterator();
        while (analiseIterator.hasNext()) {
            Analise analise = analiseIterator.next();
            List<OrdemDeCorrecao> ocps = analise.getOrdemDeCorrecao();
            Integer numbersOfRedInsiedOcpList = 0;
            for (OrdemDeCorrecao ocp : ocps) {
                if (ocp.getOldAnaliseValue() != null) {
                    if ((ocp.getOldAnaliseValue().doubleValue() > pMax2.doubleValue())
                            || (ocp.getOldAnaliseValue().doubleValue() < pMin2.doubleValue())) {
                        numbersOfRedInsiedOcpList++;
                    }
                }
                reds = reds + numbersOfRedInsiedOcpList;

            }
            if ((analise.getResultado().doubleValue() <= pMaxT2.doubleValue())
                    && (analise.getResultado().doubleValue() >= pMinT2.doubleValue())) {
                greens++;
            }
        }

        Double doubleRedValue = (reds.doubleValue() / analises.size()) * 100;
        Double doubleGreenValue = (greens.doubleValue() / analises.size()) * 100;
        Double doubleYellowValue = 100 - doubleGreenValue - doubleRedValue;

        numbersOfRed = new BigDecimal(doubleRedValue).setScale(2, RoundingMode.HALF_UP).toString();
        numbersOfYellow = new BigDecimal(doubleYellowValue).setScale(2, RoundingMode.HALF_UP).toString();
        numbersOfGreen = new BigDecimal(doubleGreenValue).setScale(2, RoundingMode.HALF_UP).toString();
    }

    public String getNumbersOfYellow() {
        return numbersOfYellow;
    }

    public void setNumbersOfYellow(String numbersOfYellow) {
        this.numbersOfYellow = numbersOfYellow;
    }

    public String getNumbersOfRed() {
        return numbersOfRed;
    }

    public void setNumbersOfRed(String numbersOfRed) {
        this.numbersOfRed = numbersOfRed;
    }

    public String getNumbersOfInsideFrequency() {
        return numbersOfInsideFrequency;
    }

    public void setNumbersOfInsideFrequency(String numbersOfInsideFrequency) {
        this.numbersOfInsideFrequency = numbersOfInsideFrequency;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public AnaliseChartDTO emptyAnalisechartDTO(Parametro parametro) {

        this.unidade = parametro.getUnidade().getUnidade();
        this.nomeProcesso = parametro.getEtapa().getProcesso().getNome();
        this.nomeEtapa = parametro.getEtapa().getNome();
        this.nomeParam = parametro.getNome();
        this.processoId = parametro.getEtapa().getProcesso().getId();
        this.etapaId = parametro.getEtapa().getId();
        this.parametroId = parametro.getId();
        this.pMax = parametro.getpMax();
        this.pMin = parametro.getpMin();
        this.pMinT = parametro.getpMinT();
        this.pMaxT = parametro.getpMaxT();
        this.numbersOfYellow = "0";
        this.numbersOfRed = "0";
        this.numbersOfGreen = "100";
        this.numbersOfInsideFrequency = "100";

        return this;
    }

    public List<String> getObservacoesAnalise() {
        return observacoesAnalise;
    }

    public void setObservacoesAnalise(List<String> observacoesAnalise) {
        this.observacoesAnalise = observacoesAnalise;
    }

    public List<String> getObservacoesOcp() {
        return observacoesOcp;
    }

    public void setObservacoesOcp(List<String> observacoesOcp) {
        this.observacoesOcp = observacoesOcp;
    }

    String getLastCorrecaoFromAnalise(Analise analise) {
        List<OrdemDeCorrecao> ordens = analise.getOrdemDeCorrecao();
        Comparator<OrdemDeCorrecao> comparator = Comparator.comparing((e) -> e.getId(), Comparator.reverseOrder());
        ordens.sort(comparator);
        return Iterables.getLast(ordens).getObservacao();
    }

    public ArrayList<Double> getStatisticas() {
        return statisticas;
    }

    public void setStatisticas(ArrayList<Double> statisticas) {
        this.statisticas = statisticas;
    }

}
