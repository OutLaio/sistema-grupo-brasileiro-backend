package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

public record NewVersionForm(
        @NotBlank(message = "The product link is required")
        @JsonAlias({"product_link"})
        String productLink
) {
}
