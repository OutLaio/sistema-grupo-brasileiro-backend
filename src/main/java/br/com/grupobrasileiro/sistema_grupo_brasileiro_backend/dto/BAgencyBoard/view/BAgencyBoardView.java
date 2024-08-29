package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.BAgencyBoard.view;

/**
 * Representa uma visão das informações de um quadro de BAgency.
 *
 * <p>Este record é usado para exibir os detalhes de um quadro de BAgency,
 * incluindo seu identificador único, ID da versão associada, localização do quadro, status de compartilhamento
 * da empresa, tipo de quadro, material e observações.</p>
 */
public record BAgencyBoardView(

        /**
         * Identificador único do quadro de BAgency.
         * Este campo representa o ID do quadro no banco de dados.
         */
        Long id,

        /**
         * ID da versão associada ao quadro de BAgency.
         * Este campo representa o ID da versão associada ao quadro.
         */
        Long idVersion,

        /**
         * Localização do quadro.
         * Este campo representa a localização do quadro.
         */
        String boardLocation,

        /**
         * Indica se o quadro é compartilhado pela empresa.
         * Este campo representa o status de compartilhamento da empresa.
         */
        Boolean companySharing,

        /**
         * Tipo do quadro.
         * Este campo representa o tipo do quadro.
         */
        String boardType,

        /**
         * Material do quadro.
         * Este campo representa o material do quadro.
         */
        String material,

        /**
         * Observações sobre o quadro.
         * Este campo contém observações adicionais sobre o quadro.
         */
        String observations

) {
}
