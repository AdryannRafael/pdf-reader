package com.adinho.pdf_reader.pdf_reader.core.gerador_documentos.state_generator;

import com.adinho.pdf_reader.pdf_reader.core.gerador_documentos.GenerateDocumentProcess;
import com.adinho.pdf_reader.pdf_reader.core.gerador_documentos.StatusEnum;
import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.Error;

public abstract class State {
    private StatusEnum statusEnum;

    protected State(StatusEnum statusEnum) {
        this.statusEnum = statusEnum;
    }


    public abstract State generate(GenerateDocumentProcess processo);
    public abstract State sendStorage(GenerateDocumentProcess processo);
    public abstract State completo(GenerateDocumentProcess processo);

    public abstract State falhou(GenerateDocumentProcess processo, Error error, String cause);

    public StatusEnum getStatusEnum() {
        return statusEnum;
    }
}
