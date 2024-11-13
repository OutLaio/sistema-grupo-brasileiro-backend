package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.form.BSignpostForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import jakarta.validation.constraints.NotNull;

/**
 * Objeto de Transferência de Dados (DTO) para o registro de Adesivo (Sticker).
 * Este DTO reúne informações de projeto, briefing e adesivo necessárias para o registro completo de um adesivo.
 *
 * @param project  O formulário do projeto associado, obrigatório.
 * @param briefing O formulário de briefing associado ao projeto, obrigatório.
 * @param sticker  O formulário de adesivo com as informações do adesivo, obrigatório.
 */
public record RegisterStickerForm(
        @NotNull(message = "Project form cannot be null.")
        ProjectForm project,

        @NotNull(message = "Briefing form cannot be null.")
        BriefingForm briefing,

        @NotNull(message = "Sticker form cannot be null.")
        BStickerForm sticker
) {
}
