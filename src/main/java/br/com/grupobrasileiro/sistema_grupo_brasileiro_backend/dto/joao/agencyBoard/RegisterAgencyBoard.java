package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.agencyBoard;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.signpost.form.BSignpostForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.agencyBoards.form.BAgencyBoardsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.projects.form.ProjectForm;
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
