package com.bluebudy.SCQ.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;

import com.bluebudy.SCQ.domain.Area;
import com.bluebudy.SCQ.domain.Controle;
import com.bluebudy.SCQ.dtos.FormErrorDto;
import com.bluebudy.SCQ.repository.AreaRepository;
import com.bluebudy.SCQ.repository.ControleRepository;
import com.bluebudy.SCQ.services.AreaService;
import com.bluebudy.SCQ.services.FileService;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AreaController {

    @Autowired
    private FileService fileSearch;

    @Autowired
    private AreaRepository repository;

    @Autowired
    private ControleRepository cRepository;

    @Autowired
    private AreaService service;

    @RequestMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE } ,method =  RequestMethod.POST, path = "/uploadArea/{dInicial}/{dFinal}")
    public ResponseEntity<?> updadteProjectWithFile(@PathVariable String dInicial,@PathVariable String dFinal, @RequestPart("file") MultipartFile excelFile) throws Docx4JException, JAXBException, IOException, ParseException{ 
        //Monta lista de areas atraves da planilha
        Date dataInicial =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dInicial);
        Date dataFinal =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dFinal);
        List<Area> areas = fileSearch.spreadSheetFileToAreaList(excelFile, dataInicial, dataFinal);
        List<Area> areasGrouped =  service.groupAreas(areas);
        List<Area> wrongRangeAreas = new ArrayList<>();

        areasGrouped.forEach(area -> {
            List<Area> allAreasByGroup = repository.findByGroupArea(area.getGroupArea());
            List<Controle> controlesByGroup = cRepository.findByNumeroGrupoArea(area.getGroupArea());
            if(allAreasByGroup.isEmpty()) {
                repository.save(area);
                updateControleArea(controlesByGroup,area.getArea());
            } else {
                //Verifica se todas as areas estão fora do range de todas as cadastradas do mesmo grupo
                boolean outSideRange =  true;
                for (Area areaByGroup : allAreasByGroup) {
                    outSideRange = service.isOutSideDataRange(area, areaByGroup);
                }
                //Se todas as areas estão fora do range de data em relaçao a todas as areas do mesmo grupo
                if(outSideRange){
                    //Salva area
                    repository.save(area);
                    updateControleArea(controlesByGroup,area.getArea());
                } else {
                    //Adicionar na lista de areas com ranges errados
                    wrongRangeAreas.add(area);
                }
                
            }
            
        });


        

        if(wrongRangeAreas.isEmpty()){
            return ResponseEntity.ok().body(("Areas salvas com sucesso"));
        } else {
            List<FormErrorDto> errors =  wrongRangeAreas.stream().map(wrongArea -> {
                FormErrorDto error = new FormErrorDto("Grupo " +  wrongArea.getGroupArea().toString(),"Ja cadastrada nesse periodo");
                return error;
            }).collect(Collectors.toList());
            
            return ResponseEntity.badRequest().body(errors);
        }
     
        
    }


    public void updateControleArea(List<Controle> controles,Double area){
        controles.stream().forEach(controle -> {
            cRepository.save(cRepository.findById(controle.getId()).map(c ->{
                boolean hasArea = c.getAreaRealizada() == null ? false : true;
                double newTotalArea = hasArea ?  c.getAreaRealizada() + area : area;
                c.setAreaRealizada(newTotalArea);
                return c;
            }).get());
        });
    }





    
}
