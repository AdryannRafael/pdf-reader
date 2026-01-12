package com.adinho.pdf_reader.pdf_reader.core.kernel.errors.specificity;

import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.SpecificityError;

public enum InternalSpecificitySystemErrors implements SpecificityError {
    /**/
    DATABASE("001"),
    INTERRUPTED_THREAD("002"),
    OTHER_ERROR("003"),
    IO("004")
    ;

    private final String value;

    InternalSpecificitySystemErrors(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
