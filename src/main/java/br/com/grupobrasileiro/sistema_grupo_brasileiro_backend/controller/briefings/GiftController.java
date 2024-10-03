package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.briefings;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.form.RegisterGiftForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.BGiftDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.BGift;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.gift.BGiftService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.BriefingService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/bgifts")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Gift", description = "API for managing gifts")
public class GiftController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private BriefingService briefingService;

    @Autowired
    private BGiftService giftService;

    @PostMapping
    @Transactional
    @Operation(summary = "Register a new gift", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Gift registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BGift.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<BGiftDetailedView> registerGift(
            @Valid @RequestBody RegisterGiftForm registerGiftForm,
            UriComponentsBuilder uriBuilder
    ) {
        Project project = projectService.register(registerGiftForm.projectForm());
        Briefing briefing = briefingService.register(registerGiftForm.briefingForm(), project);
        BGiftDetailedView giftDetailedView = giftService.register(registerGiftForm.giftForm(), briefing);
        URI uri = uriBuilder.path("/api/v1/bgifts/{id}").buildAndExpand(giftDetailedView.bGiftView().id()).toUri();
        return ResponseEntity.created(uri).body(giftDetailedView);
    }
}