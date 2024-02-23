package com.compassuol.challenge3.User.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Microsserviço User API")
                                .description("3º SPRINGBOOT Challenge - Projeto de Gerenciamento de Usuários")
                                .version("v1.0")
                                .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"))
                                .contact(new Contact().name("Pedro Kober").email("pedrokoberstain2@gmail.com"))
                );
    }
}
