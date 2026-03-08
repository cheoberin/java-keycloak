package com.cheo.java_keycloak.adapter.input.http.handler.dto;

public record FieldErrorDto(
        String field,
        String message
) {
}

