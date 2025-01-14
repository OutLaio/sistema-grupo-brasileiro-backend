package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;

import java.time.LocalDateTime;

public record DialogBoxView(
        Long id,
        EmployeeSimpleView employee,
        LocalDateTime time,
        String dialog) {
}
