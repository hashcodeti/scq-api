package com.bluebudy.SCQ.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.bluebudy.SCQ.constantes.Unidade;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class MateriaPrima implements Comparable<MateriaPrima>{

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    private String nome;
    private String fornecedor;
    private Double fatorTitulometrico;
    private Double preco;
    @Enumerated(EnumType.STRING)
    private Unidade unidade;
    
    @OneToMany(mappedBy = "materiaPrima",  cascade = CascadeType.REMOVE)
    private List<Adicao> adicao;

    public MateriaPrima() {

    }

    public MateriaPrima(Long id) {
        this.id = id;
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

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Double getFatorTitulometrico() {
        return fatorTitulometrico;
    }

    public void setFatorTitulometrico(Double fatorTitulometrico) {
        this.fatorTitulometrico = fatorTitulometrico;
    }

    public List<Adicao> getAdicao() {
        return adicao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public void setAdicao(List<Adicao> adicao) {
        this.adicao = adicao;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    @Override
    public int compareTo(MateriaPrima o) {
        if(o.nome.equals(this.nome)){
            return 1;
        } else {
            return -1;
        }
    }
    
    
    
    

}
