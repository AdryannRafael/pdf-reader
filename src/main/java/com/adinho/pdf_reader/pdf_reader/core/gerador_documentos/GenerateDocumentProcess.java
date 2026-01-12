package com.adinho.pdf_reader.pdf_reader.core.gerador_documentos;

import com.adinho.pdf_reader.pdf_reader.core.gerador_documentos.state_generator.Created;
import com.adinho.pdf_reader.pdf_reader.core.gerador_documentos.state_generator.State;
import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.Error;
import com.adinho.pdf_reader.pdf_reader.core.processo.Process;
import com.adinho.pdf_reader.pdf_reader.core.kernel.ddd.Identify;

import java.io.OutputStream;
import java.time.Instant;

public class GenerateDocumentProcess extends Process<GeradorDocumentosID> {
    private State state;
    private State stateFailed;

    private OutputStream file;
    private String path;

    private GenerateDocumentProcess(GeradorDocumentosID id,State state) {
        super(id);
        this.state = state;
    }

    public GenerateDocumentProcess(GeradorDocumentosID id, State state,
                                   boolean finished, Instant startedAt, Instant finishedAt, Instant failedAt,
                                   Instant createdAt, Instant updatedAt, Instant deletedAt) {
        super(id, finished, startedAt, finishedAt, failedAt, createdAt, updatedAt, deletedAt);
        this.state = state;
    }

    public static GenerateDocumentProcess create(){
        GeradorDocumentosID id = Identify.criar(GeradorDocumentosID.class);
        GenerateDocumentProcess process = new GenerateDocumentProcess(id, new Created());
        process.criado();
        return process;
    }

    /*===================States modifies=============*/
    public GenerateDocumentProcess generate(){
        changeState(this.state.generate(this));
        start();
        return this;
    }

    public GenerateDocumentProcess sendStorage(OutputStream file){
        this.setFile(file);
        changeState(this.state.sendStorage(this));
        return this;
    }

    public GenerateDocumentProcess success(String path) {
        this.setPath(path);
        changeState(this.state.completo(this));
        this.finish();
        return this;
    }

    @Override
    public void failed(Error error, String cause) {
        this.stateFailed = this.state;
        changeState(this.state.falhou(this, error, cause));
        fail();
    }


    @Override
    public void retry() {
        System.out.println("Reprocessando...");
    }

    /*===================Getters=============*/
    public State getState() {
        return state;
    }

    public String getPath() {
        return path;
    }

    public OutputStream getFile() {
        return file;
    }

    public State getStateFailed() {
        return stateFailed;
    }


    /*===================Setters=============*/
    private void setPath(String path) {
        if(path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Caminho não pode ser nulo ou vazio");
        }
        this.path = path;
    }
    private void setFile(OutputStream file) {
        if(file == null) {
            throw new IllegalArgumentException("Arquivo não pode ser nulo");
        }
        this.file = file;
    }
    private void changeState(final State estado) {
        if (this.state == null) {
            throw new IllegalArgumentException("Estado invalido");
        }
        this.state = estado;
        this.editado();
    }

}
