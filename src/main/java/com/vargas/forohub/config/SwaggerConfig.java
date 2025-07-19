package com.vargas.forohub.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI foroHubOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API ForoHub")
                        .description("Documentación de la API REST para el sistema de foros ForoHub")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Soporte ForoHub")
                                .email("soporte@forohub.com")
                                .url("https://forohub.com/contacto"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }
}
