package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.laio;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.projects.form.ProjectForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/projects")
@SecurityRequirement(name = "bearer-key")
public class ProjectController {

    @Autowired
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
