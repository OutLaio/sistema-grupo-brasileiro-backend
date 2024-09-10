package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.companies.register;

import jakarta.validation.constraints.NotNull;

/**
 * Objeto de Transferência de Dados (DTO) para o cadastro de Briefings de Empresas.
 * Este DTO encapsula as informações necessárias para associar um briefing a uma empresa.
 *
 * @param idCompany  O ID da empresa. Não pode ser nulo.
 * @param idBriefing O ID do briefing. Não pode ser nulo.
 */
public record CompaniesBriefingsForm(

        @NotNull(message = "{companiesBriefingsForm.idCompany.required}")
        Long idCompany,

        @NotNull(message = "{companiesBriefingsForm.idBriefing.required}")
        Long idBriefing

) {
}
