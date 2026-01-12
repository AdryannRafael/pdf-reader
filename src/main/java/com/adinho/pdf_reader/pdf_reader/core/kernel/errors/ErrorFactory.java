package com.adinho.pdf_reader.pdf_reader.core.kernel.errors;


import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.specificity.ApiRequestSpecificitySystemErrors;
import com.adinho.pdf_reader.pdf_reader.core.kernel.errors.specificity.InternalSpecificitySystemErrors;

public final class ErrorFactory {
    private final NatureErrors natureErrors;
    private static final ErrorFactory externalApiErrorFactory = new ErrorFactory(NatureErrors.API_REQUEST);
    private static final ErrorFactory domainErrorFactory = new ErrorFactory(NatureErrors.DOMAIN);
    private static final ErrorFactory negotiateErrorFactory = new ErrorFactory(NatureErrors.NEGOTIATE);
    private static final ErrorFactory internalErrorFactory = new ErrorFactory(NatureErrors.INTERNAL);
    private static final ErrorFactory databaseErrorFactory = new ErrorFactory(NatureErrors.DATABASE);

    private ErrorFactory(NatureErrors natureErrors) {
        this.natureErrors = natureErrors;
    }

    public ErrorFactory(String code) {
        if (code == null || code.isBlank() || code.length() != 6) {
            throw new IllegalArgumentException("Codigo invalido");
        }
        this.natureErrors = invokeCategoryErrorByCode(code);
    }


    private NatureErrors invokeCategoryErrorByCode(String code) {
        String codeCategory = code.substring(0, 3);
        return NatureErrors.with(codeCategory);
    }

    public SystemError create(SpecificityError specificity) {
        switch (this.natureErrors) {
            case API_REQUEST -> {
                if (!specificity.getClass().isAssignableFrom(ApiRequestSpecificitySystemErrors.class)) {
                    throw new IllegalArgumentException("Especificidade de erro incompativel com a categoria");
                }
            }
            case INTERNAL -> {
                if (!specificity.getClass().isAssignableFrom(InternalSpecificitySystemErrors.class)) {
                    throw new IllegalArgumentException("Especificidade de erro incompativel com a categoria");
                }
            }
        }
        return new SystemError(this.natureErrors, specificity);
    }

    public static ErrorFactory getFactory(NatureErrors nature) {
        return switch (nature) {
            case DOMAIN -> domainErrorFactory;
            case INTERNAL -> internalErrorFactory;
            case API_REQUEST -> externalApiErrorFactory;
            case NEGOTIATE -> negotiateErrorFactory;
            case DATABASE -> databaseErrorFactory;
            default -> throw new IllegalArgumentException("Natureza invalida");
        };
    }
}
