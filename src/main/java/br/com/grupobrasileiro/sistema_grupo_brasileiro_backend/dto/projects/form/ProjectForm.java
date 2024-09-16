package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjectForm(
        @NotNull(message = "The id of the client cannot be null")
        @JsonAlias({"id_client"})
        Long idClient,

        @NotBlank(message = "The id of the client cannot be blank")
        String title
) {
}
