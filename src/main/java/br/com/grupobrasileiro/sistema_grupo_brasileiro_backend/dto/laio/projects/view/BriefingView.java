package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.projects.view;

import java.time.LocalDateTime;

public record BriefingView(
        Long id,
        BriefingTypeView briefingType,
        LocalDateTime startTime,
        LocalDateTime expectedTime,
        LocalDateTime finishTime,
        String detailedDescription
) {
}
