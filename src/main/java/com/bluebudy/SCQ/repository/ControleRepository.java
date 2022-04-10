/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.bluebudy.SCQ.domain.Controle;

/**
 *
 * @author Alexandre
 */
public interface ControleRepository extends JpaRepository<Controle, Long> , ControleRepositoryCustom {
    

    public List<Controle> findByNumeroGrupoArea(Integer numeroGrupoArea);
}
