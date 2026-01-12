package com.adinho.pdf_reader.pdf_reader.core.kernel.exceptions;

import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.ErrorFactory;
import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.SpecificityError;
import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.SystemError;

import java.util.Objects;
import java.util.function.Supplier;

public abstract class ErrorException extends RuntimeException implements Supplier<ErrorException> {
    private final ErrorFactory factory;
    private final SystemError systemError;

    protected ErrorException(String message, final ErrorFactory factory,
                             final SpecificityError specificityError) {
        super(message);
        this.factory = Objects.requireNonNull(factory);
        this.systemError = factory.create(specificityError);
    }

    public ErrorFactory getFactory() {
        return factory;
    }

    public SystemError getSystemError() {
        return systemError;
    }
}
