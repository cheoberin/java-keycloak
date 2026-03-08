package com.cheo.java_keycloak.domain.models.document;

import com.cheo.java_keycloak.domain.exceptions.DocumentValidationException;

public record DocumentStatus(Boolean value) {

    public DocumentStatus {
        if (value == null) {
            throw new DocumentValidationException("Document status cannot be null");
        }
    }

    public static DocumentStatus active() {
        return new DocumentStatus(true);
    }

    public static DocumentStatus inactive() {
        return new DocumentStatus(false);
    }

    public boolean isActive() {
        return value;
    }

    public boolean isInactive() {
        return !value;
    }

}
