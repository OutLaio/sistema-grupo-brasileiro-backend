package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

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
}
