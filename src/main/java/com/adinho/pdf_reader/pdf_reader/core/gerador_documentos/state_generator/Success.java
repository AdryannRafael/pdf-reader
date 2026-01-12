package com.adinho.pdf_reader.pdf_reader.core.gerador_documentos.state_generator;

import com.adinho.pdf_reader.pdf_reader.core.gerador_documentos.GenerateDocumentProcess;
import com.adinho.pdf_reader.pdf_reader.core.gerador_documentos.StatusEnum;
import com.adinho.pdf_reader.pdf_reader.core.kernel.exceptions.DomainException;

import static com.adinho.pdf_reader.pdf_reader.core.kernel.errors.specificity.DomainSpecificitySystemErrors.INVALID_CHANGE_STATE;
import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.Error;

public final class Success extends State {

    public Success() {
        super(StatusEnum.SUCCESS);
    }

    @Override
    public State falhou(GenerateDocumentProcess processo, Error error, String cause) {
        throw new DomainException("Documento já foi gerado", INVALID_CHANGE_STATE);
    }

    @Override
    public State generate(GenerateDocumentProcess processo) {
        throw new DomainException("Documento já foi gerado", INVALID_CHANGE_STATE);
    }

    @Override
    public State sendStorage(GenerateDocumentProcess processo) {
        throw new DomainException("Documento já foi gerado", INVALID_CHANGE_STATE);
    }

    @Override
    public State completo(GenerateDocumentProcess processo) {
        throw new DomainException("Documento já foi gerado", INVALID_CHANGE_STATE);
    }
}
