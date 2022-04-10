package com.bluebudy.SCQ.services;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.bluebudy.SCQ.constantes.EscalaTempo;
import com.bluebudy.SCQ.customannotation.AreaPlanejadaControle;
import com.bluebudy.SCQ.customannotation.ControleHabilitado;
import com.bluebudy.SCQ.customannotation.DataPlanejadaControle;
import com.bluebudy.SCQ.customannotation.EscalaControle;
import com.bluebudy.SCQ.customannotation.FrequenciaControle;
import com.bluebudy.SCQ.customannotation.NumeroGrupoAreaControle;
import com.bluebudy.SCQ.domain.Controle;
import com.bluebudy.SCQ.repository.ControleRepository;
import com.bluebudy.SCQ.utils.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ControleService {

	@Autowired
	ControleRepository repository;

	public Controle buildControleService(Object obj)
			throws IllegalArgumentException, IllegalAccessException, ParseException {
		Controle controle = new Controle();
		String escala = null;
		Integer frequencia = 0;
		Date dataPlanejada = null;
		boolean isHabilitado = true;
		Integer numeroGrupoArea = null;
		Double areaPlanejada = 0.0;

		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(EscalaControle.class)) {
				escala = (String) field.get(obj);

			}
			if (field.isAnnotationPresent(NumeroGrupoAreaControle.class)) {
				numeroGrupoArea = (Integer) field.get(obj);

			}
			if (field.isAnnotationPresent(AreaPlanejadaControle.class)) {
				areaPlanejada = (Double) field.get(obj);

			}
			if (field.isAnnotationPresent(FrequenciaControle.class)) {
				frequencia = (Integer) field.get(obj);

			}
			if (field.isAnnotationPresent(DataPlanejadaControle.class)) {
				dataPlanejada = DateUtils.getDateTimeFromString((String) field.get(obj));

			}
			if (field.isAnnotationPresent(ControleHabilitado.class)) {
				isHabilitado = (Boolean) field.get(obj);

			}
		}
		Long periodo = buildPeriodoControle(escala, frequencia);
		if (dataPlanejada == null) {
			dataPlanejada = generateDataPlanejada(periodo, null);
		}

		controle.setDataPlanejada(dataPlanejada);
		controle.setNotified(false);
		controle.setPeriodo(periodo);
		controle.setAreaPlanejada(areaPlanejada);
		controle.setNumeroGrupoArea(numeroGrupoArea);
		controle.setHabilitado(isHabilitado);

		return repository.save(controle);

	}

	public Controle updateControle(Object obj, Controle controle)
			throws IllegalArgumentException, IllegalAccessException, ParseException {

		String escala = null;
		Integer frequencia = 0;
		Integer numeroGrupoArea = null;
		Double areaPlanejada = 0.0;
		Date dataPlanejada = new Date();

		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(EscalaControle.class)) {
				escala = (String) field.get(obj);

			}
			if (field.isAnnotationPresent(NumeroGrupoAreaControle.class)) {
				numeroGrupoArea = (Integer) field.get(obj);

			}
			if (field.isAnnotationPresent(AreaPlanejadaControle.class)) {
				areaPlanejada = (Double) field.get(obj);

			}
			if (field.isAnnotationPresent(FrequenciaControle.class)) {
				frequencia = (Integer) field.get(obj);

			}
			if (field.isAnnotationPresent(DataPlanejadaControle.class)) {
				dataPlanejada = DateUtils.getDateTimeFromString((String) field.get(obj));

			}

		}
		Long periodo = buildPeriodoControle(escala, frequencia);
		controle.setAreaPlanejada(areaPlanejada);
		controle.setNumeroGrupoArea(numeroGrupoArea);
		controle.setDataPlanejada(generateDataPlanejada(periodo, controle.getDataRealizada()));
		controle.setPeriodo(periodo);

		return repository.save(controle);

	}

	public Long buildPeriodoControle(String escala, Integer frequencia) {
		Integer minutos = EscalaTempo.valueOf(escala).getUnidade();
		return Long.valueOf(minutos * frequencia);
	}

	public Date generateDataPlanejada(Long periodo, Date data) {
		Calendar calendar = Calendar.getInstance();
		if (data != null) {
			calendar.setTime(data);
		}
		calendar.add(Calendar.MINUTE, periodo.intValue());
		return calendar.getTime();
	}

	public Integer countsShouldHave(Long period, Date fromDate, Date toDate) {
		Long range = toDate.getTime() - fromDate.getTime();
		range = range / 60000;
		Long shouldHave = range / period;
		return shouldHave.intValue();

	}

	public void updateControleData(Controle controle, Date novaData) {
		repository.save(repository.findById(controle.getId()).map(control -> {
			control.setDataRealizada(novaData);
			control.setDataPlanejada(generateDataPlanejada(controle.getPeriodo(), novaData));
			return control;
		}).get());
	}

	public void updateControleArea(Controle controle, Double novaArea) {
		repository.save(repository.findById(controle.getId()).map(control -> {
			control.setAreaRealizada(novaArea);
			return control;
		}).get());
	}

}
