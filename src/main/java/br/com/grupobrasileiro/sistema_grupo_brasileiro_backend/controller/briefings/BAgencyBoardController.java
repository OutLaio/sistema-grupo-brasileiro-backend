package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.briefings;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardRegisterView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form.RegisterAgencyBoard;
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

@RestController
@RequestMapping("/api/v1/agency-boards")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "AgencyBoard", description = "API for managing signposts")
public class BAgencyBoardController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private BriefingService briefingService;

    @Autowired
    private BAgencyBoardService bAgencyBoardService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BAgencyBoardController.class);

    @PostMapping
    @Transactional
    @Operation(summary = "Register a new agencyBoard", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "AgencyBoard registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BSignpost.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<BAgencyBoardRegisterView> registerSignpost(
            @Valid @RequestBody RegisterAgencyBoard registerAgencyBoard,
            UriComponentsBuilder uriBuilder
    ) {
        LOGGER.info("Starting registration for agency board");
        Project project = projectService.register(registerAgencyBoard.projectForm());
        LOGGER.info("Project registered successfully: projectId={}", project.getId());
        
        LOGGER.info("Registering briefing for projectId={}", project.getId());
        Briefing briefing = briefingService.register(registerAgencyBoard.briefingForm(), project);
        LOGGER.info("Briefing registered successfully: briefingId={}", briefing.getId());

        LOGGER.info("Registering agency board for briefingId={}", briefing.getId());
        BAgencyBoardRegisterView bAgencyBoardRegisterView = bAgencyBoardService.register(registerAgencyBoard.bAgencyBoardsForm(), briefing);
        LOGGER.info("Agency board registered successfully: agencyBoardId={}", bAgencyBoardRegisterView.bAgencyBoardView().id());

        URI uri = uriBuilder.path("/api/v1/agency-boards/{id}").buildAndExpand(bAgencyBoardRegisterView.bAgencyBoardView().id()).toUri();
        LOGGER.info("URI for new agency board: {}", uri);

        return ResponseEntity.created(uri).body(bAgencyBoardRegisterView);
    }
}
