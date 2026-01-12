package com.adinho.pdf_reader.pdf_reader.core.kernel;

public enum Produtos {
    GERADOR_RPV("/rpv");

    private final String path;

    Produtos(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
