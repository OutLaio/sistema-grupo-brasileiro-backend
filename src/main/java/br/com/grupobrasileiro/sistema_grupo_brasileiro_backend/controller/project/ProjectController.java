package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ApproveForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.AssignCollaboratorForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.NewVersionForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.ProjectService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.VersionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controlador para gerenciamento de projetos e versões.
 */
@RestController
@RequestMapping("/api/v1/projects")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Projects", description = "Project and Version Management")
public class ProjectController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;
    @Autowired
    private VersionService versionService;

    /**
     * Atribui um colaborador a um projeto.
     *
     * @param id   o ID do projeto
     * @param form os dados do colaborador a ser atribuído
     * @return 200 OK se a operação for bem-sucedida
     */
    @Operation(summary = "Assign collaborator", description = "Assigns a collaborator to a project.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Collaborator assigned successfully", content = @Content),
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
        @ApiResponse(responseCode = "404", description = "Project not found", content = @Content)
    })
    @PutMapping("{id}/assignCollaborator")
    @Transactional
    public ResponseEntity<?> assignCollaborator(
            @Parameter(description = "ID do projeto") @PathVariable Long id,
            @Valid @RequestBody AssignCollaboratorForm form) {
    	logger.info("Atribuindo colaborador ao projeto com ID: {}", id);
        projectService.assignCollaborator(id, form);
        
        logger.debug("Colaborador atribuído com sucesso ao projeto de ID: {}", id);
        
        return ResponseEntity.ok().build();
    }

    /**
     * Cria uma nova versão do projeto.
     *
     * @param id   o ID do projeto
     * @param form os dados da nova versão
     * @return 200 OK se a nova versão for criada com sucesso
     */
    @Operation(summary = "Create new version", description = "Creates a new version for the project.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "New version created successfully", content = @Content),
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
        @ApiResponse(responseCode = "404", description = "Project not found", content = @Content)
    })
    @PutMapping("/{id}/newVersion")
    @Transactional
    public ResponseEntity<?> newVersion(
            @Parameter(description = "ID do projeto") @PathVariable Long id,
            @Valid @RequestBody NewVersionForm form) {
    	logger.info("Criando nova versão para o projeto com ID: {}", id);
        versionService.create(id, form);
        logger.debug("Nova versão criada para o projeto de ID: {}", id);
        
        return ResponseEntity.ok().build();
    }

    /**
     * Aprova a versão do projeto pelo supervisor.
     *
     * @param form os dados da aprovação
     * @return 200 OK se a aprovação for bem-sucedida
     */
    @Operation(summary = "Supervisor Approval", description = "Approves a project version by the supervisor.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Approval successful", content = @Content),
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content)
    })
    @PutMapping("/approve/supervisor")
    @Transactional
    public ResponseEntity<?> supervisorApprove(
            @Valid @RequestBody ApproveForm form) {
    	logger.info("Supervisor aprovando versão do projeto");
        versionService.supervisorApprove(form);
        logger.debug("Versão do projeto aprovada pelo supervisor");
        
        return ResponseEntity.ok().build();
    }

    /**
     * Aprova a versão do projeto pelo cliente.
     *
     * @param form os dados da aprovação
     * @return 200 OK se a aprovação for bem-sucedida
     */
    @Operation(summary = "Client Approval", description = "Client approves a version of a project.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Approval successful", content = @Content),
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content)
    })
    @PutMapping("/approve/client")
    @Transactional
    public ResponseEntity<?> clientApprove(
            @Valid @RequestBody ApproveForm form) {
    	logger.info("Cliente aprovando versão do projeto");
        versionService.clientApprove(form);
        logger.debug("Versão do projeto aprovada pelo cliente");
        
        return ResponseEntity.ok().build();
    }

    /**
     * Define se o projeto está em produção.
     *
     * @param id o ID do projeto
     * @param hasConfection indica se o projeto está em produção
     * @return 200 OK se a operação for bem-sucedida
     */
    @Operation(summary = "Set project production", description = "Sets whether or not the project is in production.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Production status updated successfully", content = @Content),
        @ApiResponse(responseCode = "404", description = "Project not found", content = @Content)
    })
    @PutMapping("/{id}/hasProduction")
    @Transactional
    public ResponseEntity<?> hasProduction(
            @Parameter(description = "ID do projeto") @PathVariable Long id,
            @Parameter(description = "Indica se o projeto está em produção") @RequestParam Boolean hasConfection) {
    	logger.info("Atualizando status de produção para o projeto com ID: {}", id);
        projectService.setHasConfection(id, hasConfection);
        logger.debug("Status de produção atualizado para o projeto de ID: {}", id);
        
        return ResponseEntity.ok().build();
    }

    /**
     * Finaliza o projeto.
     *
     * @param id o ID do projeto
     * @return 200 OK se o projeto for finalizado com sucesso
     */
    @Operation(summary = "Finalize project", description = "Finalize the project.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Project completed successfully", content = @Content),
        @ApiResponse(responseCode = "404", description = "Project not found", content = @Content)
    })
    @PutMapping("/{id}/finish")
    @Transactional
    public ResponseEntity<?> finish(
            @Parameter(description = "ID do projeto") @PathVariable Long id) {
    	logger.info("Finalizando projeto com ID: {}", id);
        projectService.setFinished(id);
        logger.debug("Projeto finalizado com sucesso, ID: {}", id);
        
        return ResponseEntity.ok().build();
    }

    /**
     * Coloca o projeto em espera.
     *
     * @param id o ID do projeto
     * @return 200 OK se o projeto for colocado em espera com sucesso
     */
    @Operation(summary = "Put project on hold", description = "Puts the project on hold.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Project successfully put on hold", content = @Content),
        @ApiResponse(responseCode = "404", description = "Project not found", content = @Content)
    })
    @PutMapping("/{id}/standby")
    @Transactional
    public ResponseEntity<?> standby(
            @Parameter(description = "ID do projeto") @PathVariable Long id) {
    	logger.info("Colocando projeto em espera com ID: {}", id);
        projectService.setStandby(id);
        logger.debug("Projeto colocado em espera com sucesso, ID: {}", id);
        
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Transactional
    public ResponseEntity<?> getAll(Authentication authentication){
    	User user = (User) authentication.getPrincipal();
        logger.info("Recuperando todos os projetos para o usuário com ID: {}", user.getId());
        return ResponseEntity.ok(projectService.getAll(user.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
    	logger.info("Buscando projeto com ID: {}", id);
    	ResponseEntity<?> response = projectService.getById(id);
        
        if (response.getStatusCode().is2xxSuccessful()) {
            logger.debug("Projeto encontrado: ID {}", id);
        } else {
            logger.warn("Projeto com ID {} não foi encontrado", id);
        }
        
        return response;
    }
}
