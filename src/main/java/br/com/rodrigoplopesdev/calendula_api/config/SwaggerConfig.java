package br.com.rodrigoplopesdev.calendula_api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Minha API REST")
                .version("v1")
                .description("Documentação da API de produtos")
                .contact(new Contact()
                    .name("Rodrigo Lopes")
                    .email("seuemail@exemplo.com")));
    }
}