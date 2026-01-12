package com.adinho.pdf_reader.pdf_reader.core.processo;

import com.adinho.pdf_reader.pdf_reader.core.gerador_documentos.GenerateDocumentProcess;

import java.util.function.Function;

public class ProcessManager {
    private final ProcessGateway gateway;
    private Process process;

    public ProcessManager(ProcessGateway gateway, Process process) {
        this.gateway = gateway;
        this.process = process;
    }

    public <T extends Process> ProcessManager executeStep(Function<T, T> action) {
        Process apply = action.apply((T) process);
        setProcess(apply);
        if (apply instanceof GenerateDocumentProcess) {
            gateway.persist((GenerateDocumentProcess) apply);
        }

        return this;
    }


    private void setProcess(Process process) {
        this.process = process;
    }

    public Process getProcess() {
        return process;
    }
}