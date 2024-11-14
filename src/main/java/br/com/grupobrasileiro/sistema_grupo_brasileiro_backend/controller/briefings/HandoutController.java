package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.briefings;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.form.RegisterHandoutForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.BHandoutDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.BHandout;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.bHandout.BHandoutService;
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

/**
 * Controller for managing Handouts within the system.
 */
@RestController
@RequestMapping("/api/v1/handouts")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Handout", description = "API for managing handouts")
public class HandoutController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HandoutController.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private BriefingService briefingService;
    
    @Autowired
    private BHandoutService bHandoutService;
    
    
    /**
     * Registers a new handout for a project.
     *
     * @param registerHandout the data to register a handout
     * @param uriBuilder builder for creating the location URI
     * @return a response entity containing the detailed view of the registered handout
     */
    @PostMapping
    @Transactional
    @Operation(
            summary = "Register a new handout",
            description = "Registers a new handout for a project. This operation creates a new handout, " +
                          "registers the associated project and briefing, and returns a detailed view of the handout."
        )
        @ApiResponses(value = {
            @ApiResponse(
                responseCode = "201", 
                description = "Handout registered successfully", 
                content = @Content(
                    mediaType = "application/json", 
                    schema = @Schema(implementation = BHandoutDetailedView.class)
                )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
        })
    public ResponseEntity<BHandoutDetailedView> registerHandout(
            @Valid @RequestBody RegisterHandoutForm registerHandout,
            UriComponentsBuilder uriBuilder
    ) {
        LOGGER.info("Iniciando registro de novo handout para o projeto: {}", registerHandout.projectForm().title());

        Project project = projectService.register(registerHandout.projectForm());
        LOGGER.info("Projeto registrado com sucesso: {}", project.getId());

        Briefing briefing = briefingService.register(registerHandout.briefingForm(), project);
        LOGGER.info("Briefing registrado com sucesso para o projeto: {}", project.getId());

        bHandoutService.register(registerHandout.handoutForm(), briefing);
        LOGGER.info("Handout registrado com sucesso!");

        return ResponseEntity.created(URI.create("/api/v1/projects/" + project.getId())).body(null);
    }
}
