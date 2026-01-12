package com.adinho.pdf_reader.pdf_reader.core.gerador_documentos;

public enum StatusEnum {
    /*
    * Primeiro, gera um documento, ele  nao sabe qual, so sabe que vai gerar um
    * Segundo, com o documento gerado ele manda para o S3 para salvar la
    * Terceiro da sucesso */
    CREATED, GENARATING, SEND_STORAGE, FAIL, SUCCESS
}
