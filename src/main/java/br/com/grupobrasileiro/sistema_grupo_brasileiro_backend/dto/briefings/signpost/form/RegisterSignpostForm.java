package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import jakarta.validation.constraints.NotNull;

/**
 * Registro que representa o cadastro de um ponto de sinalização, que inclui
 * detalhes do projeto, informações do briefing e dados específicos do formulário de sinalização.
 *
 * @param projectForm Detalhes do projeto associado ao ponto de sinalização.
 *                    Não pode ser nulo. Veja {@link ProjectForm}.
 * @param briefingForm Informações do briefing para o ponto de sinalização.
 *                     Não pode ser nulo. Veja {@link BriefingForm}.
 * @param signpostForm Formulário com dados específicos sobre o ponto de sinalização.
 *                     Não pode ser nulo. Veja {@link BSignpostForm}.
 */
public record RegisterSignpostForm(

        @NotNull(message = "Project form cannot be null.")
        ProjectForm projectForm,

        @NotNull(message = "Briefing form cannot be null.")
        BriefingForm briefingForm,

        @NotNull(message = "Signpost form cannot be null.")
        BSignpostForm signpostForm

) {
}
