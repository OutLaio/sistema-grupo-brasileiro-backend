package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.measurements.form;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Objeto de Transferência de Dados (DTO) para o cadastro de Medidas.
 * Este DTO encapsula as informações necessárias para o registro das medidas.
 *
 * @param height A altura medida. Não pode ser nula e deve ser um valor positivo.
 * @param length O comprimento medido. Não pode ser nulo e deve ser um valor positivo.
 */
public record MeasurementsForm(

        @NotNull(message = "The height of the measurement cannot be null")
        @Positive(message = "The height of the measurement cannot be negative")
        BigDecimal height,

        @NotNull(message = "The length of the measurement cannot be null")
        @Positive(message = "The length of the measurement cannot be negative")
        BigDecimal length
) {
}
