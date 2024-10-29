package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;

public record RegisterInternalCampaignsForm(
		@NotNull(message = "Project form cannot be null.")
        @JsonAlias({"project"})
        ProjectForm projectForm,

        @NotNull(message = "Briefing form cannot be null.")
        @JsonAlias({"briefing"})
        BriefingForm briefingForm,

        @NotNull(message = "Signpost form cannot be null.")
        @JsonAlias({"internal-campaign", "internal_campaign", "internalCampaign"})
        BInternalCampaignsForm internalCampaignsForm
) { }
