package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detailed view of the AgencyBoard, including associated projectForm and briefing information.")
public record BAgencyBoardDetailedView(
        
        @Schema(description = "The view of the specific agency board.")
        BAgencyBoardView bAgencyBoard,

        @Schema(description = "The view of the associated projectForm.")
        ProjectView project,

        @Schema(description = "The view of the associated briefing.")
        BriefingView briefing
) { }
