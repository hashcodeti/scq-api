package com.bluebudy.SCQ.controllers;

import com.bluebudy.SCQ.domain.Calculadora;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController

public class Calculator {
	
	@Autowired
	private Calculadora calculadora;
	
		
	 @GetMapping("/calculadora")
	 public Double newEtapa(@RequestParam(name = "formula") String formula, @RequestParam(name = "viragem") Double viragem) {
		
		calculadora.setFormula(formula);
		calculadora.setValor(viragem);
		calculadora.setDataTokens();
		calculadora.setDataValues();
		calculadora.calcular();
	    return calculadora.getResultado();
	  }
}
