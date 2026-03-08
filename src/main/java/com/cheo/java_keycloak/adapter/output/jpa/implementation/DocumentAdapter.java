package com.cheo.java_keycloak.adapter.output.jpa.implementation;

import com.cheo.java_keycloak.adapter.output.jpa.entities.DocumentEntity;
import com.cheo.java_keycloak.adapter.output.jpa.repositories.DocumentRepository;
import com.cheo.java_keycloak.domain.models.document.Document;
import com.cheo.java_keycloak.domain.models.document.DocumentStatus;
import com.cheo.java_keycloak.domain.port.output.DocumentPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentAdapter implements DocumentPort {

    private final DocumentRepository documentRepository;

    public DocumentAdapter(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public List<Document> findAllDocuments() {
        return documentRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Document> findDocumentById(UUID id) {
        return documentRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Document save(Document newDocument) {
        var entity = documentRepository.save(toEntity(newDocument));
        return toDomain(entity);
    }

    @Override
    public void deleteById(UUID id) {
        documentRepository.deleteById(id);
    }

    private Document toDomain(DocumentEntity documentEntity) {
        return new Document(documentEntity.getId(), documentEntity.getName(), documentEntity.getCategory(), documentEntity.getPages(), documentEntity.getVersion(), documentEntity.getCreatedAt(), documentEntity.getUpdatedAt(), new DocumentStatus(documentEntity.getStatus()));
    }

    private DocumentEntity toEntity(Document document) {
        return new DocumentEntity(document.getId(), document.getName().value(), document.getCategory(), document.getPages(), document.getVersion(), document.getCreatedAt(), document.getUpdatedAt(), document.getStatus().value());
    }

}
