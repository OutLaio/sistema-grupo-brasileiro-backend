//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.joao;
//
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.agencyBoard.RegisterAgencyBoard;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.signpost.RegisterSignpost;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.BAgencyBoardRepository;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.ProjectService;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.joao.briefings.agencyBoard.BAgencyBoardService;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.joao.project.BriefingService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.transaction.Transactional;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.net.URI;
//
//@RestController
//@RequestMapping("/api/v1/agency-boards")
//@SecurityRequirement(name = "bearer-key")
//@Tag(name = "AgencyBoard", description = "API for managing signposts")
//public class BAgencyBoardController {
//
//
//    @Autowired
//    private ProjectService projectService;
//
//    @Autowired
//    private BriefingService briefingService;
//
//    @Autowired
//    private BAgencyBoardService bAgencyBoardService;
//
//    @PostMapping
//    @Transactional
//    @Operation(summary = "Register a new agencyBoard", method = "POST")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "AgencyBoard registered successfully",
//                    content = @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = BSignpost.class))),
//            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
//            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
//            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
//    })
//    public ResponseEntity<BAgencyBoard> registerSignpost(
//            @Valid @RequestBody RegisterAgencyBoard registerAgencyBoard,
//            UriComponentsBuilder uriBuilder
//    ) {
//        Project project = projectService.register(registerAgencyBoard.projectForm());
//        Briefing briefing = briefingService.register(registerAgencyBoard.briefingForm(),project);
//        BAgencyBoard bAgencyBoard = bAgencyBoardService.register(registerAgencyBoard.bAgencyBoardsForm(),briefing);
//        URI uri = uriBuilder.path("/api/v1/agency-boards/{id}").buildAndExpand(bAgencyBoard.getId()).toUri();
//        return ResponseEntity.created(uri).body(bAgencyBoard);
//    }
//
//}
