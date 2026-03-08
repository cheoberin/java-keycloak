package com.cheo.java_keycloak.adapter.input.http.dto.response;

import com.cheo.java_keycloak.domain.models.document.Document;
import com.cheo.java_keycloak.domain.models.document.DocumentCategory;

import java.time.OffsetDateTime;
import java.util.UUID;

public record DocumentDetailsResponseDto(
        UUID id,
        String name,
        DocumentCategory category,
        Integer pages,
        Integer version,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        Boolean status
) {

    public DocumentDetailsResponseDto(Document document) {
        this(document.getId(), document.getName().value(), document.getCategory(), document.getPages(), document.getVersion(), document.getCreatedAt(), document.getUpdatedAt(), document.getStatus().value());
    }

}
