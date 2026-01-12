package com.adinho.pdf_reader.pdf_reader.core.kernel.exceptions;


import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.ErrorFactory;
import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.NatureErrors;
import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.specificity.DomainSpecificitySystemErrors;

public class DomainException extends ErrorException {

    public DomainException(
            final String message,
            final DomainSpecificitySystemErrors specificityErrors
    ) {
        super(message, ErrorFactory.getFactory(NatureErrors.DOMAIN), specificityErrors);
    }

    @Override
    public ErrorException get() {
        return this;
    }
}
