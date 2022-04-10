package com.bluebudy.SCQ.domain;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;



@Entity
public class Processo {
	
	
	
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Id	  
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name="increment" , strategy = "increment") 
	private Long id;
	private String nome;
	
	@OneToMany(mappedBy = "processo" , cascade = CascadeType.REMOVE)
    private List<Etapa> etapas;
        
    @OneToMany(mappedBy = "processo" ,cascade = CascadeType.REMOVE)
    private List<TarefasDeManutencao> tarefas;
	
	public Processo() {
	
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Etapa> getEtapas() {
		return etapas;
	}
	public void setEtapas(List<Etapa> etapas) {
		this.etapas = etapas;
	}

    public List<TarefasDeManutencao> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<TarefasDeManutencao> tarefas) {
        this.tarefas = tarefas;
    }
	
        
	
	
	
	
	

}
