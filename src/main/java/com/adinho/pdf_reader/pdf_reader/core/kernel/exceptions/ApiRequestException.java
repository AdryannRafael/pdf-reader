package com.adinho.pdf_reader.pdf_reader.core.kernel.exceptions;


import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.ErrorFactory;
import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.NatureErrors;
import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.specificity.ApiRequestSpecificitySystemErrors;

public class ApiRequestException extends ErrorException {
    public ApiRequestException(
            final String message,
            final ApiRequestSpecificitySystemErrors specificityErrors
    ) {
        super(message, ErrorFactory.getFactory(NatureErrors.API_REQUEST), specificityErrors);
    }

    @Override
    public ErrorException get() {
        return this;
    }
}
