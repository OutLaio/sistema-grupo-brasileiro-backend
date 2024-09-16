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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class ProjectController {
    private ProjectService projectService;
    private VersionService versionService;

    @PutMapping("{id}/assignCollaborator")
    @Transactional
    public ResponseEntity<?> assignCollaborator(
            @PathVariable Long id,
            @Valid @RequestBody AssignCollaboratorForm form){
        projectService.assignCollaborator(id, form);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/newVersion")
    @Transactional
    public ResponseEntity<?> newVersion(
            @PathVariable Long id,
            @Valid @RequestBody NewVersionForm form){
        versionService.create(id, form);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/approve/supervisor")
    @Transactional
    public ResponseEntity<?> supervisorApprove(@RequestBody @Valid ApproveForm form){
        versionService.supervisorApprove(form);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/approve/client")
    @Transactional
    public ResponseEntity<?> clientApprove(@RequestBody @Valid ApproveForm form){
        versionService.clientApprove(form);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/hasProduction")
    @Transactional
    public ResponseEntity<?> hasProduction(@PathVariable Long id, @RequestParam Boolean hasConfection){
        projectService.setHasConfection(id, hasConfection);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/finish")
    @Transactional
    public ResponseEntity<?> finish(@PathVariable Long id){
        projectService.setFinished(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/standby")
    @Transactional
    public ResponseEntity<?> standby(@PathVariable Long id){
        projectService.setStandby(id);
        return ResponseEntity.ok().build();
    }
}
