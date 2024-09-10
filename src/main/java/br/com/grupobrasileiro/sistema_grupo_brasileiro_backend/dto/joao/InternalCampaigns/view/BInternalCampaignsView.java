package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.InternalCampaigns.view;

/**
 * Objeto de Transferência de Dados (DTO) para visualização de Campanhas Internas.
 * Este DTO encapsula as informações sobre uma campanha interna para exibição.
 *
 * @param id                O ID da campanha interna.
 * @param stationeryTypeView O tipo de papelaria associado à campanha, representado pelo DTO {@link StationeryTypeView}.
 * @param otherItemView      Informações sobre outros itens relacionados à campanha, representadas pelo DTO {@link OtherItemView}.
 * @param campaignMotto      O lema da campanha.
 */
public record BInternalCampaignsView(

        Long id,

        StationeryTypeView stationeryTypeView,

        OtherItemView otherItemView,

        String campaignMotto

) {
}
