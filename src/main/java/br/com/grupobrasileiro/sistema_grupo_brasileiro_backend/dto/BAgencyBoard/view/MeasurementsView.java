package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.BAgencyBoard.view;

import java.math.BigDecimal;

/**
 * Representa uma visão das medições de um quadro de BAgency.
 *
 * <p>Este record é usado para exibir as medições básicas de um quadro,
 * incluindo o identificador único do quadro, altura e comprimento.</p>
 */
public record MeasurementsView(

        /**
         * Identificador único do quadro.
         * Este campo representa o ID do quadro no banco de dados.
         */
        Long id,

        /**
         * Altura do quadro.
         * Este campo representa a altura do quadro.
         */
        BigDecimal height,

        /**
         * Comprimento do quadro.
         * Este campo representa o comprimento do quadro.
         */
        BigDecimal length

) {
}
