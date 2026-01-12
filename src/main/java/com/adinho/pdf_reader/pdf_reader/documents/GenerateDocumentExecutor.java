package com.adinho.pdf_reader.pdf_reader.documents;

import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.NatureErrors;
import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.SystemError;
import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.specificity.InternalSpecificitySystemErrors;
import com.adinho.pdf_reader.pdf_reader.core.kernel.exceptions.ErrorException;
import com.adinho.pdf_reader.pdf_reader.core.processo.ProcessManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.adinho.pdf_reader.pdf_reader.core.processo.Process;

import java.util.function.Function;

public class GenerateDocumentExecutor {
    private final Logger log = LoggerFactory.getLogger(GenerateDocumentExecutor.class);
    private final ProcessManager manager;

    public GenerateDocumentExecutor(ProcessManager manager) {
        this.manager = manager;
    }

    public <T extends Process> T execute(Function<ProcessManager, Process> action){
        try{
            /*Aqui ele executa tudo, pelo manager tem o salvamento automatico de cada passo*/
            return (T) action.apply( manager);
        }catch (ErrorException error){
            /*Aqui ele trata exeptions conhecidas pelo ErrorException */
            String erro = "ERRO NA GERAÇÃO DO DOCUMENTO,CODIGO: {}, MENSAGEM: {}, CAUSE {}, codigo".formatted(error.getSystemError().getErrorCode(), error.getMessage(), error.getCause());
            log.error(erro);
            manager.executeStep(p -> {
                p.failed(error.getSystemError(), error.getMessage());
                return p;
            });
            throw error;
        }
        catch (Exception error){
            /*Aqui ele trata exeptions conhecidas pelo ErrorException */
            SystemError genericError = new SystemError(NatureErrors.INTERNAL, InternalSpecificitySystemErrors.OTHER_ERROR);
            String erro = "ERRO NA GERAÇÃO DO DOCUMENTO,CODIGO: {}, MENSAGEM: {}, CAUSE {}, codigo".formatted(genericError.getErrorCode(), error.getMessage(), error.getCause());
            log.error(erro);
            manager.executeStep(p -> {
                p.failed(genericError, error.getMessage());
                return p;
            });
            throw error;
        }

    }

}
