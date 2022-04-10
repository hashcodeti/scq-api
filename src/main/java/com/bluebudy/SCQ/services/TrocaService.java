package com.bluebudy.SCQ.services;

import java.util.List;

import com.bluebudy.SCQ.dtos.TrocaDTO;
import com.bluebudy.SCQ.forms.TrocaForm;

import org.springframework.stereotype.Service;

@Service
public interface TrocaService {
    
    public TrocaDTO newTroca(TrocaForm trocaForm);
    public List<TrocaDTO> findAll();
    public List<TrocaDTO> findByProcessoId(Long parametroId);
    public TrocaDTO findByEtapaId(Long etapaId);
    public TrocaDTO updateTroca(TrocaForm trocaForm,Long trocaId);
    public void deleteTroca(Long trocaId);



}
