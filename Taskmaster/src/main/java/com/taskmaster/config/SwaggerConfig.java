package com.taskmaster.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI taskmasterOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Taskmaster API")
                        .description("API RESTful para gerenciamento de tarefas - TASKMASTER. " +
                                "Uma API robusta, escalável e de fácil manutenção para gestão de tarefas.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipe Taskmaster")
                                .email("contato@taskmaster.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}

