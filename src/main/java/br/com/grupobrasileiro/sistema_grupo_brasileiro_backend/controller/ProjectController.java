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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.CollaboratorAssignmentForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.ProjectService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/projects")
public class ProjectController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;
    
    @Cacheable("all")
    @GetMapping("/all")
    public ResponseEntity<Page<ProjectView>> projectAll(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(value = "direction", defaultValue = "ASC" ) String direction,
        @RequestParam(value = "orderBy", defaultValue = "title" ) String orderBy,
        @RequestParam(defaultValue = "10") int size) {
        
        PageRequest pageRequest  = PageRequest.of(page, size, Direction.valueOf(direction),  orderBy);
        Page<ProjectView> projectViews = projectService.projectAll(pageRequest);
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
    
    
    @PostMapping("/create-project")
    public ResponseEntity<ProjectView> save(@Valid @RequestBody ProjectForm body) {
    	LOGGER.info("Starting create-project request for: title={}", body.title());
    	
    	projectService.save(body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    	
//    	ProjectView projectView = projectService.save(body);
//    	return ResponseEntity.status(HttpStatus.CREATED).body(projectView);
    	
    }
    
    @PostMapping("/{projectId}/assign-collaborator")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public ResponseEntity<String> assignCollaboratorToProject(
            @PathVariable Long projectId,
            @RequestBody CollaboratorAssignmentForm collaborator) {

    	projectService.assignCollaboratorToProject(projectId, collaborator.collaboratorId());
    	return ResponseEntity.ok("Colaborador atribuído ao projeto com sucesso.");
    }
    
}
