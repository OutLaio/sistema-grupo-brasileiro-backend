package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;

/**
 * Objeto de Transferência de Dados (DTO) detalhado para visualização de Placa (Signpost) em um projeto.
 * Este DTO encapsula informações completas sobre a placa, o projeto e o briefing associados.
 *
 * @param signpost Detalhes da placa, incluindo localização, setor e material.
 * @param project  Informações detalhadas sobre o projeto ao qual a placa está associada.
 * @param briefing Informações do briefing relacionado à placa no contexto do projeto.
 */
public record BSignpostDetailedView(
        BSignpostView signpost,
        ProjectView project,
        BriefingView briefing
) {
}
