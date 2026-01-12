package com.adinho.pdf_reader.pdf_reader.core.kernel.errors;

public interface Error {

    NatureErrors getCategoryError();
    String getErrorCode();
    SpecificityError getSpecificity();
}
