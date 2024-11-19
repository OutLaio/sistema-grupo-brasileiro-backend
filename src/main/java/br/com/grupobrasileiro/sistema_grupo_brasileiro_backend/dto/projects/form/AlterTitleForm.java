package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form;

import jakarta.validation.constraints.NotBlank;

public record AlterTitleForm(
        @NotBlank(message = "Please enter a new title for the projectForm!")
        String newTitle
) {
}
