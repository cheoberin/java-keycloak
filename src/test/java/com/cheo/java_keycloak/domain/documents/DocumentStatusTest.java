package com.cheo.java_keycloak.domain.documents;

import com.cheo.java_keycloak.domain.exceptions.DocumentValidationException;
import com.cheo.java_keycloak.domain.models.document.DocumentStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentStatusTest {


    @Test
    void shouldCreateActiveStatus() {
        DocumentStatus status = DocumentStatus.active();

        assertTrue(status.isActive());
        assertFalse(status.isInactive());
    }

    @Test
    void shouldCreateInactiveStatus() {
        DocumentStatus status = DocumentStatus.inactive();

        assertTrue(status.isInactive());
        assertFalse(status.isActive());
    }

    @Test
    void shouldThrowExceptionWhenStatusNull() {
        assertThrows(DocumentValidationException.class,
                () -> new DocumentStatus(null));
    }

}
