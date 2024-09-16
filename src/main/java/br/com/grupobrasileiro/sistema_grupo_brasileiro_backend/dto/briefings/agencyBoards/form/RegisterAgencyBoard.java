package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import jakarta.validation.constraints.NotNull;

public record RegisterAgencyBoard(

        @NotNull(message = "Project form cannot be null.")
        ProjectForm projectForm,

        @NotNull(message = "Briefing form cannot be null.")
        BriefingForm briefingForm,


        @NotNull(message = "AgencyBoard form cannot be null.")
        BAgencyBoardsForm bAgencyBoardsForm


) {




}
