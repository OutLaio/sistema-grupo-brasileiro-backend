package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.form;

import com.fasterxml.jackson.annotation.JsonAlias;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import jakarta.validation.constraints.NotNull;

public record  RegisterHandout(

    @NotNull(message = "Project form cannot be null.")
    @JsonAlias({"projectForm"})
    ProjectForm projectForm,


    @NotNull(message = "Briefing form cannot be null.")
    @JsonAlias({"briefing"})
    BriefingForm briefingForm
    ){
}
