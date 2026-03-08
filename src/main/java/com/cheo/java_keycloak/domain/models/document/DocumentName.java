package com.cheo.java_keycloak.domain.models.document;

import com.cheo.java_keycloak.domain.exceptions.DocumentValidationException;

public record DocumentName(String value) {

    public DocumentName {
        if (value == null || value.isBlank()) {
            throw new DocumentValidationException("Document name cannot be empty");
        }

        if (value.length() > 255) {
            throw new DocumentValidationException("Document name too long");
        }

    }

}
