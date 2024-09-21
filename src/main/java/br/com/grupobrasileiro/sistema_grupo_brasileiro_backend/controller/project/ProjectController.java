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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Slf4j
public class ProjectController {
    private final ProjectService projectService;
    private final VersionService versionService;

    @PutMapping("{id}/assignCollaborator")
    @Transactional
    public ResponseEntity<?> assignCollaborator(
            @PathVariable Long id,
            @Valid @RequestBody AssignCollaboratorForm form){
        log.info("Atribuindo colaborador ao projeto: {}", id);
        projectService.assignCollaborator(id, form);
        log.info("Colaborador atribuído com sucesso ao projeto: {}", id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/newVersion")
    @Transactional
    public ResponseEntity<?> newVersion(
            @PathVariable Long id,
            @Valid @RequestBody NewVersionForm form){
        log.info("Criando nova versão para o projeto: {}", id);
        versionService.create(id, form);
        log.info("Nova versão criada com sucesso para o projeto: {}", id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/approve/supervisor")
    @Transactional
    public ResponseEntity<?> supervisorApprove(@RequestBody @Valid ApproveForm form){
        log.info("Iniciando aprovação do supervisor para o projeto: {}", form.idProject());
        versionService.supervisorApprove(form);
        log.info("Aprovação do supervisor concluída para o projeto: {}", form.idProject());
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/approve/client")
    @Transactional
    public ResponseEntity<?> clientApprove(@RequestBody @Valid ApproveForm form){
        log.info("Iniciando aprovação do cliente para o projeto: {}", form.idProject());
        versionService.clientApprove(form);
        log.info("Aprovação do cliente concluída para o projeto: {}", form.idProject());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/hasProduction")
    @Transactional
    public ResponseEntity<?> hasProduction(@PathVariable Long id, @RequestParam Boolean hasConfection){
        log.info("Alterando status de produção (hasConfection: {}) para o projeto: {}", hasConfection, id);
        projectService.setHasConfection(id, hasConfection);
        log.info("Status de produção atualizado com sucesso para o projeto: {}", id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/finish")
    @Transactional
    public ResponseEntity<?> finish(@PathVariable Long id){
        log.info("Finalizando o projeto: {}", id);
        projectService.setFinished(id);
        log.info("Projeto finalizado com sucesso: {}", id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/standby")
    @Transactional
    public ResponseEntity<?> standby(@PathVariable Long id){
        log.info("Colocando o projeto em standby: {}", id);
        projectService.setStandby(id);
        log.info("Projeto colocado em standby com sucesso: {}", id);
        return ResponseEntity.ok().build();
    }
}
