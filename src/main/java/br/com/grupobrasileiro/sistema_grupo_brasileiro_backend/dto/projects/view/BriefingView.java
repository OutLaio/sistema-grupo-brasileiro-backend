package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view;

import java.time.LocalDate;

public record BriefingView(
        Long id,
        BriefingTypeView briefingType,
        LocalDate startTime,
        LocalDate expectedTime,
        LocalDate finishTime,
        String detailedDescription
) {
}
