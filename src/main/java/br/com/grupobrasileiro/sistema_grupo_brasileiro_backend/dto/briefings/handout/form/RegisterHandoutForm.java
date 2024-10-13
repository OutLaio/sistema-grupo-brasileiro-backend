package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.form;

import com.fasterxml.jackson.annotation.JsonAlias;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import jakarta.validation.constraints.NotNull;

public record  RegisterHandoutForm(

    @NotNull(message = "Project form cannot be null.")
    @JsonAlias({"project"})
    ProjectForm projectForm,

    @NotNull(message = "Briefing form cannot be null.")
    @JsonAlias({"briefing"})
    BriefingForm briefingForm,
    
    @NotNull(message = "Handout form cannot be null.")
    @JsonAlias({"handout"})
    BHandoutForm handoutForm
    ){
}
