package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.form.BSignpostForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;

/**
 * Objeto de Transferência de Dados (DTO) para o registro de Adesivo (Sticker).
 * Este DTO reúne informações de projeto, briefing e adesivo necessárias para o registro completo de um adesivo.
 *
 * @param projectForm  O formulário do projeto associado, obrigatório.
 * @param briefingForm O formulário de briefing associado ao projeto, obrigatório.
 * @param stickerForm  O formulário de adesivo com as informações do adesivo, obrigatório.
 */
public record RegisterStickerForm(
        @NotNull(message = "Project form cannot be null.")
        @JsonAlias({"project"})
        ProjectForm projectForm,

        @NotNull(message = "Briefing form cannot be null.")
        @JsonAlias({"briefing"})
        BriefingForm briefingForm,

        @NotNull(message = "Sticker form cannot be null.")
        @JsonAlias({"sticker"})
        BStickerForm stickerForm
) {
}
