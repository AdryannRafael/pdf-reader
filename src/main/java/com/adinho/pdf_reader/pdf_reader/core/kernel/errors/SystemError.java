package com.adinho.pdf_reader.pdf_reader.core.kernel.errors;



import com.adinho.pdf_reader.pdf_reader.core.kernel.ddd.ValueObject;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class SystemError implements Error, ValueObject {
    private final NatureErrors category;
    private final SpecificityError specificityError;

    public SystemError(NatureErrors category, SpecificityError specificityError) {
        this.category = requireNonNull(category);
        this.specificityError = requireNonNull(specificityError);
    }

    @Override
    public NatureErrors getCategoryError() {
        return this.category;
    }

    @Override
    public String getErrorCode() {
        return this.category.value() + this.specificityError.value();
    }

    @Override
    public SpecificityError getSpecificity() {
        return this.specificityError;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SystemError that = (SystemError) o;
        return category == that.category && Objects.equals(specificityError, that.specificityError);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, specificityError);
    }

    @Override
    public String toString() {
        return "ProcessError{" +
                "category=" + category +
                ", specificityError=" + specificityError +
                '}';
    }
}
