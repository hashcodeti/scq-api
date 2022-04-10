package com.bluebudy.SCQ.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.bluebudy.SCQ.constantes.Unidade;
import com.bluebudy.SCQ.domain.MateriaPrima;

public class MateriaPrimaForm {


    @NotNull @NotBlank
    private String fornecedor;
    @NotNull @NotBlank
    private String nome;
    
    @NotNull
    private Double fatorTitulometrico;
    
    @NotNull
    private Double preco;
    
    @NotNull @NotBlank
    private String unidade;


    public MateriaPrimaForm() {

    }


    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getFatorTitulometrico() {
        return fatorTitulometrico;
    }

    public void setFatorTitulometrico(Double fatorTitulometrico) {
        this.fatorTitulometrico = fatorTitulometrico;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }
    
     public MateriaPrima generateMateriaPrima() {
         MateriaPrima mp = new MateriaPrima();
       
        mp.setNome(this.getNome());
        mp.setFornecedor(this.getFornecedor());
        mp.setFatorTitulometrico(this.getFatorTitulometrico());
        mp.setPreco(this.getPreco());
        mp.setUnidade(Unidade.findEnumByValue(this.unidade));
        return mp;

    }
    
    
    
    

}
