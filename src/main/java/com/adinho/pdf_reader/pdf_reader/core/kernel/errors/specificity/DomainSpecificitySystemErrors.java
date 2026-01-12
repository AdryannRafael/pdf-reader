package com.adinho.pdf_reader.pdf_reader.core.kernel.errors.specificity;

import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.SpecificityError;

public enum DomainSpecificitySystemErrors implements SpecificityError {
    /**/
    INVALID_CHANGE_STATE("501"), INVALID_ENTITY("502"), INVALID_OPERATION("503")
    ;

    private final String value;

    DomainSpecificitySystemErrors(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
