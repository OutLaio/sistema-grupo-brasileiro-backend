package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.briefings;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.form.RegisterStickerForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker.BSticker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.sticker.BStickerService;
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

import java.net.URI;

@RestController
@RequestMapping("/api/v1/stickers")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Stickers", description = "API for managing stickers")
public class StickerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StickerController.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private BriefingService briefingService;

    @Autowired
    private BStickerService stickerService;

    @PostMapping
    @Transactional
    @Operation(summary = "Register a new sticker", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sticker registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BSticker.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<?> registerSticker(
            @Valid @RequestBody RegisterStickerForm registerSticker
    ) {
        LOGGER.info("Iniciando o registro de um novo sticker para o projeto: {}", registerSticker.project().title());

        Project project = projectService.register(registerSticker.project());
        LOGGER.info("Projeto registrado com sucesso: {}", project.getId());

        Briefing briefing = briefingService.register(registerSticker.briefing(), project);
        LOGGER.info("Briefing registrado com sucesso para o projeto: {}", project.getId());

        stickerService.register(registerSticker.sticker(), briefing);
        LOGGER.info("Sticker registrado com sucesso!");

        return ResponseEntity.created(URI.create("/api/v1/projects/" + project.getId())).body(null);
    }
}
