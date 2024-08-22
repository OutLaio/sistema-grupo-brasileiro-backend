package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.CollaboratorAssignmentForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.CompanyForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.CompanyView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.UnauthorizedException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.CompanyService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.ProjectService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private CompanyService companyService;
    
    @Cacheable("all")
    @GetMapping("/all")
    public ResponseEntity<Page<ProjectView>> projectAll(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(value = "direction", defaultValue = "ASC" ) String direction,
        @RequestParam(value = "orderBy", defaultValue = "id" ) String orderBy,
        @RequestParam(defaultValue = "10") int size) {
        
        PageRequest pageRequest  = PageRequest.of(page, size, Direction.valueOf(direction),  orderBy);
        Page<ProjectView> projectViews = projectService.projectAll(pageRequest);
        return ResponseEntity.ok(projectViews);
    }
	
    @Cacheable("projects-collaborators")
    @GetMapping("/collaborators")
    public ResponseEntity<Page<ProjectView>> projectsByRole(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(value = "direction", defaultValue = "ASC" ) String direction,
        @RequestParam(value = "orderBy", defaultValue = "project_id" ) String orderBy,
        @RequestParam(defaultValue = "10") int size
    ) {
    	Integer role = RoleEnum.ROLE_COLLABORATOR.getCode();
        PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
        Page<ProjectView> projectViews = projectService.projectsCollaborators(pageRequest, role);
        return ResponseEntity.ok(projectViews);
    }

    
    
    @Cacheable("getId")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPERVISOR') OR (hasRole('COLLABORATOR') AND #id == authentication.principal.id)")
    public ResponseEntity<ProjectView> getProjectById(@PathVariable Long id) {
        try {
            ProjectView projectView = projectService.getProjectById(id);
            return ResponseEntity.ok(projectView);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('SUPERVISOR') OR (hasRole('COLLABORATOR') AND #id == authentication.principal.id)")
    public ResponseEntity<ProjectView> updateProjectStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            ProjectView updatedProjectView = projectService.updateProjectStatus(id, status);
            return ResponseEntity.ok(updatedProjectView);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    
    @PostMapping("/new")
    public ResponseEntity<ProjectView> save(@Valid @RequestBody ProjectForm body, @AuthenticationPrincipal UserDetails userDetails) {
    	LOGGER.info("Starting create-project request for: title={}", body.toString());
    	
    	ProjectView projectView = projectService.save(body, userDetails);
    	return ResponseEntity.status(HttpStatus.CREATED).body(projectView);
    	
    }
    
    @PostMapping("/{projectId}/assign-collaborator")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public ResponseEntity<String> assignCollaboratorToProject(
            @PathVariable Long projectId,
            @RequestBody CollaboratorAssignmentForm collaborator) {

        try {
            projectService.assignCollaboratorToProject(projectId, collaborator.collaboratorId());
            return ResponseEntity.ok("Colaborador atribuído ao projeto com sucesso.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autorizado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor.");
        }
    }
    
    @PostMapping("/new-company")
    public ResponseEntity<CompanyView> save(@Valid @RequestBody CompanyForm body) {
    	LOGGER.info("Starting create-project request for: title={}", body.toString());
    	
    	CompanyView companyView = companyService.save(body);
    	return ResponseEntity.status(HttpStatus.CREATED).body(companyView);
    	
    }
    
    @Cacheable("all")
    @GetMapping("/all-company")
    public ResponseEntity<Page<CompanyView>> companyAll(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(value = "direction", defaultValue = "ASC" ) String direction,
        @RequestParam(value = "orderBy", defaultValue = "name" ) String orderBy,
        @RequestParam(defaultValue = "10") int size) {
        
        PageRequest pageRequest  = PageRequest.of(page, size, Direction.valueOf(direction),  orderBy);
        Page<CompanyView> companyViews = companyService.companyAll(pageRequest);
        return ResponseEntity.ok(companyViews);
    }
    
}
