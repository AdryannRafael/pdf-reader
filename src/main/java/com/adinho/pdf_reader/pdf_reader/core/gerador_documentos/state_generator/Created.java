package com.adinho.pdf_reader.pdf_reader.core.gerador_documentos.state_generator;

import com.adinho.pdf_reader.pdf_reader.core.gerador_documentos.GenerateDocumentProcess;
import com.adinho.pdf_reader.pdf_reader.core.gerador_documentos.StatusEnum;
import com.adinho.pdf_reader.pdf_reader.core.kernel.exceptions.DomainException;
import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.Error;

public final class Created extends State{
    public Created() {
        super(StatusEnum.CREATED);
    }

    @Override
    public State generate(GenerateDocumentProcess processo) {
        return new Generating();
    }

    @Override
    public State sendStorage(GenerateDocumentProcess processo) {
        throw new DomainException("Necessario gerar o documento antes de enviar para o Storage", null);
    }

    @Override
    public State completo(GenerateDocumentProcess processo) {
        throw new DomainException("Necessario gerar o documento antes de completar o processo", null);
    }

    @Override
    public State falhou(GenerateDocumentProcess processo, Error error, String cause) {
        return new Failed(error, cause);
    }
}
