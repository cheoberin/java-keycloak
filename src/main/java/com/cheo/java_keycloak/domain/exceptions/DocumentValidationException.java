package com.cheo.java_keycloak.domain.exceptions;

public class DocumentValidationException extends IllegalArgumentException {

    public static final String IDENTIFIER = "document.validation-error";

    public DocumentValidationException(String message) {
        super(message);
    }

}
