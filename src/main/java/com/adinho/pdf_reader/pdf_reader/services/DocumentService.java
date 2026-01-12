package com.adinho.pdf_reader.pdf_reader.services;

import com.adinho.pdf_reader.pdf_reader.core.gerador_documentos.GenerateDocumentProcess;
import com.adinho.pdf_reader.pdf_reader.core.kernel.Produtos;
import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.specificity.InternalSpecificitySystemErrors;
import com.adinho.pdf_reader.pdf_reader.core.kernel.exceptions.InternalException;
import com.adinho.pdf_reader.pdf_reader.core.processo.ProcessGateway;
import com.adinho.pdf_reader.pdf_reader.core.processo.ProcessManager;
import com.adinho.pdf_reader.pdf_reader.documents.GenerateDocumentExecutor;
import com.adinho.pdf_reader.pdf_reader.documents.generators.RPVGenerator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class DocumentService {

    private final ProcessGateway processGateway;
    private final StorageService storageService;

    public DocumentService(ProcessGateway processGateway, StorageService storageService) {
        this.processGateway = processGateway;
        this.storageService = storageService;
    }

    public ByteArrayOutputStream calcular(MultipartFile modelo, String numeroProcesso, List<MultipartFile> files) {
        /*Instanciar um processo de gerar documento e o manager de processos
         * Pegar o gerador de documento que no caso é o Gerador de RPV
         * Pegar o service que manda para o S3 */
        GenerateDocumentProcess process = GenerateDocumentProcess.create();
        ProcessManager processManager = new ProcessManager(processGateway, process);

        GenerateDocumentExecutor executor = new GenerateDocumentExecutor(processManager);
        executor.execute(manager -> {
            manager
                    .executeStep((p) -> generateRpv((GenerateDocumentProcess) p, modelo, numeroProcesso, files))
                    .executeStep((p) -> sendToStorage((GenerateDocumentProcess) p, numeroProcesso, Produtos.GERADOR_RPV));
            return manager.getProcess();
        });

        return (ByteArrayOutputStream) process.getFile();
    }


    private GenerateDocumentProcess generateRpv(GenerateDocumentProcess process, MultipartFile modelo, String numeroProcesso, List<MultipartFile> files) {
        process.generate();
        try {
            RPVGenerator generator = new RPVGenerator(modelo.getInputStream(), numeroProcesso, files);
            OutputStream file = generator.gerar();
            process.sendStorage(file);
        } catch (IOException ex) {
            throw new InternalException(ex.getMessage(), InternalSpecificitySystemErrors.IO);
        } catch (Exception ex) {
            throw ex;
        }
        return process;
    }

    private GenerateDocumentProcess sendToStorage(GenerateDocumentProcess process, String numeroProcesso, Produtos produto) {
        ByteArrayOutputStream file = (ByteArrayOutputStream) process.getFile();
        String path = storageService.sendDocx(new ByteArrayInputStream(file.toByteArray()), file.size(), numeroProcesso, produto);
        process.success(path);
        return process;
    }

}
