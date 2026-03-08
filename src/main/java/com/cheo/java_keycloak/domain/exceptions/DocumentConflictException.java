package com.cheo.java_keycloak.domain.exceptions;

public class DocumentConflictException extends IllegalArgumentException {

    public static final String IDENTIFIER = "document.conflict-error";

    public DocumentConflictException(String message) {
        super(message);
    }
}
