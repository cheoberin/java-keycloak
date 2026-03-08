package com.cheo.java_keycloak.domain.service;

import com.cheo.java_keycloak.domain.exceptions.DocumentNotFoundException;
import com.cheo.java_keycloak.domain.models.document.Document;
import com.cheo.java_keycloak.domain.models.document.DocumentCommand;
import com.cheo.java_keycloak.domain.port.input.DocumentUseCase;
import com.cheo.java_keycloak.domain.port.output.DocumentPort;

import java.util.List;
import java.util.UUID;

public class DocumentService implements DocumentUseCase {

    private final DocumentPort documentPort;

    public DocumentService(DocumentPort documentPort) {
        this.documentPort = documentPort;
    }

    @Override
    public List<Document> getAllDocuments() {
        return documentPort.findAllDocuments();
    }

    @Override
    public Document getDocumentById(UUID id) {
        return documentPort.findDocumentById(id)
                .orElseThrow(() -> new DocumentNotFoundException("Document with id " + id + " not found"));
    }

    @Override
    public Document createDocument(DocumentCommand documentCommand) {
        var newDocument = new Document(documentCommand);
        return documentPort.save(newDocument);
    }

    @Override
    public Document updateDocument(UUID id, DocumentCommand documentCommand) {
        var document = documentPort.findDocumentById(id)
                .orElseThrow(() -> new DocumentNotFoundException("Document with id " + id + " not found"));
        document.applyUpdate(documentCommand);
        return documentPort.save(document);
    }

    @Override
    public void deleteDocument(UUID id) {
        documentPort.deleteById(id);
    }

    @Override
    public Document activateDocument(UUID id) {

        var document = documentPort.findDocumentById(id)
                .orElseThrow(() -> new DocumentNotFoundException("Document with id " + id + " not found"));

        document.activate();

        return documentPort.save(document);
    }

    @Override
    public Document deactivateDocument(UUID id) {

        var document = documentPort.findDocumentById(id)
                .orElseThrow(() -> new DocumentNotFoundException("Document with id " + id + " not found"));

        document.deactivate();

        return documentPort.save(document);
    }

}
