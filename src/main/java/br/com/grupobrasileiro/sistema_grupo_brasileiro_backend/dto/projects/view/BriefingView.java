package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.companiesBriefing.view.CompaniesBriefingsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.measurements.view.MeasurementsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Version;

import java.time.LocalDate;
import java.util.Set;

public record BriefingView(
        Long id,
        BriefingTypeView briefingType,
        LocalDate startTime,
        LocalDate expectedTime,
        LocalDate finishTime,
        String detailedDescription,
        MeasurementsView measurements,
        CompaniesBriefingsView companies,
        String otherCompanies,
        Set<VersionView> versions
) {
}
