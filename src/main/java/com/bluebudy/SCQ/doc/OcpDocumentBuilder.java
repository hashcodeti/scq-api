/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.doc;

import com.bluebudy.SCQ.domain.Adicao;
import com.bluebudy.SCQ.domain.MateriaPrima;
import com.bluebudy.SCQ.domain.OrdemDeCorrecao;
import com.bluebudy.SCQ.repository.MateriaPrimaRepository;
import com.bluebudy.SCQ.repository.OrdemDeCorrecaoRepository;
import com.bluebudy.SCQ.utils.DateUtils;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Text;
import org.xml.sax.SAXException;

/**
 *
 * @author Alexandre
 */
public class OcpDocumentBuilder implements DocumentBuilder {

    private List<Adicao> adicoes;
    private OrdemDeCorrecaoRepository ocpRepo;
    private MateriaPrimaRepository mpRepo;
    private String ocpId;
    private String processo;
    private String etapa;
    private String tanque;
    private String responsavel;
    private int counterLine = 1;

    private String observacoes;

    public OcpDocumentBuilder(List<Adicao> adicoes, OrdemDeCorrecaoRepository ocpRepo, MateriaPrimaRepository mpRepo) {
        this.adicoes = adicoes;
        this.ocpRepo = ocpRepo;
        this.mpRepo = mpRepo;
        loadVaribles();
    }

    public void loadVaribles() {
     this.ocpId = String.valueOf(adicoes.get(0).getOrdemDeCorrecao().getId());
     OrdemDeCorrecao ocp = ocpRepo.findById(Long.valueOf(ocpId)).get();
     this.processo = ocp.getAnalise().getParametro().getEtapa().getProcesso().getNome();
     this.etapa = ocp.getAnalise().getParametro().getEtapa().getNome();
     this.tanque = String.valueOf(ocp.getAnalise().getParametro().getEtapa().getPosicao());
     this.observacoes = ocp.getObservacao();
     this.responsavel = ocp.getResponsavel();

    }

    public String buildDocument() throws SAXException, IOException, ParserConfigurationException, TransformerException, Exception {

        StringBuilder builder = new StringBuilder();

        try {
            int adicoesSize = adicoes.size();
          
        

            WordprocessingMLPackage wordPack = WordprocessingMLPackage.load(new File("modelo_ocp.docx"));
            MainDocumentPart document = wordPack.getMainDocumentPart();
            List<Object> objects = document.getJAXBNodesViaXPath("//w:t", true);

            for (Object object : objects) {
            int indexAdicoes = counterLine - 1;

                Text text = (Text) object;
                if (text.getValue().contains("«ocpId»")) {

                    text.setValue(String.format("%05d", adicoes.get(0).getOrdemDeCorrecao().getId()));
                }
                if (text.getValue().contains("«processo»")) {
                    builder.append(processo);
                    text.setValue(processo);
                }
                if (text.getValue().contains("«etapa»")) {
                    builder.append("-" + etapa);
                    text.setValue(etapa);
                }
                if (text.getValue().contains("«tanque»")) {
                    text.setValue(String.valueOf(tanque));
                }
                if (text.getValue().contains("«data»")) {
                    String value = DateUtils.formatToDate(Calendar.getInstance().getTime());
                    builder.append("-" + value);
                    text.setValue(value);
                }
               
                if (text.getValue().contains("«produtoAcao"+this.counterLine+"»")) {
                   
                   if(adicoesSize>=counterLine){
                     text.setValue(mpRepo.findById(adicoes.get(indexAdicoes).getMateriaPrima().getId()).get().getNome());
                 } else {
                     text.setValue("");
                 }
                }
                if (text.getValue().contains("«quantidade"+this.counterLine+"»")) {
                    if(adicoesSize>=counterLine){
                     text.setValue(String.valueOf(adicoes.get(indexAdicoes).getQuantidade())+" "+ mpRepo.findById(adicoes.get(indexAdicoes).getMateriaPrima().getId()).get().getUnidade().getUnidade());
                 } else {
                     text.setValue("");
                 }
                    counterLine++;
                }
                
                if (text.getValue().contains("«observacoes»")) {
                               
                     text.setValue(observacoes);
               
                }
                if (text.getValue().contains("«responsavel»")) {
                               
                     text.setValue(responsavel);
               
                }
             

            }
            FileOutputStream file = new FileOutputStream(new File(builder.toString() + ".docx"));
            wordPack.save(file);
            file.close();

        } catch (Docx4JException e) {

        }

        return builder.toString() + ".docx";

    }

}
