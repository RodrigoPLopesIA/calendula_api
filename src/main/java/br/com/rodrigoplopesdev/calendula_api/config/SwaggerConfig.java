package br.com.rodrigoplopesdev.calendula_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Calendula API")
                .description("This API provides endpoints to manage products, users, and orders in an online catalog system.")
                .version("v1")
                .contact(new Contact().email("rodrigo@email.com"))
            );
    }
}