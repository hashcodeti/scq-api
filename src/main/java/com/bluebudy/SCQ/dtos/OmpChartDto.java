package com.bluebudy.SCQ.dtos;

public class OmpChartDto {
    
    private String processName;
    private Integer numberOfTrocasExecuted = 0;
    private Integer numberOfTrocasShouldBeExecuted = 0;
    private Integer numberOfTarefasExecuted = 0;
    private Integer numberOfTarefasShouldBeExecuted = 0;
    private Integer processId;
    

    public OmpChartDto() {
    }
   
    public String getProcessName() {
        return processName;
    }
    public void setProcessName(String processName) {
        this.processName = processName;
    }
    public Integer getNumberOfTrocasExecuted() {
        return numberOfTrocasExecuted;
    }
    public void setNumberOfTrocasExecuted(Integer numberOfChangesExecuted) {
        this.numberOfTrocasExecuted = numberOfChangesExecuted;
    }
    public Integer getNumberOfTrocasShouldBeExecuted() {
        return numberOfTrocasShouldBeExecuted;
    }
    public void setNumberOfTrocasShouldBeExecuted(Integer numberOfChangesShouldBeExcuted) {
        this.numberOfTrocasShouldBeExecuted = numberOfChangesShouldBeExcuted;
    }
    public Integer getNumberOfTarefasExecuted() {
        return numberOfTarefasExecuted;
    }
    public void setNumberOfTarefasExecuted(Integer numberOfTarefasExecuted) {
        this.numberOfTarefasExecuted = numberOfTarefasExecuted;
    }
    public Integer getNumberOfTarefasShouldBeExecuted() {
        return numberOfTarefasShouldBeExecuted;
    }
    public void setNumberOfTarefasShouldBeExecuted(Integer numberOfTarefasShouldBeExecuted) {
        this.numberOfTarefasShouldBeExecuted = numberOfTarefasShouldBeExecuted;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    


    

    





}
