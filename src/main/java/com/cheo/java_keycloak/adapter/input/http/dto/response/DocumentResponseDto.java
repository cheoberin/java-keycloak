package com.cheo.java_keycloak.adapter.input.http.dto.response;

import com.cheo.java_keycloak.domain.models.document.Document;

import java.util.UUID;

public record DocumentResponseDto(
        UUID id,
        String name,
        Integer version
) {

    public DocumentResponseDto(Document document) {
        this(document.getId(), document.getName().value(), document.getVersion());
    }

}
