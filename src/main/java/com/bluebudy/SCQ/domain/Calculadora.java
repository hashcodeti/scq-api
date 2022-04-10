package com.bluebudy.SCQ.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bluebudy.SCQ.repository.AnaliseRepository;

import org.apache.commons.lang3.StringUtils;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class Calculadora {
	
	@Autowired
	private AnaliseRepository repository;
	
	private String formula;
	private Double viragem;
	private Double resultado;
	private String vars;
	private Double[] dataValues;
	private String[] dataTokens;

	
	
	
	public Calculadora() {
		
	}

	public String getFormula() {
		return formula;
	}
	
	public void setFormula(String formula) {
		this.formula = formula;
		int i = setVars().split(",").length;
		dataValues = new Double[i];
		dataTokens = new String[i];
		
	}
	
	
	
	
	public AnaliseRepository getRepository() {
		return repository;
	}

	public void setRepository(AnaliseRepository repository) {
		this.repository = repository;
	}

	public String getVars() {
		return vars;
	}

	public void setVars(String vars) {
		this.vars = vars;
	}

	public Double getValor() {
		return viragem;
	}
	
	public void setValor(Double viragem) {
		this.viragem = viragem;
	}
	
	public Double getResultado() {
		return resultado;
	}
	
	public void setResultado(Double resultado) {
		this.resultado = resultado;
		
	}
	
	
	public Double calcular() {
		Function function = new Function(buildFunction());
		Expression expression = new Expression(buildExpression(),function);
		resultado = expression.calculate();
		return resultado;
	}
	
	public String buildFunction() {
		StringBuilder sb = new StringBuilder();
		sb.append("function(");
		sb.append(vars);
		sb.append(") = ");
		sb.append(cleanFormulaToken());
		return sb.toString();
	}
	
	public String setVars(){
		Pattern p = Pattern.compile("(?<=\\[)([^\\]]+)(?=\\])");
		Matcher m = p.matcher(this.formula);
		StringBuilder sb = new StringBuilder();
		
		while(m.find()) {
		    sb.append(m.group());
		    sb.append(",");
		}	
			return this.vars = sb.deleteCharAt(sb.length()-1).toString();
		
		

	}
	
	public String cleanFormulaToken() {
		String firstRemove = StringUtils.remove(formula, "[");
		String formulaCleaned = StringUtils.remove(firstRemove,"]");
		return formulaCleaned;
	
	}

	public String buildExpression() {
		StringBuilder sb = new StringBuilder();
		sb.append("function(");
		sb.append(setValueForExpression());
		sb.append(")");
		return sb.toString();
		
	}
	
	public String setValueForExpression() {
		
		StringBuilder sb = new StringBuilder();
		String[] tokens = vars.split(",");
		int i = 0;
		for(String var : tokens) {
			if(var.startsWith("mp")) {
				sb.append(dataValues[i]);
				sb.append(",");
				i++;
			} else if(var.startsWith("da")) {
				sb.append(dataValues[i]);
				sb.append(",");
				i++;
			}	else {
				sb.append(viragem);
				sb.append(",");
				i++;
			}
			
		}
		return sb.deleteCharAt(sb.length()-1).toString();
	}
	
	
	public void setDataTokens(){
		int i = 0;
		if(vars.length()==1) {
			dataTokens[i] = vars.trim();
		} else if (vars.length()>1){
			String tokens[] = vars.split(",");
			for(String var : tokens) {	
				dataTokens[i] = var;
				i++;
			}
		}
	}
	
	
	public void setDataValues() {
		int i = 0;
		for(String dT : dataTokens) {
			if(dT==null) {
				
			} else {
				if(dT.startsWith("da")) {
					dataValues[i] = repository.LastAnaliseResult(dT).getResultado();
					i++;
				} else {
					dataValues[i] = viragem;
					i++;
					
				}
			}
		
	}
		
	}
}
