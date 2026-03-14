package com.cheo.java_keycloak.domain.service;

import com.cheo.java_keycloak.domain.exceptions.DocumentNotFoundException;
import com.cheo.java_keycloak.domain.models.document.Document;
import com.cheo.java_keycloak.domain.models.document.DocumentCategory;
import com.cheo.java_keycloak.domain.models.document.DocumentCommand;
import com.cheo.java_keycloak.domain.port.output.DocumentPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DocumentServiceTest {

    @Mock
    private DocumentPort documentPort;

    private DocumentService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new DocumentService(documentPort);
    }

    private DocumentCommand command() {
        return new DocumentCommand(
                "Doc",
                10,
                DocumentCategory.JUDICIAL
        );
    }

    @Test
    void shouldReturnAllDocuments() {

        var doc = new Document(command());

        when(documentPort.findAllDocuments())
                .thenReturn(List.of(doc));

        var result = service.getAllDocuments();

        assertEquals(1, result.size());
        verify(documentPort).findAllDocuments();
    }

    @Test
    void shouldReturnDocumentById() {

        var id = UUID.randomUUID();
        var doc = new Document(command());

        when(documentPort.findDocumentById(id))
                .thenReturn(Optional.of(doc));

        var result = service.getDocumentById(id);

        assertEquals(doc, result);
        verify(documentPort).findDocumentById(id);
    }

    @Test
    void shouldThrowWhenDocumentNotFound() {

        var id = UUID.randomUUID();

        when(documentPort.findDocumentById(id))
                .thenReturn(Optional.empty());

        assertThrows(DocumentNotFoundException.class,
                () -> service.getDocumentById(id));
    }

    @Test
    void shouldCreateDocument() {

        var command = command();
        var document = new Document(command);

        when(documentPort.save(any()))
                .thenReturn(document);

        var result = service.createDocument(command);

        assertNotNull(result);
        verify(documentPort).save(any());
    }

    @Test
    void shouldUpdateDocument() {

        var id = UUID.randomUUID();
        var document = new Document(command());

        when(documentPort.findDocumentById(id))
                .thenReturn(Optional.of(document));

        when(documentPort.save(any()))
                .thenReturn(document);

        var result = service.updateDocument(id, command());

        assertEquals(2, result.getVersion());
        verify(documentPort).save(document);
    }

    @Test
    void shouldDeleteDocument() {

        var id = UUID.randomUUID();

        service.deleteDocument(id);

        verify(documentPort).deleteById(id);
    }

    @Test
    void shouldActivateDocument() {

        var id = UUID.randomUUID();
        var document = new Document(command());

        when(documentPort.findDocumentById(id))
                .thenReturn(Optional.of(document));

        when(documentPort.save(any()))
                .thenReturn(document);

        var result = service.activateDocument(id);

        assertTrue(result.getStatus().isActive());
        verify(documentPort).save(document);
    }

    @Test
    void shouldDeactivateDocument() {

        var id = UUID.randomUUID();
        var document = new Document(command());

        document.activate();

        when(documentPort.findDocumentById(id))
                .thenReturn(Optional.of(document));

        when(documentPort.save(any()))
                .thenReturn(document);

        var result = service.deactivateDocument(id);

        assertTrue(result.getStatus().isInactive());
        verify(documentPort).save(document);
    }

}
