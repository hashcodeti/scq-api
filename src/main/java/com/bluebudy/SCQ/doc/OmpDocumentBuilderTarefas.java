/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.doc;

import com.bluebudy.SCQ.domain.OrdemDeManutencao;
import com.bluebudy.SCQ.domain.Processo;
import com.bluebudy.SCQ.domain.TarefasDeManutencao;
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
public class OmpDocumentBuilderTarefas  implements DocumentBuilder {


    private List<TarefasDeManutencao> tarefas;
    private ProcessoRepository processoRepo;
    private TarefasDeManutencaoRepository tarefassRepo;
    private Processo processo;
   
    private int counterManutencao = 1;
    private OrdemDeManutencao omp;

    public OmpDocumentBuilderTarefas(OrdemDeManutencao omp, ProcessoRepository processoRepo, TrocaRepository trocaRepo,
            TarefasDeManutencaoRepository tarefassRepo) {
        this.omp = omp;
        this.processoRepo = processoRepo;
        this.processo = processoRepo.findById(omp.getProcessoId()).get();
      
        this.tarefassRepo = tarefassRepo;
        loadVaribles();

    }

    public void loadVaribles() {
        tarefas = new LinkedList<>();
       
        for (String tarefasTokens : omp.getTarefasManutencaoId().split(";")) {
        	String tarefaId = tarefasTokens.split(":")[0];
            tarefas.add(tarefassRepo.findById(Long.valueOf(tarefaId)).get());
        }
       
    }

   public String buildDocument() throws SAXException, IOException, ParserConfigurationException, TransformerException, Exception {
       String fileName = null;
       try {

            int tarefasSize = tarefas.size();
            
    
            
            WordprocessingMLPackage wordPack = WordprocessingMLPackage.load(new File("modelo_omp_tarefas.docx"));
            MainDocumentPart document = wordPack.getMainDocumentPart();
            List<Object> objects = document.getJAXBNodesViaXPath("//w:t", true);

            for (Object object : objects) {
               
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
                
           
                 
                 if (text.getValue().equals("«manutencao" + String.valueOf(this.counterManutencao) + "»")) {
                       if(tarefasSize>=counterManutencao){
                        text.setValue(tarefas.get(indexTarefa).getNome());
                 } else {
                     text.setValue("");
                 }
                    this.counterManutencao++;
                }

            }
            
            fileName = processo.getNome()+ " - " + "tarefas - " + DateUtils.formatToDate(Calendar.getInstance().getTime()) +".docx";
            FileOutputStream file = new FileOutputStream(new File(fileName));
            
            wordPack.save(file);
            file.close();
            
            
            
        } catch (Docx4JException e) {

        }
        
        return fileName;

    }

 

      
    

}
