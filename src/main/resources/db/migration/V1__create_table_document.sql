CREATE TABLE documents
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    category   VARCHAR(30)  NOT NULL,
    pages      INTEGER      NOT NULL,
    version    INTEGER      NOT NULL,
    created_at TIMESTAMP    NOT NULL,
    updated_at TIMESTAMP,
    status     BOOLEAN      NOT NULL
)