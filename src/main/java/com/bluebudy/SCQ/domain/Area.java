package com.bluebudy.SCQ.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Area {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataInicio;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataFim;
    private Double area;
    private Integer groupArea;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Date getDataInicio() {
        return dataInicio;
    }
    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }
    public Date getDataFim() {
        return dataFim;
    }
    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }
    public Double getArea() {
        return area;
    }
    public void setArea(Double area) {
        this.area = area;
    }
    public Integer getGroupArea() {
        return groupArea;
    }
    public void setGroupArea(Integer groupArea) {
        this.groupArea = groupArea;
    }
    

    
    
}
