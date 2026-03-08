package com.cheo.java_keycloak.adapter.input.http.handler.dto;

public record ErrorDto(
        String message,
        String identifier
) {

}
