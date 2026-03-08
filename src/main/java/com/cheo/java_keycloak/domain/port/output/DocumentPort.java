package com.cheo.java_keycloak.domain.port.output;

import com.cheo.java_keycloak.domain.models.document.Document;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentPort {

    List<Document> findAllDocuments();

    Optional<Document> findDocumentById(UUID id);

    Document save(Document newDocument);

    void deleteById(UUID id);

}
