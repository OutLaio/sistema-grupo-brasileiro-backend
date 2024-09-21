package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form;

import jakarta.validation.constraints.NotBlank;

public record OtherRoutesForm(

        @NotBlank(message = "The company cannot be blank")
        String company,

        @NotBlank(message = "The city cannot be blank")
        String city,

        @NotBlank(message = "The type cannot be blank")
        String type
) {
}
