package com.cheo.java_keycloak.domain.documents;

import com.cheo.java_keycloak.domain.exceptions.DocumentValidationException;
import com.cheo.java_keycloak.domain.models.document.DocumentPages;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DocumentPagesTest {

    @Test
    void shouldCreateValidPages() {
        DocumentPages pages = new DocumentPages(10);

        assertEquals(10, pages.value());
    }

    @Test
    void shouldAllowSinglePage() {
        DocumentPages pages = new DocumentPages(1);

        assertEquals(1, pages.value());
    }

    @Test
    void shouldThrowExceptionWhenPagesIsNull() {
        assertThrows(DocumentValidationException.class,
                () -> new DocumentPages(null));
    }

    @Test
    void shouldThrowExceptionWhenPagesIsZero() {
        assertThrows(DocumentValidationException.class,
                () -> new DocumentPages(0));
    }

    @Test
    void shouldThrowExceptionWhenPagesIsNegative() {
        assertThrows(DocumentValidationException.class,
                () -> new DocumentPages(-5));
    }

}
