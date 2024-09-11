package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.companiesBriefing.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.agencyBoards.view.CompanyView;

import java.util.List;

/**
 * Objeto de Transferência de Dados (DTO) para visualização de Briefings de Empresas.
 * Este DTO encapsula as informações sobre a associação entre uma empresa e um briefing para exibição.
 *
 * @param companies  Lista de views das empresas.
 */
public record CompaniesBriefingsView(

        List<CompanyView> companies

) {
}
