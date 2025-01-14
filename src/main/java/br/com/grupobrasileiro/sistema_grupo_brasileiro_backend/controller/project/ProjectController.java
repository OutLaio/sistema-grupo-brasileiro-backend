package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.project;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.Response;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.*;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.VersionView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.ProjectService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.VersionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;
import java.util.UUID;

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
        @ApiResponse(responseCode = "200", description = "Collaborator assigned successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))),
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
        @ApiResponse(responseCode = "404", description = "Project not found", content = @Content)
    })
    @PutMapping("{id}/assignCollaborator")
    @Transactional
    public ResponseEntity<?> assignCollaborator(
            @Parameter(description = "ID do projeto") @PathVariable Long id,
            @Valid @RequestBody AssignCollaboratorForm form) {
        String requestId = UUID.randomUUID().toString();
        logger.info("[{}] Iniciando processo de atribuição de colaborador ao projeto com ID: {}", requestId, id);
        logger.debug("[{}] Dados recebidos: colaborador ID = {}", requestId, form.idCollaborator());

        projectService.assignCollaborator(id, form);
        logger.info("[{}] Colaborador atribuído com sucesso ao projeto com ID: {}", requestId, id);

        return ResponseEntity.ok().body(new Response<>("Colaborador atribuído com sucesso!"));
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
        @ApiResponse(responseCode = "200", description = "New version created successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))),
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
        @ApiResponse(responseCode = "404", description = "Project not found", content = @Content)
    })
    @PutMapping("/{id}/newVersion")
    @Transactional
    public ResponseEntity<?> newVersion(
            @Parameter(description = "ID do projeto") @PathVariable Long id,
            @Valid @RequestBody NewVersionForm form) {
        String requestId = UUID.randomUUID().toString();
        logger.info("[{}] Iniciando criação de nova versão de arte para o projeto com ID: {}", requestId, id);

        logger.debug("[{}] Dados recebidos para a nova versão: link do produto = {}",
                requestId, form.productLink());

        VersionView view = versionService.create(id, form);
        logger.info("[{}] Nova versão criada com sucesso para o projeto com ID: {}. ID da versão: {}",
                requestId, id, view.id());

        return ResponseEntity.created(URI.create("/api/v1/projects/" + id)).body(new Response<>("Nova Arte Enviada com Sucesso!", view));
    }

    /**
     * Aprova a versão do projeto pelo supervisor.
     *
     * @param id id do projeto
     * @param form os dados da aprovação
     * @return 200 OK se a aprovação for bem-sucedida
     */
    @Operation(summary = "Supervisor Approval", description = "Approves a project version by the supervisor.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Approval successful",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))),
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content)
    })
    @PutMapping("{id}/approve/supervisor")
    @Transactional
    public ResponseEntity<?> supervisorApprove(
            @PathVariable Long id,
            @Valid @RequestBody ApproveForm form) {
        String requestId = UUID.randomUUID().toString();
        logger.info("[{}] Iniciando aprovação de versão pelo supervisor.", requestId);

        logger.debug("[{}] Dados recebidos para aprovação do supervisor: versão ID = {}, status = {}",
                requestId, form.idVersion(), form.approved());

        VersionView view = versionService.supervisorApprove(id, form);
        logger.debug("Versão do projeto " + (form.approved() ? "aprovada" : "não aprovada") + " pelo supervisor");
        
        return ResponseEntity.ok().body(new Response<>("Status de aprovação alterado com sucesso!", view));
    }

    /**
     * Aprova a versão do projeto pelo cliente.
     *
     * @param form os dados da aprovação
     * @return 200 OK se a aprovação for bem-sucedida
     */
    @Operation(summary = "Client Approval", description = "Client approves a version of a project.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Approval successful",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))),
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content)
    })
    @PutMapping("{id}/approve/client")
    @Transactional
    public ResponseEntity<?> clientApprove(
            @PathVariable Long id,
            @Valid @RequestBody ApproveForm form) {
        String requestId = UUID.randomUUID().toString();
        logger.info("[{}] Iniciando aprovação de versão pelo cliente.", requestId);

        logger.debug("[{}] Dados recebidos para aprovação do clinte: versão ID = {}, status = {}",
                requestId, form.idVersion(), form.approved());

        VersionView view = versionService.clientApprove(id, form);
        logger.debug("Versão do projeto " + (form.approved() ? "aprovada" : "não aprovada") + " pelo cliente");

        return ResponseEntity.ok().body(new Response<>("Status de aprovação alterado com sucesso!", view));
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
        @ApiResponse(responseCode = "200", description = "Production status updated successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))),
        @ApiResponse(responseCode = "404", description = "Project not found", content = @Content)
    })
    @PutMapping("/{id}/hasProduction")
    @Transactional
    public ResponseEntity<?> hasProduction(
            @Parameter(description = "ID do projeto") @PathVariable Long id,
            @Parameter(description = "Indica se o projeto está em produção") @RequestParam Boolean hasConfection) {
        String requestId = UUID.randomUUID().toString();
        logger.info("[{}] Iniciando atualização de status de produção para o projeto com ID: {}.", requestId, id);

        logger.debug("[{}] Dados recebidos: ID do projeto = {}, status de produção = {}.",
                requestId, id, hasConfection);

        projectService.setHasConfection(id, hasConfection);
        logger.info("[{}] Status de produção atualizado com sucesso para o projeto com ID: {}.",
                requestId, id);

        return ResponseEntity.ok().body(new Response<>("Status Alterado com sucesso"));
    }

    /**
     * Finaliza o projeto.
     *
     * @param id o ID do projeto
     * @return 200 OK se o projeto for finalizado com sucesso
     */
    @Operation(summary = "Finalize project", description = "Finalize the project.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Project completed successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))),
        @ApiResponse(responseCode = "404", description = "Project not found", content = @Content)
    })
    @PutMapping("/{id}/finish")
    @Transactional
    public ResponseEntity<?> finish(
            @Parameter(description = "ID do projeto") @PathVariable Long id) {
        String requestId = UUID.randomUUID().toString();
        logger.info("[{}] Iniciando finalização do projeto com ID: {}.", requestId, id);

        logger.debug("[{}] Verificando e processando finalização do projeto. ID: {}.", requestId, id);

        projectService.setFinished(id);
        logger.info("[{}] Projeto com ID: {} finalizado com sucesso.", requestId, id);

        return ResponseEntity.ok().body(new Response<>("Projeto finalizado com sucesso!"));
    }

    /**
     * Coloca o projeto em espera.
     *
     * @param id o ID do projeto
     * @return 200 OK se o projeto for colocado em espera com sucesso
     */
    @Operation(summary = "Put project on hold", description = "Puts the project on hold.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Project successfully put on hold",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))),
        @ApiResponse(responseCode = "404", description = "Project not found", content = @Content)
    })
    @PutMapping("/{id}/standby")
    @Transactional
    public ResponseEntity<?> standby(
            @Parameter(description = "ID do projeto") @PathVariable Long id) {
        String requestId = UUID.randomUUID().toString();
        logger.info("[{}] Iniciando operação para colocar o projeto em espera. ID do projeto: {}", requestId, id);

        projectService.setStandby(id);
        logger.info("[{}] Projeto colocado em espera com sucesso. ID do projeto: {}", requestId, id);
        
        return ResponseEntity.ok().body(new Response<>("Projeto colocado em espera com sucesso!"));
    }

    /**
     * Recupera todos os projetos do usuário autenticado.
     *
     * @param authentication as credenciais do usuário autenticado
     * @return 200 OK se os projetos forem recuperados com sucesso
     */
    @Operation(summary = "Get all projects", description = "Retrieves all projects for the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects successfully retrieved",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content)
    })
    @GetMapping
    @Transactional
    public ResponseEntity<?> getAll(
            @Parameter(hidden = true) Authentication authentication) {
        String requestId = UUID.randomUUID().toString();
        User user = (User) authentication.getPrincipal();

        logger.info("[{}] Iniciando recuperação de todos os projetos para o usuário. ID do usuário: {}", requestId, user.getId());
        Set<ProjectView> projects = projectService.getAll(user.getId());

        logger.info("[{}] Recuperação de projetos concluída com sucesso. Total de projetos encontrados: {}", requestId, projects.size());
        return ResponseEntity.ok(new Response<>("Recuperação de projetos concluída com sucesso!", projects));
    }

    /**
     * Busca um projeto pelo ID.
     *
     * @param id o ID do projeto
     * @return 200 OK se o projeto for encontrado, ou 404 se não for encontrado
     */
    @Operation(summary = "Get project by ID", description = "Fetches the details of a project by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project found",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Project not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @Parameter(description = "ID do projeto") @PathVariable Long id) {
        String requestId = UUID.randomUUID().toString();
        logger.info("[{}] Iniciando busca pelo projeto. ID do projeto: {}", requestId, id);

        ResponseEntity<?> response = projectService.getById(id);
        logger.info("[{}] Projeto encontrado com sucesso. ID do projeto: {}", requestId, id);

        return response;
    }

    /**
     * Atualiza o título de um projeto.
     *
     * @param id o ID do projeto
     * @param form os dados para alteração do título
     * @return 200 OK se o título for atualizado com sucesso
     */
    @Operation(summary = "Update project title", description = "Updates the title of a project.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Title successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
            @ApiResponse(responseCode = "404", description = "Project not found", content = @Content)
    })
    @PutMapping("/{id}/alterTitle")
    public ResponseEntity<?> updateTitle(
            @Parameter(description = "ID do projeto") @PathVariable Long id,
            @RequestBody @Valid AlterTitleForm form) {
        String requestId = UUID.randomUUID().toString();
        logger.info("[{}] Iniciando atualização do título para o projeto. ID do projeto: {}", requestId, id);
        logger.debug("[{}] Novo título recebido: {}", requestId, form.newTitle());

        projectService.updateTitle(id, form.newTitle());

        logger.info("[{}] Título do projeto atualizado com sucesso. ID do projeto: {}", requestId, id);
        return ResponseEntity.ok().body(new Response<>("Título do projeto atualizado com sucesso!"));
    }

    /**
     * Atualiza a data de um projeto.
     *
     * @param id o ID do projeto
     * @param form os dados para alteração da data
     * @return 200 OK se a data for atualizada com sucesso
     */
    @Operation(summary = "Update project date", description = "Updates the date of a project.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Date successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
            @ApiResponse(responseCode = "404", description = "Project not found", content = @Content)
    })
    @PutMapping("/{id}/alterDate")
    public ResponseEntity<?> updateDate(
            @Parameter(description = "ID do projeto") @PathVariable Long id,
            @RequestBody @Valid AlterDateForm form) {
        String requestId = UUID.randomUUID().toString();
        logger.info("[{}] Iniciando atualização da data para o projeto. ID do projeto: {}", requestId, id);
        logger.debug("[{}] Nova data recebida: {}", requestId, form.newDate());

        projectService.updateDate(id, form.newDate());

        logger.info("[{}] Data do projeto atualizada com sucesso. ID do projeto: {}", requestId, id);
        return ResponseEntity.ok().body(new Response<>("Data do projeto atualizada com sucesso!"));

    }

    /**
     * Atualiza o status de um projeto.
     *
     * @param id o ID do projeto
     * @param form os dados para alteração do status
     * @return 200 OK se o status for atualizado com sucesso
     */
    @Operation(summary = "Update project status", description = "Updates the status of a project.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
            @ApiResponse(responseCode = "404", description = "Project not found", content = @Content)
    })
    @PutMapping("/{id}/alterStatus")
    public ResponseEntity<?> updateStatus(
            @Parameter(description = "ID do projeto") @PathVariable Long id,
            @RequestBody @Valid AlterStatusForm form) {
        String requestId = UUID.randomUUID().toString();
        logger.info("[{}] Iniciando atualização do status para o projeto. ID do projeto: {}", requestId, id);
        logger.debug("[{}] Novo status recebido: {}", requestId, form.newStatus());

        projectService.updateStatus(id, form.newStatus());

        logger.info("[{}] Status do projeto atualizado com sucesso. ID do projeto: {}", requestId, id);
        return ResponseEntity.ok().body(new Response<>("Status do projeto atualizado com sucesso!"));

    }
}
