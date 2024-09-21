package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.briefings;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.form.RegisterSignpostForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.SignpostRegisterView;
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
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/signposts")
@SecurityRequirement(name = "bearer-key")
@RequiredArgsConstructor
@Tag(name = "Signpost", description = "API for managing signposts")
public class SignpostController {

    private final ProjectService projectService;
    private final BriefingService briefingService;
    private final BSignpostService signpostService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SignpostController.class);

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
    public ResponseEntity<SignpostRegisterView> registerSignpost(
            @Valid @RequestBody RegisterSignpostForm registerSignpost,
            UriComponentsBuilder uriBuilder
    ) {
        LOGGER.info("Starting registration for signpost");
        Project project = projectService.register(registerSignpost.projectForm());
        LOGGER.info("Project registered successfully: projectId={}", project.getId());

        LOGGER.info("Registering briefing for projectId={}", project.getId());
        Briefing briefing = briefingService.register(registerSignpost.briefingForm(), project);
        LOGGER.info("Briefing registered successfully: briefingId={}", briefing.getId());

        LOGGER.info("Registering signpost for briefingId={}", briefing.getId());
        SignpostRegisterView signpostRegisterView = signpostService.register(registerSignpost.signpostForm(), briefing);
        LOGGER.info("Signpost registered successfully: signpostId={}", signpostRegisterView.bSignpostView().id());

        URI uri = uriBuilder.path("/api/v1/signposts/{id}").buildAndExpand(signpostRegisterView.bSignpostView().id()).toUri();
        LOGGER.info("URI for new signpost: {}", uri);

        return ResponseEntity.created(uri).body(signpostRegisterView);
    }
}
