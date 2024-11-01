package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.briefings;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.form.RegisterSignpostForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.signpost.BSignpostService;
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

@RestController
@RequestMapping("/api/v1/signposts")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Signpost", description = "API for managing signposts")
public class SignpostController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignpostController.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private BriefingService briefingService;

    @Autowired
    private BSignpostService signpostService;

    @PostMapping
    @Transactional
    @Operation(summary = "Register a new signpost", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Signpost registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BSignpost.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<BSignpostDetailedView> registerSignpost(
            @Valid @RequestBody RegisterSignpostForm registerSignpostForm,
            UriComponentsBuilder uriBuilder
    ) {
        LOGGER.info("Iniciando o registro de um novo signpost para o projeto: {}", registerSignpostForm.projectForm().title());

        Project project = projectService.register(registerSignpostForm.projectForm());
        LOGGER.info("Projeto registrado com sucesso: {}", project.getId());

        Briefing briefing = briefingService.register(registerSignpostForm.briefingForm(), project);
        LOGGER.info("Briefing registrado com sucesso para o projeto: {}", project.getId());

        BSignpostDetailedView signpostRegisterView = signpostService.register(registerSignpostForm.signpostForm(), briefing);
        LOGGER.info("Signpost registrado com sucesso: {}", signpostRegisterView.bSignpostView().id());

        URI uri = uriBuilder.path("/api/v1/signposts/{id}").buildAndExpand(signpostRegisterView.bSignpostView().id()).toUri();
        return ResponseEntity.created(uri).body(signpostRegisterView);
    }
}
