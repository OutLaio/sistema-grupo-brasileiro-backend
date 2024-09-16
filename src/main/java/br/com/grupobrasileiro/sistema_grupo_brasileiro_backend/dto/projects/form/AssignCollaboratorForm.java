package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;

public record AssignCollaboratorForm(
        @NotNull(message = "The Id of the Collaborator is required")
        @JsonAlias({"id_collaborator"})
        Long idCollaborator
) {
}
