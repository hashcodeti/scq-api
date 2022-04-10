package com.bluebudy.SCQ.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bluebudy.SCQ.domain.Analise;
import com.bluebudy.SCQ.domain.Calculadora;
import com.bluebudy.SCQ.domain.Controle;
import com.bluebudy.SCQ.domain.Parametro;
import com.bluebudy.SCQ.repository.AnaliseRepository;
import com.bluebudy.SCQ.repository.ControleRepository;
import com.bluebudy.SCQ.repository.EtapaRepository;
import com.bluebudy.SCQ.repository.NotificacaoRepository;
import com.bluebudy.SCQ.repository.ParametroRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DependenceAnaliseGenerator {
	
	@Autowired
	ParametroRepository parametroRepository;
	@Autowired
	EtapaRepository etapaRepository;
	@Autowired
	AnaliseRepository analiseRepository;
	
	@Autowired
	NotificacaoRepository notificationRepository;
	
	@Autowired
	ControleRepository controlRepo;

	Analise analise;

	Calendar dataFromRefAnalise;

	
	@Autowired
	private NotificationService notificationService;
	
	
	
	
	public DependenceAnaliseGenerator() {
		
	}
	
	public void setAnalise(Analise analise) {
		this.analise = analise;
		dataFromRefAnalise = Calendar.getInstance();
		dataFromRefAnalise.setTime(analise.getData());
	}



	public void resolveParametrosThatNeedAnalise() throws ParseException {

		//Pega a referencia da etapa do parametro que esta sendo analisado
	    long etapaId = etapaRepository.findEtapaByParametrosId(analise.getParametro().getId()).getId();

		//Pega todos os parametros da etapa
        List<Parametro> parametros =  parametroRepository.findParametrosByEtapaId(etapaId); 

		//Itera sobre os parametros da Etapa
        for(Parametro parametro : parametros) {
			
			//Se as variaveis do parametro nao contem V e o parametro da analise atual esta contido na formula passa para verificar a frequencia
        	if(verifyIfAcutalParameterContainsInRelationFormula(parametro)) {
				 if(verifyIfCanGenerateBasedOnFrequency(parametro)){
					generateAnalise(parametro);
					
					Controle controle = parametro.getControle();
					if((controle.getDataPlanejada() == null)){
						Calendar caleRealizada = Calendar.getInstance();
						caleRealizada.setTime(new Date());
						caleRealizada.add(Calendar.MINUTE,controle.getPeriodo().intValue());
						controle.setDataPlanejada(caleRealizada.getTime());
					}
					
					if((controle.getDataRealizada() == null)){
						controle.setDataPlanejada(new Date());
					}
					controle.updateNextAnalise();
					controle.setNotified(false);
					controlRepo.saveAndFlush(controle);
					notificationService.resolveNotifications("Analise", parametroRepository, controle.getId() , notificationRepository);
				 }
        		

        	}
        }
	}
	
	

	private boolean verifyIfCanGenerateBasedOnFrequency(Parametro parametro) {
		Integer numberOfAnalises = analiseRepository.getNumberOfAnalises(parametro.getId());
		if((numberOfAnalises == 0) && checkIfHaveAllDependentParameters(parametro)){
			return true;
		}else if (notificationService.checkIfParameterIsLate(parametro)){
			return true;
		} else {
			return false;
		}
	}

	

	private boolean checkIfHaveAllDependentParameters(Parametro parametro) {
		Calculadora calculadora = new Calculadora();
		calculadora.setFormula(parametro.getFormula());
		String variaveis = calculadora.getVars();
		String tokens[] = variaveis.split(",");
		List<Integer> numbersOfAnalises = new ArrayList<>();
			for (int i = 0; i < tokens.length; i++) {
				Long parametroId = Long.valueOf(StringUtils.removeStart(tokens[i], "da"));
				Integer numberOfAnalise = analiseRepository.getNumberOfAnalises(parametroId);
				if(numberOfAnalise > 0) {
					numbersOfAnalises.add(numberOfAnalise);
				}
				
				
			}
			if(tokens.length == numbersOfAnalises.size()) {
				return true;
			} else {
				return false;
			}
	}

	public boolean verifyIfAcutalParameterContainsInRelationFormula(Parametro actualParametro) {
		Calculadora calculadora = new Calculadora();
		//Pegar o parametro atual de cada iteraçao e seta a formula para recuperar as variaveis
		calculadora.setFormula(actualParametro.getFormula());
		//Pega as variaveis da formula atraves do servico de calculadora
		String variaveis = calculadora.getVars();

		if(!variaveis.contains("V") && variaveis.contains(String.valueOf(analise.getParametro().getId()))){
			
			return true;
		}
		
		return false;

	}

	
	


	public void generateAnalise(Parametro parametro) {
		Analise analise = new Analise();
        analise.setAnalista(this.analise.getAnalista());
        analise.setResultado(calculateReuslt(parametro.getFormula()));
        analise.setParametro(parametro);
        analise.setNeedCorrecao(false);
        analiseRepository.save(analise);
      	}
	
	
	 public Double calculateReuslt(String formula) {
		Calculadora calculadora = new Calculadora();
		calculadora.setRepository(analiseRepository);
		calculadora.setFormula(formula);
		calculadora.setValor(0.0);
		calculadora.setDataTokens();
		calculadora.setDataValues();
		calculadora.calcular();
	    return calculadora.getResultado();
	  }

}
