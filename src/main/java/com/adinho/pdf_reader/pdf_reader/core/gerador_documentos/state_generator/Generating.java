package com.adinho.pdf_reader.pdf_reader.core.gerador_documentos.state_generator;

import com.adinho.pdf_reader.pdf_reader.core.gerador_documentos.GenerateDocumentProcess;
import com.adinho.pdf_reader.pdf_reader.core.gerador_documentos.StatusEnum;
import com.adinho.pdf_reader.pdf_reader.core.kernel.exceptions.DomainException;

import static com.adinho.pdf_reader.pdf_reader.core.kernel.errors.specificity.DomainSpecificitySystemErrors.INVALID_CHANGE_STATE;
import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.Error;

public final class Generating extends State {
    public Generating() {
        super(StatusEnum.GENARATING);
    }

    @Override
    public State falhou(GenerateDocumentProcess processo, Error error, String cause) {
        return new Failed(error, cause);
    }

    @Override
    public State generate(GenerateDocumentProcess processo) {
        throw new DomainException("Processo já está gerando o documento", INVALID_CHANGE_STATE);
    }

    @Override
    public State sendStorage(GenerateDocumentProcess processo) {
        if(processo.getFile() != null){
            return new SendStorage();
        }
        throw new DomainException("Para enviar para o Storage é necessario primeiro passar o arquivo", INVALID_CHANGE_STATE);
    }

    @Override
    public State completo(GenerateDocumentProcess processo) {
        throw new DomainException("Processo ainda precisa passar pelo estado de enviar para o Storage", INVALID_CHANGE_STATE);
    }
}
