package com.cheo.java_keycloak.adapter.input.http.controller;


import com.cheo.java_keycloak.adapter.input.http.api.DocumentApi;
import com.cheo.java_keycloak.adapter.input.http.dto.request.DocumentRequestDto;
import com.cheo.java_keycloak.adapter.input.http.dto.response.DocumentDetailsResponseDto;
import com.cheo.java_keycloak.adapter.input.http.dto.response.DocumentResponseDto;
import com.cheo.java_keycloak.domain.port.input.DocumentUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class DocumentController implements DocumentApi {

    private final DocumentUseCase documentUseCase;
    private final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    public DocumentController(DocumentUseCase documentUseCase) {
        this.documentUseCase = documentUseCase;
    }

    @Override
    public ResponseEntity<List<DocumentResponseDto>> getAllDocuments() {
        logger.info("GET: FIND ALL DOCUMENTS");
        var response = documentUseCase.getAllDocuments().stream().map(DocumentResponseDto::new).toList();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<DocumentDetailsResponseDto> getDocument(UUID id) {
        logger.info("GET: FIND DOCUMENT ID={}", id);
        var response = new DocumentDetailsResponseDto(documentUseCase.getDocumentById(id));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<DocumentDetailsResponseDto> createDocument(DocumentRequestDto documentRequestDto) {
        logger.info("POST: CREATE DOCUMENT NAME={}", documentRequestDto.name());
        var response = documentUseCase.createDocument(documentRequestDto.getDocumentCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(new DocumentDetailsResponseDto(response));
    }

    @Override
    public ResponseEntity<DocumentDetailsResponseDto> updateDocument(UUID id, DocumentRequestDto documentRequestDto) {
        logger.info("PUT: UPDATES ON DOCUMENT ID={}", id);
        var response = documentUseCase.updateDocument(id, documentRequestDto.getDocumentCommand());
        return ResponseEntity.ok(new DocumentDetailsResponseDto(response));
    }

    @Override
    public ResponseEntity<Void> deleteDocument(UUID id) {
        logger.info("DELETE: DELETE DOCUMENT ID={}", id);
        documentUseCase.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<DocumentDetailsResponseDto> activateDocument(UUID id) {
        logger.info("PUT: ACTIVATION DOCUMENT ID={}", id);
        var response = documentUseCase.activateDocument(id);
        return ResponseEntity.ok(new DocumentDetailsResponseDto(response));
    }

    @Override
    public ResponseEntity<DocumentDetailsResponseDto> deactivateDocument(UUID id) {
        logger.info("PUT: DEACTIVATION DOCUMENT ID={}", id);
        var response = documentUseCase.deactivateDocument(id);
        return ResponseEntity.ok(new DocumentDetailsResponseDto(response));
    }
}
