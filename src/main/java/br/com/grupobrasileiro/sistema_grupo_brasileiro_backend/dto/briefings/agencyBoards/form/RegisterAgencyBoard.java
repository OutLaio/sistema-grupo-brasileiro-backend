package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;

public record RegisterAgencyBoard(

        @NotNull(message = "Project form cannot be null.")
        @JsonAlias({"project"})
        ProjectForm projectForm,

        @NotNull(message = "Briefing form cannot be null.")
        @JsonAlias({"briefing"})
        BriefingForm briefingForm,

        @NotNull(message = "AgencyBoard form cannot be null.")
        @JsonAlias({"agency-board", "agency_board", "agencyBoard"})
        BAgencyBoardsForm bAgencyBoardsForm
) { }
