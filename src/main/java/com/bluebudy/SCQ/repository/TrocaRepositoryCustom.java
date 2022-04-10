/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.repository;

import com.bluebudy.SCQ.domain.Troca;
import java.util.List;

/**
 *
 * @author Alexandre
 */
public interface TrocaRepositoryCustom {
    
    public List<Troca> findTrocaAtrasada();
    public List<Troca> findTrocaByProcesso(Long paramId);
    public Troca findTrocaByEtapa(Long etapaId);
}
