package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.laio;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/projects")
@Slf4j
public class ProjectController {

    private VersionService versionService;

    @RequestMapping("/approve/supervisor")
    public ResponseEntity<?> supervisorApprove(@RequestBody @Valid ApproveForm form){
    	log.info("Iniciando aprovação do supervisor para o projeto: {}", form.idProject());
        versionService.supervisorApprove(form);
        log.info("Aprovação do supervisor concluída para o projeto: {}", form.idProject());
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/approve/client")
    public ResponseEntity<?> clientApprove(@RequestBody @Valid ApproveForm form){
    	log.info("Iniciando aprovação do cliente para o projeto: {}", form.idProject());
        versionService.clientApprove(form);
        log.info("Aprovação do cliente concluída para o projeto: {}", form.idProject());
        return ResponseEntity.ok().build();
    }

}
