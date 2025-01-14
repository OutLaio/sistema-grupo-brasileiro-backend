package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.briefings;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.Response;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.form.RegisterInternalCampaignsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.form.DialogBoxForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.BInternalCampaign;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.internalCampaigns.InternalCampaignsService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.dialogbox.DialogBoxService;
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
import java.util.UUID;

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

    @Autowired
    private DialogBoxService dialogBoxService;

    /**
     * Registers a new internal campaign for a project.
     *
     * @param registerInternalCampaigns the data to register a internal campaign
     * @param uriBuilder builder for creating the location URI
     * @return a response entity containing a message success
     */
    @PostMapping
    @Transactional
    @Operation(summary = "Register a new internal campaigns", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Internal Campaigns registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<?> registerInternalCampaigns(
            @Valid @RequestBody RegisterInternalCampaignsForm registerInternalCampaigns,
            UriComponentsBuilder uriBuilder
    ) {
        String requestId = UUID.randomUUID().toString();
        LOGGER.info("[{}] Iniciando registro de nova solicitação de campanha interna.", requestId);

        LOGGER.debug("[{}] Dados do projeto recebido para registro: título = {}",
                requestId, registerInternalCampaigns.projectForm().title());

        Project project = projectService.register(registerInternalCampaigns.projectForm());
        LOGGER.info("[{}] Projeto registrado com sucesso. ID do projeto: {}",
                requestId, project.getId());

        Briefing briefing = briefingService.register(registerInternalCampaigns.briefingForm(), project);
        LOGGER.info("[{}] Briefing registrado com sucesso para o projeto. ID do briefing: {}",
                requestId, briefing.getId());

        internalCampaignsService.register(registerInternalCampaigns.internalCampaignsForm(), briefing);
        LOGGER.info("[{}] Solicitação de campanha interna registrada com sucesso para o briefing: {}",
                requestId, briefing.getId());

        dialogBoxService.createMessage(new DialogBoxForm(0L, briefing.getId(), "Sua solicitação foi criada com sucesso e está aguardando análise do supervisor. Assim que aprovada, daremos início ao desenvolvimento. Caso precise alterar algum dado informado ou realizar qualquer comunicação sobre o desenvolvimento, por favor, utilize este chat. Todas as atualizações também serão enviadas por aqui. Obrigado!"));

        URI location = uriBuilder.path("/api/v1/projects/{id}").buildAndExpand(project.getId()).toUri();
        LOGGER.info("[{}] Registro de solicitação de campanha interna concluído com sucesso.", requestId);
        return ResponseEntity.created(location).body(new Response<>("Nova solicitação criada com sucesso!"));
    }
}
