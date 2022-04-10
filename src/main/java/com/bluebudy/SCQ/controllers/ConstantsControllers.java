package com.bluebudy.SCQ.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.bluebudy.SCQ.constantes.Unidade;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConstantsControllers {


    @GetMapping("/unidades")
    List<String> getUnidades(){
        return List.of(Unidade.values()).stream().map(unidade -> unidade.getUnidade()).collect(Collectors.toList());
    }
    
}
