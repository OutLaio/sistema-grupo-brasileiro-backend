package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.BAgencyBoard.view;

/**
 * Representa uma visão detalhada das rotas associadas a um quadro de BAgency.
 *
 * <p>Este record é usado para exibir informações sobre rotas, incluindo a cidade da empresa associada e o tipo de rota.</p>
 */
public record RoutesView(

        /**
         * Identificador único da rota.
         * Este campo representa o ID da rota no banco de dados.
         */
        Long id,

        /**
         * Cidade da empresa associada à rota.
         * Este campo representa o ID da cidade que está relacionada à rota.
         */
        CompaniesCitiesView companiesCitiesView,

        /**
         * Tipo de rota.
         * Este campo representa o tipo de rota associada ao quadro de BAgency.
         */
        String type

) {
}
