package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.signpost;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.signpost.view.BSignpostView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.projects.view.ProjectView;

public record SignpostRegisterView(

        BSignpostView bSignpostView,

        ProjectView projectView,

        BriefingView briefingView

) {
}
