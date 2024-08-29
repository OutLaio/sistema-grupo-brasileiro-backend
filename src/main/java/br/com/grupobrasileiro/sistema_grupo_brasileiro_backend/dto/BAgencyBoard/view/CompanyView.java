package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.BAgencyBoard.view;

/**
 * Representa uma visão das informações de uma empresa.
 *
 * <p>Este record é usado para exibir os detalhes básicos de uma empresa,
 * incluindo seu identificador único e nome.</p>
 */
public record CompanyView(

        /**
         * Identificador único da empresa.
         * Este campo é obrigatório e representa o ID da empresa no banco de dados.
         */
        Long id,

        /**
         * Nome da empresa.
         * Este campo é obrigatório e representa o nome da empresa.
         */
        String name

) {
}
