package com.cheo.java_keycloak.domain.models.document;

public record DocumentCommand(
        String name,
        Integer pages,
        DocumentCategory category
) {
}
