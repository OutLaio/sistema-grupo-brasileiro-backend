package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Objeto de Transferência de Dados (DTO) para visualização de um Comunicado.
 * Este DTO encapsula as informações de um comunicado para exibição.
 *
 * @param id              O ID do comunicado.
 * @param handoutType     O tipo de comunicado, representado pelo DTO {@link HandoutTypeView}.
 */
@Schema(description = "Representação detalhada de um comunicado para exibição.")
public record BHandoutView(
		
		@Schema(description = "O identificador único do comunicado.")
        Long id,
        
        @Schema(description = "O tipo de comunicado, com detalhes adicionais.")
        HandoutTypeView handoutType

) {
}
