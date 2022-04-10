package com.bluebudy.SCQ.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

public class Revisao {

    @Id	  
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name="increment" , strategy = "increment") 
	private Long id;
	
    private String itemAlterado;

    private String dePara;

    private String motivo;

    
}
