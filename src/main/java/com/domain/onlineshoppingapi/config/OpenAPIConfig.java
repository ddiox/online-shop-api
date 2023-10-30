package com.domain.onlineshoppingapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
    info = @Info(
        contact = @Contact(
            name = "Glenn Claudio Ivan Petrus",
            email = "glenn@cakap.id",
            url = "https://cakap.com"
        ),
        description = "OpenAPI Documentation for Online Shopping API",
        title = "Online Shopping API",
        version = "1.0",
        license = @License(
            name = "License name",
            url = "https://cakap.com"
        ),
        termsOfService = "Terms of Service"
    )
    
)
public class OpenAPIConfig {
    
}
