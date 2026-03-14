package com.cheo.java_keycloak.domain.models.document;

import com.cheo.java_keycloak.domain.exceptions.DocumentConflictException;
import com.cheo.java_keycloak.domain.exceptions.DocumentValidationException;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Document {

    private UUID id;

    private DocumentName name;

    private DocumentCategory category;

    private DocumentPages pages;

    private Integer version;

    private final OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private DocumentStatus status;

    public Document(UUID id, String name, DocumentCategory category, Integer pages, Integer version, OffsetDateTime createdAt, OffsetDateTime updatedAt, DocumentStatus status) {
        this.id = id;
        this.name = new DocumentName(name);
        this.category = category;
        this.pages = new DocumentPages(pages);
        this.version = version;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }

    public Document(DocumentCommand command) {
        this.name = new DocumentName(command.name());
        this.category = require(command.category(), "Document category cannot be null");
        this.pages = new DocumentPages(command.pages());
        this.version = 1;
        this.createdAt = OffsetDateTime.now();
        this.status = new DocumentStatus(false);
    }

    public void applyUpdate(DocumentCommand command) {
        this.name = new DocumentName(command.name());
        this.category = require(command.category(), "Document category cannot be null");
        this.pages = new DocumentPages(command.pages());
        this.version++;
        this.updatedAt = OffsetDateTime.now();
    }

    public void activate() {
        if (this.status.isActive()) {
            throw new DocumentConflictException("Document already active");
        }

        this.status = DocumentStatus.active();
        this.updatedAt = OffsetDateTime.now();
    }

    public void deactivate() {
        if (this.status.isInactive()) {
            throw new DocumentConflictException("Document already inactive");
        }

        this.status = DocumentStatus.inactive();
        this.updatedAt = OffsetDateTime.now();
    }

    private <T> T require(T value, String message) {
        if (value == null) {
            throw new DocumentValidationException(message);
        }
        return value;
    }

    public UUID getId() {
        return id;
    }

    public DocumentName getName() {
        return name;
    }

    public DocumentCategory getCategory() {
        return category;
    }

    public DocumentPages getPages() {
        return pages;
    }

    public Integer getVersion() {
        return version;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public DocumentStatus getStatus() {
        return status;
    }


}
