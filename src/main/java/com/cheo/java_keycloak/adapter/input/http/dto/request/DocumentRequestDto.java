package com.cheo.java_keycloak.adapter.input.http.dto.request;

import com.cheo.java_keycloak.domain.models.document.DocumentCategory;
import com.cheo.java_keycloak.domain.models.document.DocumentCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DocumentRequestDto(
        @NotBlank
        String name,
        @NotNull
        Integer pages,
        @NotNull
        DocumentCategory category
) {

    public DocumentCommand getDocumentCommand() {
        return new DocumentCommand(this.name, this.pages, this.category);
    }

}
