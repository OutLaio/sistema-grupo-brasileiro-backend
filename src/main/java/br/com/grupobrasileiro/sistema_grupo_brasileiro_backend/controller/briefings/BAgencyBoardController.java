package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.briefings;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form.RegisterAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.MessageSuccessView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.agencyBoard.BAgencyBoardService;
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
@RequestMapping("/api/v1/agency-boards")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "AgencyBoard", description = "API for managing signposts")
public class BAgencyBoardController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BAgencyBoardController.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private BriefingService briefingService;

    @Autowired
    private BAgencyBoardService bAgencyBoardService;

    /**
     * Registers a new AgencyBoard for a projectForm.
     * 
     * @param registerAgencyBoard the data to register a new agency board
     * @param uriBuilder builder for creating the location URI
     * @return a response entity containing the details of the registered agency board
     */
    @Operation(
    	    summary = "Register a new agencyBoard", 
    	    description = "Registers a new agencyBoard for a projectForm, creating the associated briefing and projectForm before registering the agency board."
    	)
    	@ApiResponses(value = {
    	    @ApiResponse(
    	        responseCode = "201",
    	        description = "AgencyBoard registered successfully",
    	        content = @Content(
    	            mediaType = "application/json",
    	            schema = @Schema(implementation = BAgencyBoardDetailedView.class)
    	        )
    	    ),
    	    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
    	    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
    	    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    	})
    @PostMapping
    @Transactional
    public ResponseEntity<?> registerSignpost(
            @Valid @RequestBody RegisterAgencyBoard registerAgencyBoard,
            UriComponentsBuilder uriBuilder
    ) {
        String requestId = UUID.randomUUID().toString();
        LOGGER.info("[{}] Iniciando registro de nova solicitação de placa de intinerários.", requestId);

        LOGGER.debug("[{}] Dados do projeto recebido para registro: título = {}",
                requestId, registerAgencyBoard.projectForm().title());

        Project project = projectService.register(registerAgencyBoard.projectForm());
        LOGGER.info("[{}] Projeto registrado com sucesso. ID do projeto: {}",
                requestId, project.getId());

        Briefing briefing = briefingService.register(registerAgencyBoard.briefingForm(), project);
        LOGGER.info("[{}] Briefing registrado com sucesso para o projeto. ID do briefing: {}",
                requestId, briefing.getId());
        
        bAgencyBoardService.register(registerAgencyBoard.bAgencyBoardsForm(), briefing);
        LOGGER.info("[{}] Solicitação de placa de intinerários registrada com sucesso para o briefing: {}",
                requestId, briefing.getId());

        URI location = uriBuilder.path("/api/v1/projects/{id}").buildAndExpand(project.getId()).toUri();
        LOGGER.info("[{}] Registro de solicitação de placa de intinerários concluído com sucesso.", requestId);
        return ResponseEntity.created(location).body(new MessageSuccessView("Nova solicitação criada com sucesso!"));
    }
}
