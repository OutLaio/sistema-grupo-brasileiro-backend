package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view;

/**
 * Objeto de Transferência de Dados (DTO) para visualização de Tipo de Comunicado.
 * Este DTO encapsula as informações de um tipo de comunicado para exibição.
 *
 * @param id          O ID do tipo de comunicado.
 * @param description A descrição do tipo de comunicado.
 */
public record HandoutTypeView(

        Long id,
        String description

) {
}
