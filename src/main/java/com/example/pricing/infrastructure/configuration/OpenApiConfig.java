package com.example.pricing.infrastructure.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API de Precios")
                        .description("API para gestionar y consultar precios de productos")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}