package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.BAgencyBoard.register;

import jakarta.validation.constraints.*;

/**
 * Representa um formulário para registrar ou atualizar rotas de uma empresa de BAgency.
 *
 * <p>Este registro contém informações sobre as rotas, incluindo o ID da cidade da empresa e o tipo de rota.</p>
 */
public record RoutesForm(

        /**
         * ID da cidade da empresa associada à rota.
         * Este campo é obrigatório e não pode ser nulo.
         */
        @NotNull(message = "{routesForm.idCityCompany.required}")
        Long idCityCompany,

        /**
         * Tipo de rota.
         * Este campo é obrigatório e não pode estar em branco.
         */
        @NotBlank(message = "{routesForm.type.required}")
        @Size(max = 50, message = "{routesForm.type.size}")
        String type

) {
}
