package com.cheo.java_keycloak.domain.port.input;

import com.cheo.java_keycloak.domain.models.document.Document;
import com.cheo.java_keycloak.domain.models.document.DocumentCommand;

import java.util.List;
import java.util.UUID;

public interface DocumentUseCase {

    List<Document> getAllDocuments();

    Document getDocumentById(UUID id);

    Document createDocument(DocumentCommand document);

    Document updateDocument(UUID id, DocumentCommand document);

    void deleteDocument(UUID id);

    Document activateDocument(UUID id);

    Document deactivateDocument(UUID id);
}
