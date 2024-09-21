package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.view;

import java.time.LocalDateTime;

public record DialogBoxView(
        Long id,
        String employeeName,
        String briefingTitle,
        LocalDateTime time,
        String dialog) {
}
