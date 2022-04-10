package com.bluebudy.SCQ.dtos;

import com.bluebudy.SCQ.domain.MateriaPrima;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;


public class MateriaPrimaDTO implements Serializable {

    private Long id;
    private String nome;
    private String fornecedor;
    private Double fatorTitulometrico;
    private String unidade;
    private Double preco;
    
    public MateriaPrimaDTO(MateriaPrima materiaPrima) {
        this.id = materiaPrima.getId();
        this.nome = materiaPrima.getNome();
        this.fornecedor = materiaPrima.getFornecedor();
        this.fatorTitulometrico = materiaPrima.getFatorTitulometrico();
        this.unidade = materiaPrima.getUnidade().getUnidade();
        this.preco = materiaPrima.getPreco();
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

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
    
    
    
    
    
    public static List<MateriaPrimaDTO> materiasPrimaToMateriasPrimaDTO(List<MateriaPrima> materiasPrimas){
        return materiasPrimas.stream().map(MateriaPrimaDTO::new).collect(Collectors.toList());
    }

}
