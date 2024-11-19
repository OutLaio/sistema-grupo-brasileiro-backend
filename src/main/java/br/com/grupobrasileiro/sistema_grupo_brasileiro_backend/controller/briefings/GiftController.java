package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.briefings;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.form.RegisterGiftForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.BGiftDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.MessageSuccessView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.BGift;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.gift.BGiftService;
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

/**
 * Controller for managing Gifts within the system.
 */
@RestController
@RequestMapping("/api/v1/gifts")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Gift", description = "API for managing gifts")
public class GiftController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GiftController.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private BriefingService briefingService;

    @Autowired
    private BGiftService giftService;

    /**
     * Registers a new gift for a projectForm.
     * 
     * @param registerGift the data to register a gift
     * @param uriBuilder builder for creating the location URI
     * @return a response entity containing the detailed view of the registered gift
     */
    @PostMapping
    @Transactional
    @Operation(
        summary = "Register a new gift",
        description = "Registers a new gift for a projectForm. This operation creates a new gift, " +
                      "registers the associated projectForm and briefing, and returns a detailed view of the gift."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Gift registered successfully", 
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = BGiftDetailedView.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<?> registerGift(
            @Valid @RequestBody RegisterGiftForm registerGift,
            UriComponentsBuilder uriBuilder
    ) {
        String requestId = UUID.randomUUID().toString();
        LOGGER.info("[{}] Iniciando registro de nova solicitação de layout p/ brinde.", requestId);

        LOGGER.debug("[{}] Dados do projeto recebido para registro: título = {}",
                requestId, registerGift.projectForm().title());

        Project project = projectService.register(registerGift.projectForm());
        LOGGER.info("[{}] Projeto registrado com sucesso. ID do projeto: {}",
                requestId, project.getId());

        Briefing briefing = briefingService.register(registerGift.briefingForm(), project);
        LOGGER.info("[{}] Briefing registrado com sucesso para o projeto. ID do briefing: {}",
                requestId, briefing.getId());

        giftService.register(registerGift.giftForm(), briefing);
        LOGGER.info("[{}] Solicitação de layout p/ brinde registrado com sucesso para o briefing: {}",
                requestId, briefing.getId());

        URI location = uriBuilder.path("/api/v1/projects/{id}").buildAndExpand(project.getId()).toUri();
        LOGGER.info("[{}] Registro de solicitação de layout p/ brinde concluído com sucesso.", requestId);
        return ResponseEntity.created(location).body(new MessageSuccessView("Nova solicitação criada com sucesso!"));
    }
}
