package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import jakarta.validation.constraints.NotNull;

public record RegisterPrintedForm(
		@NotNull(message = "Project form cannot be null.")
        ProjectForm projectForm,

        @NotNull(message = "Briefing form cannot be null.")
        BriefingForm briefingForm,

        @NotNull(message = "Signpost form cannot be null.")
        PrintedForm printedForm
) {

}
