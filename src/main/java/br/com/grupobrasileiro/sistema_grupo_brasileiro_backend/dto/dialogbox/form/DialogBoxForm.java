package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.form;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DialogBoxForm(
        @NotNull(message = "The Employee Id is required")
        @JsonAlias({"id_employee"})
        Long idEmployee,

        @NotNull(message = "The Briefing Id is required")
        @JsonAlias({"id_briefing"})
        Long idBriefing,

        @NotBlank(message = "The field Message is required")
        String message
) { }
