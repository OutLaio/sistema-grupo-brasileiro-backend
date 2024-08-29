package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.BAgencyBoard.view;

/**
 * Representa uma visão consolidada das informações de uma empresa e de uma cidade associada.
 *
 * <p>Este record é usado para exibir os detalhes de uma empresa e de uma cidade,
 * incluindo seus identificadores únicos, nome da empresa e nome da cidade.</p>
 */
public record CompaniesCitiesView(

        /**
         * Identificador único da associação entre empresa e cidade.
         * Este campo é obrigatório e representa o ID da associação no banco de dados.
         */
        Long id,

        /**
         * Informações da empresa.
         *
         * <p>Este campo contém os detalhes da empresa, incluindo o identificador único e o nome.
         * É representado pelo record {@link CompanyView}.</p>
         */
        CompanyView companyView,

        /**
         * Informações da cidade.
         *
         * <p>Este campo contém os detalhes da cidade, incluindo o identificador único e o nome.
         * É representado pelo record {@link CityView}.</p>
         */
        CityView cityView

) {
}
