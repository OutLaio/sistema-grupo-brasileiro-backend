package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.BAgencyBoard.register;

import jakarta.validation.constraints.*;

/**
 * Representa um formulário para registrar ou atualizar um quadro de BAgency.
 *
 * <p>Este registro contém informações sobre um quadro de BAgency, incluindo o ID da versão, localização do quadro, status de compartilhamento
 * da empresa, tipo de quadro, material e observações.</p>
 */
public record BAgencyBoardForm(

        /**
         * ID da versão associada ao quadro de BAgency.
         * Este campo é obrigatório e não pode ser nulo.
         */
        @NotNull(message = "{bAgencyBoardForm.idVersion.required}")
        Long idVersion,

        /**
         * Localização do quadro.
         * Este campo é obrigatório e não pode estar em branco.
         */
        @NotBlank(message = "{bAgencyBoardForm.boardLocation.required}")
        String boardLocation,

        /**
         * Indica se o quadro é compartilhado pela empresa.
         * Este campo é opcional e pode ser nulo.
         */
        @Null(message = "{bAgencyBoardForm.companySharing.null}")
        Boolean companySharing,

        /**
         * Tipo do quadro.
         * Este campo é obrigatório e não pode estar em branco.
         */
        @NotBlank(message = "{bAgencyBoardForm.boardType.required}")
        String boardType,

        /**
         * Material do quadro.
         * Este campo é opcional e pode estar em branco, mas não deve exceder o limite de tamanho especificado.
         */
        @Size(max = 100, message = "{bAgencyBoardForm.material.size}")
        String material,

        /**
         * Observações sobre o quadro.
         * Este campo é opcional e pode estar em branco, mas não deve exceder o limite de tamanho especificado.
         */
        @Size(max = 500, message = "{bAgencyBoardForm.observations.size}")
        String observations
) {
}
