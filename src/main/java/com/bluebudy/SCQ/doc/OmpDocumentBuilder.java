/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.doc;

import com.bluebudy.SCQ.domain.OrdemDeManutencao;
import com.bluebudy.SCQ.domain.Processo;
import com.bluebudy.SCQ.domain.TarefasDeManutencao;
import com.bluebudy.SCQ.domain.Troca;
import com.bluebudy.SCQ.forms.OmpForm;
import com.bluebudy.SCQ.repository.ProcessoRepository;
import com.bluebudy.SCQ.repository.TarefasDeManutencaoRepository;
import com.bluebudy.SCQ.repository.TrocaRepository;
import com.bluebudy.SCQ.utils.DateUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Br;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.R;
import org.docx4j.wml.Text;
import org.xml.sax.SAXException;

/**
 *
 * @author Alexandre
 */
public class OmpDocumentBuilder  implements DocumentBuilder {

    private OrdemDeManutencao omp;
    private List<TarefasDeManutencao> tarefas;
    private List<Troca> trocas;
    private ProcessoRepository processoRepo;
    private TrocaRepository trocaRepo;
    private TarefasDeManutencaoRepository tarefassRepo;
    private Processo processo;
    private Long ompId;
    private int counterLine = 1;
    private int counterManutencao = 1;

    public OmpDocumentBuilder(OrdemDeManutencao omp, ProcessoRepository processoRepo, TrocaRepository trocaRepo,
            TarefasDeManutencaoRepository tarefassRepo) {
        this.omp = omp;
        this.processoRepo = processoRepo;
        this.processo = processoRepo.findById(omp.getProcessoId()).get();
        this.trocaRepo = trocaRepo;
        this.tarefassRepo = tarefassRepo;
        this.ompId = ompId;
        loadVaribles();

    }

    public void loadVaribles() {
        tarefas = new LinkedList<>();
        trocas = new LinkedList<>();

       

        for (String trocasTokens : omp.getTrocasId().split(",")) {
        	String trocaId = trocasTokens.split(":")[0];
            trocas.add(trocaRepo.findById(Long.valueOf(trocaId)).get());
        }
        
        for (String tarefasTokens : omp.getTarefasManutencaoId().split(",")) {
        	String tarefaId = tarefasTokens.split(":")[0];
            tarefas.add(tarefassRepo.findById(Long.valueOf(tarefaId)).get());
        }
        
       
    }

   public String buildDocument() throws SAXException, IOException, ParserConfigurationException, TransformerException, Exception {
       String fileName = null;
       try {
            int trocasSize = trocas.size();
            int tarefasSize = tarefas.size();
            
    
            
            WordprocessingMLPackage wordPack = WordprocessingMLPackage.load(new File("modelo_omp.docx"));
            MainDocumentPart document = wordPack.getMainDocumentPart();
            List<Object> objects = document.getJAXBNodesViaXPath("//w:t", true);

            for (Object object : objects) {
                int indexTroca = counterLine-1;
                int indexTarefa = counterManutencao-1;
                Text text = (Text) object;
                if (text.getValue().contains("«nomeProcesso»")) {
                    text.setValue(processo.getNome());
                }
                if (text.getValue().contains("«emitidoEm»")) {
                    Calendar calendar = Calendar.getInstance();
                    text.setValue(DateUtils.formatToDate(calendar.getTime()));
                }
                if (text.getValue().contains("«emitidoPor»")) {
                    text.setValue(omp.getEmitidoPor());
                }
                if (text.getValue().contains("«programadoPara»")) {
                    text.setValue(DateUtils.formatToDate(omp.getProgramadoPara()));
                }
                if (text.getValue().contains("«ompId»")) {
                    text.setValue(String.valueOf(String.format("%05d", omp.getId())));
                }
                
                if (text.getValue().equals("«etapa" + String.valueOf(this.counterLine) + "»")) {
                 if(trocasSize>=counterLine){
                     text.setValue(trocas.get(indexTroca).getEtapa().getNome());
                 } else {
                     text.setValue("");
                 }
                }
                if (text.getValue().equals("«Tq" + String.valueOf(this.counterLine) + "»")) {
                   if(trocasSize>=counterLine){
                     text.setValue(String.valueOf(trocas.get(indexTroca).getEtapa().getPosicao()));
                 } else {
                     text.setValue("");
                 }
                   
                }
                if (text.getValue().equals("«produto" + String.valueOf(this.counterLine) + "»")) {
                    if(trocasSize>=counterLine){
                        formatListMontagens(text, indexTroca);
                 } else {
                     text.setValue("");
                 }
                }
                if (text.getValue().equals("«qtd" + String.valueOf(this.counterLine) + "»")) {
                    if(trocasSize>=counterLine){
                        formatQuantidadeProduto(text, indexTroca);
                 } else {
                     text.setValue("");
                 }
                    this.counterLine++;
                }
                
                 
                 if (text.getValue().equals("«manutencao" + String.valueOf(this.counterManutencao) + "»")) {
                       if(tarefasSize>=counterManutencao){
                        text.setValue(tarefas.get(indexTarefa).getNome());
                 } else {
                     text.setValue("");
                 }
                    this.counterManutencao++;
                }

            }
            fileName = processo.getNome()+ " - " + DateUtils.formatToDate(Calendar.getInstance().getTime()) +".docx";
            FileOutputStream file = new FileOutputStream(new File(fileName));
            
            wordPack.save(file);
            file.close();
            
            
            
        } catch (Docx4JException e) {

        }
        
        return fileName;

    }

 

    public void formatListMontagens(Text text,int index) {
        List<String> nomesProdutos = trocas.get(index).getEtapa().getMontagem().stream().map(mc -> mc.getMateriaPrima().getNome()).collect(Collectors.toList());
        ObjectFactory wFactory = new ObjectFactory();
        R row = (R) text.getParent();
        

        for (int i = 0; i < nomesProdutos.size(); i++) {

            if (i == 0) {
                text.setValue(nomesProdutos.get(i));
            } else {
                Br lineBreak = wFactory.createBr();
                row.getContent().add(lineBreak);
                Text newText = wFactory.createText();
                newText.setValue(nomesProdutos.get(i));
                row.getContent().add(newText);
            }

        }

    }
    
    public void formatQuantidadeProduto(Text text,int index) {
         List<String> quantidades = trocas.get(index).getEtapa().getMontagem().stream().map(mc -> String.valueOf(mc.getQuantidade())).collect(Collectors.toList());
        ObjectFactory wFactory = new ObjectFactory();
        R row = (R) text.getParent();
        

        for (int i = 0; i < quantidades.size(); i++) {

            if (i == 0) {
                text.setValue(quantidades.get(i));
            } else {
                Br lineBreak = wFactory.createBr();
                row.getContent().add(lineBreak);
                Text newText = wFactory.createText();
                newText.setValue(quantidades.get(i));
                row.getContent().add(newText);
            }

        }

    }
    
    

}
