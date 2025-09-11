package com.adinho.pdf_reader.pdf_reader;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class MoneyFormatter {

    private static final Locale LOCALE_BR = new Locale("pt", "BR");

    public static String format(BigDecimal valor) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(LOCALE_BR);
        return nf.format(valor);
    }

    public static String formatSemSimbolo(BigDecimal valor) {
        NumberFormat nf = NumberFormat.getNumberInstance(LOCALE_BR);
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(valor);
    }
}