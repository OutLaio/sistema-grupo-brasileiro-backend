package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.companiesBriefing.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyView;

import java.util.Set;

/**
 * Objeto de Transferência de Dados (DTO) para visualização de Briefings de Empresas.
 * Este DTO encapsula as informações sobre a associação entre uma empresa e um briefing para exibição.
 *
 * @param companies  Lista de views das empresas.
 */
public record CompaniesBriefingsView(

        Set<CompanyView> companies

) {
}
