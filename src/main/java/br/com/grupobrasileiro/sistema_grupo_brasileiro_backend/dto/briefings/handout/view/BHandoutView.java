package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view;

/**
 * Objeto de Transferência de Dados (DTO) para visualização de um Comunicado.
 * Este DTO encapsula as informações de um comunicado para exibição.
 *
 * @param id              O ID do comunicado.
 * @param handoutType     O tipo de comunicado, representado pelo DTO {@link HandoutTypeView}.
 */
public record BHandoutView(

        Long id,
        HandoutTypeView handoutType

) {
}
