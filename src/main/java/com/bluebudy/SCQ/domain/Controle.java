package com.bluebudy.SCQ.domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Controle {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	private Long periodo;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dataPlanejada;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dataRealizada;
	private Double areaPlanejada;
	private Double areaRealizada;
	private Integer numeroGrupoArea;
	private boolean notified;
	private boolean isHabilitado = true;

	public Controle() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Long periodo) {
		this.periodo = periodo;
	}

	public Date getDataPlanejada() {
		return dataPlanejada;
	}

	public void setDataPlanejada(Date dataPlanejada) {
		this.dataPlanejada = dataPlanejada;
	}

	public Date getDataRealizada() {
		return dataRealizada;
	}

	public void setDataRealizada(Date dataRealizada) {
		this.dataRealizada = dataRealizada;
	}

	public boolean isNotified() {
		return notified;
	}

	public void setNotified(boolean notified) {
		this.notified = notified;
	}

	

	public boolean isHabilitado() {
		return isHabilitado;
	}

	public void setHabilitado(boolean isHabilitado) {
		this.isHabilitado = isHabilitado;
	}


	public Double getAreaPlanejada() {
        return areaPlanejada;
    }

    public void setAreaPlanejada(Double areaPlanejada) {
        this.areaPlanejada = areaPlanejada;
    }

    public Double getAreaRealizada() {
        return areaRealizada;
    }

    public void setAreaRealizada(Double areaRealizada) {
        this.areaRealizada = areaRealizada;
    }

    public Integer getNumeroGrupoArea() {
        return numeroGrupoArea;
    }

    public void setNumeroGrupoArea(Integer numeroGrupoArea) {
        this.numeroGrupoArea = numeroGrupoArea;
    }

	public void updateNextAnalise() {
		Calendar cal = Calendar.getInstance(); // creates calendar
		cal.setTime(new Date()); // sets calendar time/date
		this.dataRealizada = cal.getTime();
		cal.add(Calendar.MINUTE, this.periodo.intValue()); // adds one hour

		this.dataPlanejada = cal.getTime();

	}

}
