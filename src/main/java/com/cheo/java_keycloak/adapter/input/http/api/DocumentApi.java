package com.cheo.java_keycloak.adapter.input.http.api;

import com.cheo.java_keycloak.adapter.input.http.dto.request.DocumentRequestDto;
import com.cheo.java_keycloak.adapter.input.http.dto.response.DocumentDetailsResponseDto;
import com.cheo.java_keycloak.adapter.input.http.dto.response.DocumentResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Tag(name = "Document", description = "Endpoints for Document Management")
@RequestMapping("/api/documents")
public interface DocumentApi {

    @GetMapping
    @Operation(summary = "Return a list with all documents")
    @ApiResponse(responseCode = "200", description = "Documents successfully retrieved", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DocumentResponseDto.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing token", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(hidden = true)))
    ResponseEntity<List<DocumentResponseDto>> getAllDocuments();

    @GetMapping("/{id}")
    @Operation(summary = "Return the details from a specific document")
    @ApiResponse(responseCode = "200", description = "Document successfully retrieved", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DocumentDetailsResponseDto.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing token", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Document not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(hidden = true)))
    ResponseEntity<DocumentDetailsResponseDto> getDocument(@PathVariable UUID id);

    @PostMapping
    @Operation(summary = "Create a new document")
    @ApiResponse(responseCode = "201", description = "Documents successfully created", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DocumentDetailsResponseDto.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing token", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(hidden = true)))
    ResponseEntity<DocumentDetailsResponseDto> createDocument(@RequestBody @Valid DocumentRequestDto documentRequestDto);

    @PutMapping("/{id}")
    @Operation(summary = "Update document information")
    @ApiResponse(responseCode = "200", description = "Documents successfully updated", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DocumentDetailsResponseDto.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing token", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Document not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(hidden = true)))
    ResponseEntity<DocumentDetailsResponseDto> updateDocument(@PathVariable UUID id, @RequestBody @Valid DocumentRequestDto documentRequestDto);

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a document")
    @ApiResponse(responseCode = "204", description = "Documents successfully deleted", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing token", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(hidden = true)))
    ResponseEntity<Void> deleteDocument(@PathVariable UUID id);

    @PutMapping("/{id}/activate")
    @ApiResponse(responseCode = "200", description = "Documents successfully activated", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DocumentDetailsResponseDto.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing token", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Document not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(hidden = true)))
    ResponseEntity<DocumentDetailsResponseDto> activateDocument(@PathVariable UUID id);

    @PutMapping("/{id}/deactivate")
    @ApiResponse(responseCode = "200", description = "Documents successfully activated", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DocumentDetailsResponseDto.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing token", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Document not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(hidden = true)))
    ResponseEntity<DocumentDetailsResponseDto> deactivateDocument(@PathVariable UUID id);

}
