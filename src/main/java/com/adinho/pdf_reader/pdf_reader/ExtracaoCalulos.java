package com.adinho.pdf_reader.pdf_reader;

import java.math.BigDecimal;

public record ExtracaoCalulos(BigDecimal principal, BigDecimal juros,BigDecimal selic, BigDecimal total) {

    @Override
    public String toString() {
        return "ExtracaoCalulos{" +
                "principal=" + principal +
                ", juros=" + juros +
                ", selic=" + selic +
                ", total=" + total +
                '}';
    }
}
