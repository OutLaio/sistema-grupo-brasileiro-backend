package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import jakarta.validation.constraints.NotNull;

public record ProjectCompleteForm(
    @NotNull(message = "Project data is required!")
    ProjectForm project,

    @NotNull(message = "Details data is required!")
    DetailsForm details
)

{}
