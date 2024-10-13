package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;

public record BGiftDetailedView(
        BGiftView bGiftView,
        
        ProjectView projectView,
        
        BriefingView briefingView
		) {

}
