package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.bAgencyBoard.register;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Representa um formulário para registrar ou atualizar medições de um quadro de BAgency.
 *
 * <p>Este registro contém informações sobre as medições de um quadro, incluindo o ID do quadro, altura e comprimento.</p>
 */
public record MeasurementsForm(



        /**
         * Altura do quadro.
         * Este campo é obrigatório e deve ser um valor positivo.
         */
        @NotNull(message = "{measurementsForm.height.required}")
        @DecimalMin(value = "0.0", inclusive = false, message = "{measurementsForm.height.min}")
        BigDecimal height,

        /**
         * Comprimento do quadro.
         * Este campo é obrigatório e deve ser um valor positivo.
         */
        @NotNull(message = "{measurementsForm.length.required}")
        @DecimalMin(value = "0.0", inclusive = false, message = "{measurementsForm.length.min}")
        BigDecimal length

) {
}
