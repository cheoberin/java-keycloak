package com.cheo.java_keycloak.domain.exceptions;

public class DocumentNotFoundException extends RuntimeException {

    public static final String IDENTIFIER = "document.not-found";

    public DocumentNotFoundException(String message) {
        super(message);
    }

}
