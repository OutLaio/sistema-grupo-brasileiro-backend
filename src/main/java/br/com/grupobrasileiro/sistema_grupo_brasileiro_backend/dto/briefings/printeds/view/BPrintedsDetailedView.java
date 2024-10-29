package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;

public record BPrintedsDetailedView(
		PrintedView printed,
        ProjectView project,
        BriefingView briefing
) {
}
