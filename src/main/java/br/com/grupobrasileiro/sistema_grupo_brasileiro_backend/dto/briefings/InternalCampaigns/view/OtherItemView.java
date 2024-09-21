package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view;

/**
 * Objeto de Transferência de Dados (DTO) para visualização de Outros Itens.
 * Este DTO encapsula as informações de um item adicional para exibição.
 *
 * @param id          O ID do item.
 * @param description A descrição do item.
 */
public record OtherItemView(

        Long id,
        String description

) {
}
