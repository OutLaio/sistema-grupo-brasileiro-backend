package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detalhes completos do Gift, incluindo informações do Projeto e Briefing associado")
public record BGiftDetailedView(
	BGiftView bGift,
	
	@Schema(description = "Informações detalhadas do Projeto associado", required = true)
    ProjectView project,

    @Schema(description = "Informações detalhadas do Briefing associado", required = true)
    BriefingView briefing
) { }

