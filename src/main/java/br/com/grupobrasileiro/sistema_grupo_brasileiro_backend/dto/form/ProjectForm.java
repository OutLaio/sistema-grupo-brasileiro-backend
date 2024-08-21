package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import jakarta.validation.constraints.NotBlank;

public record ProjectForm(
        @NotBlank(message = "O título é obrigatório!") String title,

        @NotBlank(message = "Description is required!") String description
) {

}
