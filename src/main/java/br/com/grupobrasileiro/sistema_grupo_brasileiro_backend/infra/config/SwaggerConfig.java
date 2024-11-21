package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API do Sistema Grupo Brasileiro")
                        .version("1.0")
                        .description("Documentação detalhada para o Sistema Grupo Brasileiro")
                        .contact(new Contact()
                                .name("Sistema Grupo Brasileiro")
                                .email("suporte@grupobrasileiro.com")
                                .url("https://sistema-gb-front.onrender.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .addTagsItem(new Tag().name("Authentication").description("Authentication and access control endpoints"))
                .addTagsItem(new Tag().name("Handout").description("Communications management"))
                .addTagsItem(new Tag().name("AgencyBoard").description("Signpost managements"));
    }
}
