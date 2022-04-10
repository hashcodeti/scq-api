package com.bluebudy.SCQ.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.bluebudy.SCQ.domain.Adicao;
import com.bluebudy.SCQ.domain.OrdemDeManutencao;
import com.bluebudy.SCQ.domain.Processo;
import com.bluebudy.SCQ.domain.Troca;
import com.bluebudy.SCQ.dtos.AdicaoChartDTO;
import com.bluebudy.SCQ.dtos.AdicaoChartDtoDetails;
import com.bluebudy.SCQ.repository.AdicaoRepository;
import com.bluebudy.SCQ.repository.OmpRepository;
import com.bluebudy.SCQ.repository.TrocaRepository;
import com.bluebudy.SCQ.utils.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdicaoService {

    @Autowired
    private AdicaoRepository adicaoRepository;
    @Autowired
    private TrocaRepository tRepository;

    @Autowired
    private OmpRepository ompRepository;

    public AdicaoChartDTO loadAdicaoChart(Processo processo, String inicialDate, String finalDate) {
        Date fromDate = null;
        Date toDate = null;
        try {
            fromDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(inicialDate);
            toDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(finalDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        AdicaoChartDTO adicaoChartDto = new AdicaoChartDTO();
        adicaoChartDto.setTotalGastosOcp(0.0);
        adicaoChartDto.setTotalGastosOmp(0.0);
        List<AdicaoChartDtoDetails> adicaoChartDtails = new ArrayList<>();
        List<Adicao> adicoesOcp = adicaoRepository
                .findByOrdemDeCorrecaoAnaliseParametroEtapaProcessoIdAndDataBetween(processo.getId(), fromDate, toDate);
        List<OrdemDeManutencao> omps = ompRepository.findByProcessoIdAndRealizadoEmBetween(processo.getId(), fromDate,
                toDate);

        omps.stream().forEach(omp -> {
            if ((omp.getTrocasId() != null) && (!omp.getTrocasId().isEmpty())) {
                List<String> idStatus = Arrays.asList(omp.getTrocasId().split(","));
                idStatus.stream().forEach(idStat -> {
                    String[] trocaStatusToken = idStat.split(":");
                    if (trocaStatusToken[1].equals("OK")) {
                        Optional<Troca> trocaOpt = tRepository.findById(Long.valueOf(trocaStatusToken[0]));
                        trocaOpt.ifPresent(troca -> {
                            troca.getEtapa().getMontagem().forEach(montagem -> {
                                adicaoChartDto.setTotalGastosOmp(adicaoChartDto.getTotalGastosOmp() + getPrecoTotal(
                                        montagem.getQuantidade(), montagem.getMateriaPrima().getPreco()));
                                adicaoChartDtails.add(new AdicaoChartDtoDetails(
                                        montagem.getMateriaPrima().getNome(),
                                        montagem.getQuantidade(),
                                        getPrecoTotal(montagem.getQuantidade(), montagem.getMateriaPrima().getPreco())
                                                .toString(),
                                        troca.getEtapa().getId(),
                                        troca.getEtapa().getNome(),
                                        montagem.getMateriaPrima().getUnidade().getUnidade(),
                                        DateUtils.formatToDateTime(omp.getRealizadoEm()),
                                        false));
                            });

                        });

                    }
                });
            }

        });

        for (Adicao adicao : adicoesOcp) {
            adicaoChartDto.setTotalGastosOcp(adicaoChartDto.getTotalGastosOcp()
                    + getPrecoTotal(adicao.getQuantidade(), adicao.getMateriaPrima().getPreco()));
            adicaoChartDtails.add(new AdicaoChartDtoDetails(
                    adicao.getMateriaPrima().getNome(),
                    adicao.getQuantidade(),
                    getPrecoTotal(adicao.getQuantidade(), adicao.getMateriaPrima().getPreco()).toString(),
                    adicao.getOrdemDeCorrecao().getAnalise().getParametro().getEtapa().getId(),
                    adicao.getOrdemDeCorrecao().getAnalise().getParametro().getEtapa().getNome(),
                    adicao.getMateriaPrima().getUnidade().getUnidade(),
                    DateUtils.formatToDateTime(adicao.getData()),
                    true));
        }

        adicaoChartDto.setAdicaoDetails(adicaoChartDtails);
        adicaoChartDto.setProcessoNome(processo.getNome());

        return adicaoChartDto;
    }

    private Double getPrecoTotal(Double quantidade, Double preco) {
        Double total = quantidade * preco;
        BigDecimal bigTotal = new BigDecimal(total);
        return bigTotal.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

}
