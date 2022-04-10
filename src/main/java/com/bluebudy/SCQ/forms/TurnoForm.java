package com.bluebudy.SCQ.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.bluebudy.SCQ.domain.Turno;

public class TurnoForm {

    @NotNull
    @NotBlank
    private String nome;

    @NotNull
    @NotBlank
    private String inicio;

    @NotNull
    @NotBlank
    private String fim;

    @NotNull
    private Boolean isPrimeiroTurno;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public Turno generateTurno() {
        Turno turno = new Turno();
        turno.setInicio(inicio);
        turno.setFim(fim);
        turno.setNome(nome);
        turno.setIsPrimeiroTurno(isPrimeiroTurno);
        return turno;
    }

    public Boolean getIsPrimeiroTurno() {
        return isPrimeiroTurno;
    }

    public void setIsPrimeiroTurno(Boolean isPrimeiroTurno) {
        this.isPrimeiroTurno = isPrimeiroTurno;
    }

    

}
