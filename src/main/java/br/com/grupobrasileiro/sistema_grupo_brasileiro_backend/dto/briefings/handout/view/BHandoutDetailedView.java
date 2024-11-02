package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;

public record BHandoutDetailedView(
		BHandoutView bHandoutView, 
		
        ProjectView projectView,
        
        BriefingView briefingView
        
		) {

}
