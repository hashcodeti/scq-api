package com.bluebudy.SCQ.dtos;

import java.util.List;

public class FunctionWsModelDTO {

    String type;
    String route;
    List<String> functions;



    

    public FunctionWsModelDTO(String type, List<String> functions, String route) {
        this.type = type;
        this.functions = functions;
        this.route = route;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getRoute() {
        return route;
    }
    public void setRoute(String route) {
        this.route = route;
    }
    public List<String> getFunctions() {
        return functions;
    }
    public void setFunctions(List<String> functions) {
        this.functions = functions;
    }

    
    
    
}
