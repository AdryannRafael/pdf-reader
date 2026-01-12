package com.adinho.pdf_reader.pdf_reader.core.kernel.errors.specificity;

import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.SpecificityError;

public enum DatabaseSpecificitySystemErrors implements SpecificityError {
    /**/
    NOT_FOUND("001")
    ;

    private final String value;

    DatabaseSpecificitySystemErrors(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
