package com.cheo.java_keycloak.domain.documents;

import com.cheo.java_keycloak.domain.exceptions.DocumentValidationException;
import com.cheo.java_keycloak.domain.models.document.DocumentName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DocumentNameTest {

    @Test
    void shouldCreateValidName() {
        DocumentName name = new DocumentName("Contract");

        assertEquals("Contract", name.value());
    }

    @Test
    void shouldThrowExceptionWhenNameNull() {
        assertThrows(DocumentValidationException.class,
                () -> new DocumentName(null));
    }

    @Test
    void shouldThrowExceptionWhenNameBlank() {
        assertThrows(DocumentValidationException.class,
                () -> new DocumentName(" "));
    }

    @Test
    void shouldThrowExceptionWhenNameTooLong() {
        String longName = "a".repeat(256);

        assertThrows(DocumentValidationException.class,
                () -> new DocumentName(longName));
    }

}
