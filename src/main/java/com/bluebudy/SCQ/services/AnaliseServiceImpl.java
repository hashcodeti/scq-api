package com.bluebudy.SCQ.services;

import com.bluebudy.SCQ.domain.Analise;
import com.bluebudy.SCQ.domain.Controle;
import com.bluebudy.SCQ.repository.AnaliseRepository;
import com.bluebudy.SCQ.repository.ControleRepository;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnaliseServiceImpl implements AnaliseService {

    @Autowired
    AnaliseRepository repository;

    @Autowired
    ControleRepository controleRepository;


    //TODO quando deletar analise, atualizar a frequencia atual do controle do parametro
    public void deleteAnaliseById(Long analiseId) {
        Analise analise = repository.findById(analiseId).get();
        Controle controle = analise.getParametro().getControle();
        repository.deleteById(analiseId);
        Long parametroId = analise.getParametro().getId();
        Analise lastAnalise = repository.findLastAnaliseByParamId(parametroId);
        controle.setDataRealizada(lastAnalise.getData());
        controle.setDataPlanejada(DateUtils.addMinutes(controle.getDataRealizada(), controle.getPeriodo().intValue()));
        controleRepository.save(controle);


    }
    
}
