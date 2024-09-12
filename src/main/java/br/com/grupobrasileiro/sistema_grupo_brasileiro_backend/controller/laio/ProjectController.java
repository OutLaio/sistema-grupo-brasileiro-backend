package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.laio;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private VersionService versionService;

    @RequestMapping("/approve/supervisor")
    public ResponseEntity<?> supervisorApprove(@RequestBody @Valid ApproveForm form){
        versionService.supervisorApprove(form);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/approve/client")
    public ResponseEntity<?> clientApprove(@RequestBody @Valid ApproveForm form){
        versionService.clientApprove(form);
        return ResponseEntity.ok().build();
    }

}
