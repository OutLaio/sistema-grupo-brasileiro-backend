package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;


/**
 * Classe record que representa o registro de presentes (gifts) associado a um projeto e briefing.
 * Usa validação para garantir que os campos obrigatórios não sejam nulos.
 */
public record RegisterGiftForm(

    /**
     * Formulário de projeto associado ao presente.
     * Deve ser fornecido e não pode ser nulo.
     * O JSON pode utilizar "project" como alias.
     */
    @NotNull(message = "Project form cannot be null.")
    @JsonAlias({"project"})
    ProjectForm projectForm,

    /**
     * Formulário de briefing associado ao presente.
     * Deve ser fornecido e não pode ser nulo.
     * O JSON pode utilizar "briefing" como alias.
     */
    @NotNull(message = "Briefing form cannot be null.")
    @JsonAlias({"briefing"})
    BriefingForm briefingForm,

    /**
     * Formulário específico do presente (gift) a ser registrado.
     * Deve ser fornecido e não pode ser nulo.
     * O JSON pode utilizar "gift-form", "gift_form", ou "giftForm" como alias.
     */
    @NotNull(message = "giftForm form cannot be null.")
    @JsonAlias({"gift-form", "gift_form", "giftForm"})
    GiftForm giftForm
) {

}
