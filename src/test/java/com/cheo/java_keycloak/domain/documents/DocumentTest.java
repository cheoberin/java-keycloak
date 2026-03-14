package com.cheo.java_keycloak.domain.documents;

import com.cheo.java_keycloak.domain.exceptions.DocumentConflictException;
import com.cheo.java_keycloak.domain.exceptions.DocumentValidationException;
import com.cheo.java_keycloak.domain.models.document.Document;
import com.cheo.java_keycloak.domain.models.document.DocumentCategory;
import com.cheo.java_keycloak.domain.models.document.DocumentCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {

    private DocumentCommand command() {
        return new DocumentCommand(
                "My Document",
                10,
                DocumentCategory.JUDICIAL
        );
    }

    @Test
    void shouldCreateDocument() {
        Document document = new Document(command());

        assertEquals("My Document", document.getName().value());
        assertEquals(DocumentCategory.JUDICIAL, document.getCategory());
        assertEquals(10, document.getPages().value());
        assertEquals(1, document.getVersion());
        assertTrue(document.getStatus().isInactive());
        assertNotNull(document.getCreatedAt());
    }

    @Test
    void shouldUpdateDocument() {
        Document document = new Document(command());

        DocumentCommand update = new DocumentCommand(
                "Updated",
                20,
                DocumentCategory.COMMERCIAL
        );

        document.applyUpdate(update);

        assertEquals("Updated", document.getName().value());
        assertEquals(20, document.getPages().value());
        assertEquals(DocumentCategory.COMMERCIAL, document.getCategory());
        assertEquals(2, document.getVersion());
        assertNotNull(document.getUpdatedAt());
    }

    @Test
    void shouldActivateDocument() {
        Document document = new Document(command());

        document.activate();

        assertTrue(document.getStatus().isActive());
        assertNotNull(document.getUpdatedAt());
    }

    @Test
    void shouldThrowWhenActivatingAlreadyActiveDocument() {
        Document document = new Document(command());

        document.activate();

        assertThrows(DocumentConflictException.class,
                document::activate);
    }

    @Test
    void shouldDeactivateDocument() {
        Document document = new Document(command());

        document.activate();
        document.deactivate();

        assertTrue(document.getStatus().isInactive());
    }

    @Test
    void shouldThrowWhenDeactivatingInactiveDocument() {
        Document document = new Document(command());

        assertThrows(DocumentConflictException.class,
                document::deactivate);
    }

    @Test
    void shouldThrowWhenCategoryNull() {
        DocumentCommand command = new DocumentCommand(
                "Doc",
                10,
                null
        );

        assertThrows(DocumentValidationException.class,
                () -> new Document(command));
    }


}
