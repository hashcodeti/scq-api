/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.notifications;

import java.util.Date;

import com.bluebudy.SCQ.domain.Parametro;
import com.bluebudy.SCQ.repository.ControleRepository;
import com.bluebudy.SCQ.repository.NotificacaoRepository;
import com.bluebudy.SCQ.utils.DateUtils;

/**
 *
 * @author Alexandre
 */

public class NotificacaoAnalise {

  private String nomeParametro;
  private String nomeEtapa;
  private String nomeProcesso;
  private String posicaoTanque;
  private String dataPlanejada;
  private String dataRealizada;

  public NotificacaoAnalise() {

  }

  public NotificacaoAnalise(Parametro parametro, NotificacaoRepository repository, ControleRepository freqRepo) {
    this.nomeEtapa = parametro.getEtapa().getNome();
    this.nomeProcesso = parametro.getEtapa().getProcesso().getNome();
    this.nomeParametro = parametro.getNome();
    this.posicaoTanque = parametro.getEtapa().getPosicao().toString();
    this.dataPlanejada = DateUtils.formatToDateTime(parametro.getControle().getDataPlanejada() == null ? new Date():parametro.getControle().getDataPlanejada());
    this.dataRealizada = DateUtils.formatToDateTime(parametro.getControle().getDataRealizada() == null ? new Date():parametro.getControle().getDataRealizada() );
  }

  public String getNomeParametro() {
    return nomeParametro;
  }

  public void setNomeParametro(String nomeParametro) {
    this.nomeParametro = nomeParametro;
  }

  public String getNomeEtapa() {
    return nomeEtapa;
  }

  public void setNomeEtapa(String nomeEtapa) {
    this.nomeEtapa = nomeEtapa;
  }

  public String getNomeProcesso() {
    return nomeProcesso;
  }

  public void setNomeProcesso(String nomeProcesso) {
    this.nomeProcesso = nomeProcesso;
  }

  public String getPosicaoTanque() {
    return posicaoTanque;
  }

  public void setPosicaoTanque(String posicaoTanque) {
    this.posicaoTanque = posicaoTanque;
  }

  public String getDataPlanejada() {
    return dataPlanejada;
  }

  public void setDataPlanejada(String dataPlanejada) {
    this.dataPlanejada = dataPlanejada;
  }

  public String getDataRealizada() {
    return dataRealizada;
  }

  public void setDataRealizada(String dataRealizada) {
    this.dataRealizada = dataRealizada;
  }

  


}
