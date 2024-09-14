package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.Mikaelle.form;

import jakarta.validation.constraints.NotNull;

/**
 * Formulário para desativação de usuário.
 * <p>
 * Esta classe representa o formulário necessário para desativar um usuário específico
 * no sistema. Contém apenas o identificador único do usuário a ser desativado.
 * </p>
 *
 * @param id O ID do usuário a ser desativado.
 */
public record DisableUserForm(
    /**
     * O ID do usuário a ser desativado.
     * <p>
     * Este campo é utilizado para identificar de forma única o usuário
     * que deve ser desativado no sistema.
     * </p>
     */
    @NotNull(message = "The Id user is required")
    Long idUser
) {
}
