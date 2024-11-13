package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.briefings;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.form.RegisterInternalCampaignsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.BInternalCampaignsDetailsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.BInternalCampaign;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.internalCampaigns.InternalCampaignsService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.BriefingService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/internal-campaigns")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Internal Campaigns", description = "API for managing internal campaigns")
public class InternalCampaignsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InternalCampaignsController.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private BriefingService briefingService;

    @Autowired
    private InternalCampaignsService internalCampaignsService;

    @PostMapping
    @Transactional
    @Operation(summary = "Register a new internal campaigns", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Internal Campaigns registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BInternalCampaign.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<BInternalCampaignsDetailsView> registerInternalCampaigns(
            @Valid @RequestBody RegisterInternalCampaignsForm registerInternalCampaigns,
            UriComponentsBuilder uriBuilder
    ) {
        LOGGER.info("Iniciando o registro de uma nova campanha interna para o projeto: {}", registerInternalCampaigns.projectForm().title());

        Project project = projectService.register(registerInternalCampaigns.projectForm());
        LOGGER.info("Projeto registrado com sucesso: {}", project.getId());

        Briefing briefing = briefingService.register(registerInternalCampaigns.briefingForm(), project);
        LOGGER.info("Briefing registrado com sucesso para o projeto: {}", project.getId());

        internalCampaignsService.register(registerInternalCampaigns.internalCampaignsForm(), briefing);
        LOGGER.info("Campanha interna registrada com sucesso!");

        return ResponseEntity.created(URI.create("/api/v1/projects/" + project.getId())).body(null);
    }
}
