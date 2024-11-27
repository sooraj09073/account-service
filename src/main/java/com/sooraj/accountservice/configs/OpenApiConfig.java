package com.sooraj.accountservice.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(
        contact = @Contact(
                name = "Sooraj R",
                email ="soorajgreshmam@gmail.com"
        ),
        description = "Account Service api documentation",
        title = "Open API Specification - Account Service ",
        version = "1.0.0"
        ),
        servers = @Server(
                description = "local ENV",
                url = "http://localhost:8080"
        )
    )
public class OpenApiConfig {
}
