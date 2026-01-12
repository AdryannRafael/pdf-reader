package com.adinho.pdf_reader.pdf_reader.core.kernel.errors.specificity;

import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.SpecificityError;

public enum NegotiateSpecificitySystemErrors implements SpecificityError {
    /**/
    REGISTRE_NOT_FOUND("001")
    ;

    private final String value;

    NegotiateSpecificitySystemErrors(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
