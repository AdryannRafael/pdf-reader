package com.adinho.pdf_reader.pdf_reader.core.kernel.errors.specificity;


import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.SpecificityError;

public enum ApiRequestSpecificitySystemErrors implements SpecificityError {
    /**/
    MUST_REQUEST("429"),
    UNAVAILABLE("503"),
    NOT_FOUND("404"),
//    BAD_REQUEST("400"),
//    UNAUTHORIZED("401"),
//    FORBIDDEN("403")
    ;

    private final String value;

    ApiRequestSpecificitySystemErrors(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
