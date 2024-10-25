package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;

public record BSignpostDetailedView(

        BSignpostView bSignpost,
        ProjectView project,
        BriefingView briefing

) {
}
