package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.bAgencyBoard.view;

/**
 * Representa uma visão das informações da cidade.
 *
 * <p>Este record é usado para exibir os detalhes básicos de uma cidade,
 * incluindo seu identificador único e nome.</p>
 */
public record CityView(

        /**
         * Identificador único da cidade.
         * Este campo é obrigatório e representa o ID da cidade no banco de dados.
         */
        Long id,

        /**
         * Nome da cidade.
         * Este campo é obrigatório e representa o nome da cidade.
         */
        String name

) {
}
