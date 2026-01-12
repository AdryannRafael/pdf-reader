package com.adinho.pdf_reader.pdf_reader.core.kernel.errors;

import java.util.Arrays;

public enum NatureErrors {
    API_REQUEST("100"),
    DOMAIN("200"),
    NEGOTIATE("300"),
    INTERNAL("500"),
    DATABASE("600");
    private String value;

    NatureErrors(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }


    public static NatureErrors with(String code) {
        return Arrays.stream(values())
                .filter(value -> value.value().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Code não existe: " + code));
    }
}
