package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.measurements.view;

import java.math.BigDecimal;

/**
 * Objeto de Transferência de Dados (DTO) para visualização de Medidas.
 * Este DTO encapsula as informações de medidas para exibição.
 *
 * @param height A altura medida.
 * @param length O comprimento medido.
 */
public record MeasurementsView(

        BigDecimal height,

        BigDecimal length

) {
}
