package com.adinho.pdf_reader.pdf_reader.core.kernel.exceptions;


import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.ErrorFactory;
import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.NatureErrors;
import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.SpecificityError;

public class NegotiateException extends ErrorException {

    public NegotiateException(String message, SpecificityError specificityError) {
        super(message, ErrorFactory.getFactory(NatureErrors.NEGOTIATE), specificityError);
    }

    @Override
    public ErrorException get() {
        return this;
    }
}
