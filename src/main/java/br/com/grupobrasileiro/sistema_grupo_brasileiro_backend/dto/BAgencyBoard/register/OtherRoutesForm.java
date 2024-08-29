package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.BAgencyBoard.register;

import jakarta.validation.constraints.*;

/**
 * Representa um formulário para registrar ou atualizar outras rotas de uma empresa de BAgency.
 *
 * <p>Este registro contém informações sobre as outras rotas, incluindo a empresa, a cidade e o tipo de rota.</p>
 */
public record OtherRoutesForm(

        /**
         * Nome da empresa associada à rota.
         * Este campo é obrigatório e não pode estar em branco.
         */
        @NotBlank(message = "{otherRoutesForm.company.required}")
        @Size(max = 100, message = "{otherRoutesForm.company.size}")
        String company,

        /**
         * Nome da cidade associada à rota.
         * Este campo é obrigatório e não pode estar em branco.
         */
        @NotBlank(message = "{otherRoutesForm.city.required}")
        @Size(max = 100, message = "{otherRoutesForm.city.size}")
        String city,

        /**
         * Tipo de rota.
         * Este campo é obrigatório e não pode estar em branco.
         */
        @NotBlank(message = "{otherRoutesForm.type.required}")
        @Size(max = 50, message = "{otherRoutesForm.type.size}")
        String type

) {
}
