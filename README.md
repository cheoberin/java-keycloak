# Java Keycloak Resource Server CRUD

Simple Spring Boot CRUD project used to explore **Keycloak + OAuth2 Resource Server** integration.

The application exposes a protected REST API for managing documents and validates JWT access tokens issued by Keycloak. It also demonstrates role-based authorization, Flyway database migrations, Swagger/OpenAPI documentation, and a clean separation between HTTP, domain, and persistence layers.

## What this project does

- creates, reads, updates, and deletes documents
- activates and deactivates documents
- protects endpoints with roles extracted from a Keycloak JWT
- persists data in PostgreSQL with Spring Data JPA
- manages schema changes with Flyway
- exposes Swagger UI for local exploration
- exposes Actuator endpoints

## Tech stack

- Java 21
- Spring Boot 4
- Spring Security OAuth2 Resource Server
- Spring Web MVC
- Spring Data JPA
- Flyway
- PostgreSQL
- springdoc OpenAPI / Swagger UI
- Maven

## Domain model

The API manages a `Document` with the following fields:

- `id` - UUID
- `name` - required, trimmed, max 255 chars
- `category` - `JUDICIAL`, `COMMERCIAL`, or `PERSONAL`
- `pages` - required, must be greater than zero
- `version` - incremented on update
- `createdAt`
- `updatedAt`
- `status` - active/inactive

New documents start as **inactive**.

## Security model

This application is configured as an OAuth2 **resource server**.

- Every endpoint requires authentication except Swagger and Actuator routes.
- JWT roles are read from the Keycloak claim:

```json
{
  "resource_access": {
	"doc-application": {
	  "roles": ["DOCUMENT_READER", "DOCUMENT_MANAGER", "DOCUMENT_ADMIN"]
	}
  }
}
```

The Keycloak client expected by the application is:

- `doc-application`

### Required roles per action

| Role | Access |
| --- | --- |
| `DOCUMENT_READER` | list and view documents |
| `DOCUMENT_MANAGER` | create, update, activate, deactivate |
| `DOCUMENT_ADMIN` | delete documents |

## API endpoints

Base path: `/api/documents`

| Method | Path | Role | Description |
| --- | --- | --- | --- |
| `GET` | `/api/documents` | `DOCUMENT_READER` | List all documents |
| `GET` | `/api/documents/{id}` | `DOCUMENT_READER` | Get one document |
| `POST` | `/api/documents` | `DOCUMENT_MANAGER` | Create a document |
| `PUT` | `/api/documents/{id}` | `DOCUMENT_MANAGER` | Update a document |
| `DELETE` | `/api/documents/{id}` | `DOCUMENT_ADMIN` | Delete a document |
| `PUT` | `/api/documents/{id}/activate` | `DOCUMENT_MANAGER` | Activate a document |
| `PUT` | `/api/documents/{id}/deactivate` | `DOCUMENT_MANAGER` | Deactivate a document |

### Sample create/update payload

```json
{
  "name": "Contract 2026",
  "pages": 12,
  "category": "COMMERCIAL"
}
```

## Profiles and configuration

The application reads the active profile from:

- `SPRING_PROFILES_ACTIVE`

### Local profile

The `local` profile is already configured for:

- PostgreSQL at `jdbc:postgresql://localhost:5432/keycloak`
- user `postgres`
- password `postgres`
- Keycloak issuer URI `http://localhost:8081/realms/documents`

### Dev / UAT / Prod profiles

These profiles use environment variables:

- `DB_URL`
- `DB_NAME`
- `DB_USER`
- `DB_PASSWORD`
- `KEYCLOAK_ISSUER_URI`

Swagger/OpenAPI is disabled in `uat` and `prod`.

## Running locally

### Prerequisites

- Java 21
- PostgreSQL
- Keycloak

### 1. Create the database

Create a PostgreSQL database named `keycloak`.

Flyway will create the `documents` table automatically when the app starts.

### 2. Prepare Keycloak

Create the following Keycloak resources:

- realm: `documents`
- client: `doc-application`
- roles: `DOCUMENT_READER`, `DOCUMENT_MANAGER`, `DOCUMENT_ADMIN`

Make sure the access token includes client roles inside `resource_access.doc-application.roles`.

### 3. Start the application

```powershell
$env:SPRING_PROFILES_ACTIVE="local"
.\mvnw.cmd spring-boot:run
```

The API will start on:

- `http://localhost:8080`

## Swagger and Actuator

When running locally, you can explore the API here:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Actuator health: `http://localhost:8080/actuator/health`

Swagger and Actuator endpoints are publicly accessible in the current security configuration.

## Example authenticated requests

Replace `<TOKEN>` with a valid Keycloak access token.

```powershell
$token = "<TOKEN>"

Invoke-RestMethod -Method Get `
  -Uri "http://localhost:8080/api/documents" `
  -Headers @{ Authorization = "Bearer $token" }
```

```powershell
$token = "<TOKEN>"
$body = @{
  name = "Contract 2026"
  pages = 12
  category = "COMMERCIAL"
} | ConvertTo-Json

Invoke-RestMethod -Method Post `
  -Uri "http://localhost:8080/api/documents" `
  -Headers @{ Authorization = "Bearer $token" } `
  -ContentType "application/json" `
  -Body $body
```

## Error handling

The application returns structured errors for common cases such as:

- validation errors (`400`)
- document not found (`404`)
- conflict state changes, such as activating an already active document (`409`)
- forbidden access (`403`)

Unexpected errors include a `traceId`. You can also pass your own `X-Trace-Id` request header.

## Build and test

```powershell
.\mvnw.cmd test
```

## Docker image

The repository includes a multi-stage `Dockerfile`.

```powershell
docker build -t java-keycloak .
docker run --rm -p 8080:8080 java-keycloak
```

For containerized execution, provide the same environment variables required by the selected Spring profile.

## Project structure

The codebase is organized in a clean architecture style:

- `adapter/input/http` - controllers, DTOs, security, exception handling
- `domain/models` - domain entities and value objects
- `domain/service` - business rules
- `domain/port` - input/output ports
- `adapter/output/jpa` - persistence adapter and repository
- `application/config` - bean wiring

## Purpose

This project is a good starter reference if you want to explore:

- how Spring Boot validates Keycloak JWTs
- how to map Keycloak client roles into Spring Security roles
- how to protect CRUD endpoints with `@PreAuthorize`
- how to combine PostgreSQL, Flyway, and Swagger in a secured API
