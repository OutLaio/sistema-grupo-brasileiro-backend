package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.companies.view;

/**
 * Objeto de Transferência de Dados (DTO) para visualização de Briefings de Empresas.
 * Este DTO encapsula as informações sobre a associação entre uma empresa e um briefing para exibição.
 *
 * @param idCompany  O ID da empresa.
 * @param idBriefing O ID do briefing.
 */
public record CompaniesBriefingsView(

        Long idCompany,

        Long idBriefing

) {
}
