package com.cheo.java_keycloak.adapter.input.http.handler;

import com.cheo.java_keycloak.adapter.input.http.handler.dto.ErrorDto;
import com.cheo.java_keycloak.adapter.input.http.handler.dto.FieldErrorDto;
import com.cheo.java_keycloak.domain.exceptions.DocumentConflictException;
import com.cheo.java_keycloak.domain.exceptions.DocumentNotFoundException;
import com.cheo.java_keycloak.domain.exceptions.DocumentValidationException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdviser {

    private final Logger logger = LoggerFactory.getLogger(ControllerAdviser.class);

    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<ErrorDto> handleDocumentNotFoundException(DocumentNotFoundException error) {

        logger.warn(error.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new ErrorDto(error.getMessage(), DocumentNotFoundException.IDENTIFIER)
                );
    }

    @ExceptionHandler(DocumentValidationException.class)
    public ResponseEntity<ErrorDto> handleDocumentValidationException(DocumentValidationException error) {

        logger.warn(error.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new ErrorDto(error.getMessage(), DocumentValidationException.IDENTIFIER)
                );
    }

    @ExceptionHandler(DocumentConflictException.class)
    public ResponseEntity<ErrorDto> handleDocumentConflictException(DocumentConflictException error) {

        logger.warn(error.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new ErrorDto(error.getMessage(), DocumentConflictException.IDENTIFIER)
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<FieldErrorDto>> argumentNotValid(MethodArgumentNotValidException error) {

        logger.warn(error.getMessage());

        var fields = error.getFieldErrors().stream().map(
                fieldError -> new FieldErrorDto(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                )).toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(fields);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception e, HttpServletRequest request) {

        logger.error("Unexpected error on {} {}", request.getMethod(), request.getRequestURI(), e);

        Map<String, Object> body = Map.of(
                "status", 500,
                "error", "Internal Server Error",
                "message", e.getMessage(),
                "traceId", MDC.get("traceId")
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

}
