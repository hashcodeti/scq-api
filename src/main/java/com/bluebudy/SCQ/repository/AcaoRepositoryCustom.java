/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.repository;

import com.bluebudy.SCQ.domain.Acao;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Alexandre
 */
public interface AcaoRepositoryCustom  {
    
    List<Acao>findFrequenciaToDay(Date date);
    
    
}
