package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.briefings;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.form.RegisterPrintedForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view.BPrintedsDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.BInternalCampaign;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.printed.PrintedService;
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
@RequestMapping("/api/v1/printed")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Printed", description = "API for managing printed")
public class PrintedController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private BriefingService briefingService;

    @Autowired
    private PrintedService printedService;

    @PostMapping
    @Transactional
    @Operation(summary = "Register a new printed", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Printed registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BInternalCampaign.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<BPrintedsDetailedView> registerPrinted(
            @Valid @RequestBody RegisterPrintedForm registerPrintedForm,
            UriComponentsBuilder uriBuilder
    ) {
        Project project = projectService.register(registerPrintedForm.projectForm());
        Briefing briefing = briefingService.register(registerPrintedForm.briefingForm(),project);
        printedService.register(registerPrintedForm.printedForm(), briefing);
        return ResponseEntity.created(URI.create("/api/v1/projects/" + project.getId())).body(null);
    }
    
}
