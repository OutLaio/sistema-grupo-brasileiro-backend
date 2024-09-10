package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.BAgencyBoards;

import jakarta.validation.constraints.NotBlank;

public record CompanyForm(
        @NotBlank(message = "The company name cannot be blank")
        String name
) {
}
