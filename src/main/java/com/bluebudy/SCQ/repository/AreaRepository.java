package com.bluebudy.SCQ.repository;

import java.util.List;

import com.bluebudy.SCQ.domain.Area;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area,Long> {

    public List<Area> findByGroupArea(Integer groupArea);
    
}
