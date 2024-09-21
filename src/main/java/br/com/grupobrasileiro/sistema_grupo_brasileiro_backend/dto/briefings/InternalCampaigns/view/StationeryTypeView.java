package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view;

/**
 * Objeto de Transferência de Dados (DTO) para visualização de Tipo de Papelaria.
 * Este DTO encapsula as informações sobre um tipo de papelaria para exibição.
 *
 * @param id          O ID do tipo de papelaria.
 * @param description A descrição do tipo de papelaria.
 */
public record StationeryTypeView(

        Long id,
        String description

) {
}
