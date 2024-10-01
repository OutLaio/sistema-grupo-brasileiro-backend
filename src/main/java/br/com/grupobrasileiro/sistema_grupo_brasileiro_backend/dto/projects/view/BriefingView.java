package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.companiesBriefing.view.CompaniesBriefingsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.measurements.view.MeasurementsView;

import java.time.LocalDate;

public record BriefingView(
        Long id,
        BriefingTypeView briefingType,
        LocalDate startTime,
        LocalDate expectedTime,
        LocalDate finishTime,
        String detailedDescription,
        MeasurementsView measurements,
        CompaniesBriefingsView companies,
        String otherCompanies
) {
}
