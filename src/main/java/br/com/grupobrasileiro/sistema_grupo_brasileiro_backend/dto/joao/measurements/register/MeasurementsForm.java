package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.measurements.register;

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

        @NotNull(message = "{measurementsForm.height.required}")
        @Positive(message = "{measurementsForm.height.positive}")
        BigDecimal height,

        @NotNull(message = "{measurementsForm.length.required}")
        @Positive(message = "{measurementsForm.length.positive}")
        BigDecimal length

) {
}
