package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import jakarta.validation.constraints.NotNull;

/**
 * Registro que representa o cadastro de um ponto de sinalização, que inclui
 * detalhes do projeto, informações do briefing e dados específicos do formulário de sinalização.
 *
 * @param project Detalhes do projeto associado ao ponto de sinalização.
 *                    Não pode ser nulo. Veja {@link ProjectForm}.
 * @param briefing Informações do briefing para o ponto de sinalização.
 *                     Não pode ser nulo. Veja {@link BriefingForm}.
 * @param signpost Formulário com dados específicos sobre o ponto de sinalização.
 *                     Não pode ser nulo. Veja {@link BSignpostForm}.
 */
public record RegisterSignpostForm(

        @NotNull(message = "Project form cannot be null.")
        ProjectForm project,

        @NotNull(message = "Briefing form cannot be null.")
        BriefingForm briefing,

        @NotNull(message = "Signpost form cannot be null.")
        BSignpostForm signpost

) {
}
