package com.adinho.pdf_reader.pdf_reader.core.gerador_documentos.state_generator;

import com.adinho.pdf_reader.pdf_reader.core.gerador_documentos.GenerateDocumentProcess;
import com.adinho.pdf_reader.pdf_reader.core.gerador_documentos.StatusEnum;
import com.adinho.pdf_reader.pdf_reader.core.kernel.exceptions.DomainException;

import static com.adinho.pdf_reader.pdf_reader.core.kernel.errors.specificity.DomainSpecificitySystemErrors.INVALID_CHANGE_STATE;
import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.Error;

public final class Failed extends State {

    private final Error error;
    private final String cause;

    public Failed(Error error, String cause) {
        super(StatusEnum.FAIL);
        this.error = error;
        this.cause = cause;
    }

    @Override
    public State falhou(GenerateDocumentProcess processo, Error error, String cause) {
        throw new DomainException("Processo já está no estado de falha", INVALID_CHANGE_STATE);

    }

    @Override
    public State generate(GenerateDocumentProcess processo) {
        throw new DomainException("Processo já está no estado de falha", INVALID_CHANGE_STATE);

    }

    @Override
    public State sendStorage(GenerateDocumentProcess processo) {
        throw new DomainException("Processo já está no estado de falha", INVALID_CHANGE_STATE);

    }

    @Override
    public State completo(GenerateDocumentProcess processo) {
        throw new DomainException("Processo já está no estado de falha", INVALID_CHANGE_STATE);
    }

    public Error getError() {
        return error;
    }

    public String getCause() {
        return cause;
    }
}
