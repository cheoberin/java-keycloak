package com.cheo.java_keycloak.domain.models.document;

import com.cheo.java_keycloak.domain.exceptions.DocumentValidationException;

public record DocumentPages(Integer value) {

    public DocumentPages {
        if (value == null) {
            throw new DocumentValidationException("Pages cannot be null");
        }

        if (value <= 0) {
            throw new DocumentValidationException("Pages must be greater than zero");
        }
    }
}
