package com.cheo.java_keycloak.adapter.output.jpa.repositories;

import com.cheo.java_keycloak.adapter.output.jpa.entities.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, UUID> {

}
