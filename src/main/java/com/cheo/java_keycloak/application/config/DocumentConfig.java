package com.cheo.java_keycloak.application.config;

import com.cheo.java_keycloak.domain.port.input.DocumentUseCase;
import com.cheo.java_keycloak.domain.port.output.DocumentPort;
import com.cheo.java_keycloak.domain.service.DocumentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentConfig {

    @Bean
    public DocumentUseCase documentUseCase(DocumentPort documentPort) {
        return new DocumentService(documentPort);
    }

}
