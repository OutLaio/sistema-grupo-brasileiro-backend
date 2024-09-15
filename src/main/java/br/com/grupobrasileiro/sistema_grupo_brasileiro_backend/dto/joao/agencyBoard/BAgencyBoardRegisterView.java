package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.agencyBoard;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.signpost.view.BSignpostView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.agencyBoards.view.BAgencyBoardView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.projects.view.ProjectView;

public record BAgencyBoardRegisterView(

        BAgencyBoardView bAgencyBoardView,

        ProjectView projectView,

        BriefingView briefingView
) {
}
