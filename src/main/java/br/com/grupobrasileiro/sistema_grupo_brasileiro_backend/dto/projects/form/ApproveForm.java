package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form;

import jakarta.validation.constraints.NotNull;

public record ApproveForm(
        @NotNull(message = "The id project cannot be null")
        Long idProject,

        @NotNull(message = "The id version cannot be null")
        Long idVersion,

        @NotNull(message = "The approved status cannot be null")
        Boolean approved,

        String feedback
) {
}
