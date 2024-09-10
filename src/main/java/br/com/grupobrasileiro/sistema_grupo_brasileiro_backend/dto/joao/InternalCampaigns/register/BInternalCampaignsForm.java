package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.InternalCampaigns.register;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

/**
 * Objeto de Transferência de Dados (DTO) para o cadastro de Campanhas Internas.
 * Este DTO encapsula as informações necessárias para o registro de uma campanha interna.
 *
 * @param idStationeryType O ID do tipo de material de escritório associado à campanha. Não pode ser nulo.
 * @param idOtherItem      O ID de outro item relacionado à campanha. Não pode ser nulo.
 * @param campaignMotto    O lema da campanha. Não pode ser nulo e deve ser fornecido.
 */
public record BInternalCampaignsForm(

        @NotNull(message = "{bInternalCampaignsForm.idStationeryType.required}")
        Long idStationeryType,

        @NotNull(message = "{bInternalCampaignsForm.idOtherItem.required}")
        Long idOtherItem,

        @NotBlank(message = "{bInternalCampaignsForm.campaignMotto.required}")
        String campaignMotto

) {
}
