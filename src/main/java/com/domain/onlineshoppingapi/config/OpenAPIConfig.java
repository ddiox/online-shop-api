package com.domain.onlineshoppingapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;

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
    ),
    security = {
        @SecurityRequirement(
            name = "bearerAuth"
        )
    }
)
@SecurityScheme(
    name = "bearerAuth",
    description = "JWT auth description",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {
    
}
