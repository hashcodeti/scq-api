package com.bluebudy.SCQ.services;

import java.util.Calendar;
import java.util.Date;

import com.bluebudy.SCQ.repository.TurnoRepository;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParametroService {
    

    @Autowired
    TurnoRepository turnoRepo;

    public  boolean needAnaliseHoje(Date dataPlanejada) {
		Calendar calendarHoje = Calendar.getInstance();
		Date dataInicioTurnoA = DateUtils.setHours(calendarHoje.getTime(), 7);
		Date dataFimTurnoC = DateUtils.setMinutes(DateUtils.setDays(DateUtils.setHours(calendarHoje.getTime(), 7),
				calendarHoje.get(Calendar.DAY_OF_MONTH) + 1), 10);
		if (dataPlanejada.getTime() >= dataInicioTurnoA.getTime()
				&& dataPlanejada.getTime() <= dataFimTurnoC.getTime()) {
			return true;
		} else if (dataPlanejada.getTime() < dataInicioTurnoA.getTime()) {
			return true;
		} else {
			return false;
		}
	}
}
