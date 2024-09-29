package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.companiesBriefing.form.CompaniesBriefingsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.measurements.form.MeasurementsForm;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public record BriefingForm(
        @NotNull(message = "The expected date cannot be null")
        @JsonAlias({"expected_date"})
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate expectedDate,

        @NotBlank(message = "The detailed description cannot be blank")
        @JsonAlias({"detailed_description"})
        String detailedDescription,

        Set<CompaniesBriefingsForm> companies,

        @JsonAlias({"other_company"})
        String otherCompany,

        @NotNull(message = "The briefing type cannot be null")
        @JsonAlias({"id_briefing_type"})
        Long idBriefingType,

        MeasurementsForm measurement
) {
}
