package com.adinho.pdf_reader.pdf_reader.core.gerador_documentos.state_generator;

import com.adinho.pdf_reader.pdf_reader.core.gerador_documentos.GenerateDocumentProcess;
import com.adinho.pdf_reader.pdf_reader.core.gerador_documentos.StatusEnum;
import com.adinho.pdf_reader.pdf_reader.core.kernel.exceptions.DomainException;

import static com.adinho.pdf_reader.pdf_reader.core.kernel.errors.specificity.DomainSpecificitySystemErrors.INVALID_CHANGE_STATE;
import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.Error;

public final class SendStorage extends State {
    public SendStorage() {
        super(StatusEnum.SEND_STORAGE);
    }

    @Override
    public State falhou(GenerateDocumentProcess processo, Error error, String cause) {
        return new Failed(error, cause);
    }

    @Override
    public State generate(GenerateDocumentProcess processo) {
        throw new DomainException("Já está enviando para o armazenamento", INVALID_CHANGE_STATE);
    }

    @Override
    public State sendStorage(GenerateDocumentProcess processo) {
        throw new DomainException("Já está enviando para o armazenamento", INVALID_CHANGE_STATE);
    }

    @Override
    public State completo(GenerateDocumentProcess processo) {
        if (processo.getPath() != null && !processo.getPath().isEmpty()) {
            return new Success();
        }
        throw new DomainException("Para o processo de gerar documento seja completo, precisa gerar o documento", INVALID_CHANGE_STATE);
    }
}
