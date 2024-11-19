package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API do Sistema Grupo Brasileiro")
                        .version("1.0")
                        .description("Documentação detalhada para o Sistema Grupo Brasileiro"));
    }
}
