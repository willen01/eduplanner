package com.dev.willen.eduplanner.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "willen dos Santos",
                        email = "willendev@contato.com.br",
                        url = "https://willendev.com.br"
                ),
                description = "OpenApi documentation for Spring Security",
                title = "Eduplanner service",
                version = "1.0"
        )
)
//Esquema espefico para cada controlador
@SecurityScheme(
        name = "BearerAuth",
        description = "Jwt auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
