package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.project;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ApproveForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.AssignCollaboratorForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.NewVersionForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.ProjectService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.VersionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class ProjectController {
    private final ProjectService projectService;
    private final VersionService versionService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    @PutMapping("{id}/assignCollaborator")
    @Transactional
    public ResponseEntity<?> assignCollaborator(
            @PathVariable Long id,
            @Valid @RequestBody AssignCollaboratorForm form){
    	
    	LOGGER.info("Assigning collaborator to project: {}", id);
        projectService.assignCollaborator(id, form);
        LOGGER.info("Collaborator successfully assigned to project: {}", id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/newVersion")
    @Transactional
    public ResponseEntity<?> newVersion(
            @PathVariable Long id,
            @Valid @RequestBody NewVersionForm form){
    	
        LOGGER.info("Creating new version for project: {}", id);
        versionService.create(id, form);
        LOGGER.info("New version successfully created for project: {}", id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/approve/supervisor")
    @Transactional
    public ResponseEntity<?> supervisorApprove(@RequestBody @Valid ApproveForm form){
    	
        LOGGER.info("Starting supervisor approval for project: {}", form.idProject());
        versionService.supervisorApprove(form);
        LOGGER.info("Supervisor approval completed for project: {}", form.idProject());
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/approve/client")
    @Transactional
    public ResponseEntity<?> clientApprove(@RequestBody @Valid ApproveForm form){
    	
        LOGGER.info("Starting client approval for project: {}", form.idProject());
        versionService.clientApprove(form);
        LOGGER.info("Client approval completed for project: {}", form.idProject());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/hasProduction")
    @Transactional
    public ResponseEntity<?> hasProduction(@PathVariable Long id, @RequestParam Boolean hasConfection){
    	
        LOGGER.info("Changing production status (hasConfection: {}) for project: {}", hasConfection, id);
        projectService.setHasConfection(id, hasConfection);
        LOGGER.info("Production status successfully updated for project: {}", id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/finish")
    @Transactional
    public ResponseEntity<?> finish(@PathVariable Long id){
    	
        LOGGER.info("Finishing project: {}", id);
        projectService.setFinished(id);
        LOGGER.info("Project successfully finished: {}", id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/standby")
    @Transactional
    public ResponseEntity<?> standby(@PathVariable Long id){
    	
        LOGGER.info("Putting project on standby: {}", id);
        projectService.setStandby(id);
        LOGGER.info("Project successfully put on standby: {}", id);
        return ResponseEntity.ok().build();
    }
}
