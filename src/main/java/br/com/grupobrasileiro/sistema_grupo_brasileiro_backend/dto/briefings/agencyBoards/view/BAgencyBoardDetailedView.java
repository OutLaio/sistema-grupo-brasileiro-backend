package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;

public record BAgencyBoardDetailedView(

        BAgencyBoardView bAgencyBoardView,

        ProjectView projectView,

        BriefingView briefingView
) {
}
