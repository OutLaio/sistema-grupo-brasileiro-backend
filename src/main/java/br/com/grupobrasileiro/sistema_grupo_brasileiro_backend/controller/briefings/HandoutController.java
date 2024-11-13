package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.briefings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.form.RegisterHandoutForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.BHandoutDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.BHandout;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.bHandout.BHandoutService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.BriefingService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.util.UriComponentsBuilder;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.RequestBody;

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
    
    @PostMapping
    @Transactional
    @Operation(summary = "Register a new handout", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Handout registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BHandout.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<BHandoutDetailedView> registerHandout(
            @Valid @RequestBody RegisterHandoutForm registerHandoutForm,
            UriComponentsBuilder uriBuilder
    ) {
        LOGGER.info("Iniciando registro de novo handout para o projeto: {}", registerHandoutForm.projectForm().title());

        Project project = projectService.register(registerHandoutForm.projectForm());
        LOGGER.info("Projeto registrado com sucesso: {}", project.getId());

        Briefing briefing = briefingService.register(registerHandoutForm.briefingForm(), project);
        LOGGER.info("Briefing registrado com sucesso para o projeto: {}", project.getId());

        bHandoutService.register(registerHandoutForm.handoutForm(), briefing);
        LOGGER.info("Handout registrado com sucesso: {}", handoutDetailedView.bHandoutView().id());

        return ResponseEntity.created(URI.create("/api/v1/projects/" + project.getId())).body(null);
    }
}
