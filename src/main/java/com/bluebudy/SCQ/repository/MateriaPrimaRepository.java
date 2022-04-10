package com.bluebudy.SCQ.repository;

import com.bluebudy.SCQ.domain.MateriaPrima;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MateriaPrimaRepository extends JpaRepository<MateriaPrima,Long> , MateriaPrimaRepositoryCustom{


    
    
}
