package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import jakarta.validation.constraints.NotBlank;

public record CompanyForm(
        @NotBlank(message = "Name is required!") String name) {

}
