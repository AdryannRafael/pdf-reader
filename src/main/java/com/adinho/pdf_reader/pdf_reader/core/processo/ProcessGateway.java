package com.adinho.pdf_reader.pdf_reader.core.processo;

import com.adinho.pdf_reader.pdf_reader.core.gerador_documentos.GenerateDocumentProcess;

public interface ProcessGateway {
    Process persist(GenerateDocumentProcess process);

}
