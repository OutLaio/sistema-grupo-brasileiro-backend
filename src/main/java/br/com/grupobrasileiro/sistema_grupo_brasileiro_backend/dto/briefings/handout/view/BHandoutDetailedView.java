package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detailed view of a registered handout with projectForm and briefing information.")
public record BHandoutDetailedView(
		@Schema(description = "The handout details")
		BHandoutView bHandoutView, 
		
		@Schema(description = "The associated projectForm details")
        ProjectView projectView,
        
        @Schema(description = "The briefing details associated with the handout")
        BriefingView briefingView
        
		) {

}
