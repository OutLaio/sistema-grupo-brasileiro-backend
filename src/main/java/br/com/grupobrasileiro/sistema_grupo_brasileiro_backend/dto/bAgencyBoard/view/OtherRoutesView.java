package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.bAgencyBoard.view;

/**
 * Representa uma visão das informações de outras rotas de uma empresa de BAgency.
 *
 * <p>Este record é usado para exibir os detalhes básicos de outras rotas,
 * incluindo o nome da empresa, o nome da cidade e o tipo de rota associada.</p>
 */
public record OtherRoutesView(


        Long id,

        /**
         * Nome da empresa associada à rota.
         * Este campo representa o nome da empresa que está associada à rota.
         */
        String company,

        /**
         * Nome da cidade associada à rota.
         * Este campo representa o nome da cidade que está associada à rota.
         */
        String city,

        /**
         * Tipo de rota.
         * Este campo representa o tipo da rota associada.
         */
        String type

) {
}
