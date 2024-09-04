package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.bAgencyBoard.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.project.view.VersionView;

import java.util.List;

/**
 * Representa uma visão detalhada das informações de um quadro de BAgency.
 *
 * <p>Este record é usado para exibir detalhes completos sobre um quadro de BAgency,
 * incluindo o quadro em si, suas medidas, rotas e outras rotas associadas.</p>
 */
public record BAgencyBoardDetailsView(

        /**
         * Identificador único do quadro de BAgency.
         * Este campo representa o ID do quadro no banco de dados.
         */
        Long id,

        /**
         * Informações sobre a versão desse BAgencyBoard .
         */
        VersionView versionView,

        /**
         * Informações detalhadas do BAgencyBoard.
         * Este campo representa os detalhes do quadro de BAgency.
         */
        BAgencyBoardView bAgencyBoardView,

        /**
         * Informações detalhadas sobre as medições do quadro.
         * Este campo representa as medidas associadas ao quadro.
         */
        MeasurementsView measurementsView,

        /**
         * Lista de informações detalhadas sobre as rotas associadas ao quadro.
         * Esta lista pode ser nula, mas se fornecida, não deve conter elementos nulos.
         */
        List<RoutesView> routesViews,

        /**
         * Lista de informações detalhadas sobre outras rotas associadas ao quadro.
         * Esta lista pode ser nula, mas se fornecida, não deve conter elementos nulos.
         */
        List<OtherRoutesView> otherRoutesViews

) {
}
