package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.profile.view.ProfileView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.annotations.ValidEmail;

/**
 * Representa uma visão das informações de usuário para exibição, excluindo o campo de senha.
 *
 * <p>Este record contém o e-mail, o código da função (role) e o ID do usuário, sem incluir
 * informações sensíveis como a senha. Os campos são validados para garantir que atendam aos
 * requisitos de formato.</p>
 */
public record UserView(

        /**
         * Identificador único do usuário.
         * Este campo é obrigatório e não pode ser nulo.
         */
        Long id,

        /**
         * Endereço de e-mail do usuário.
         * Este campo é validado usando a anotação personalizada {@link ValidEmail},
         * que verifica se o e-mail está em um formato válido e não está em branco.
         */
        String email,

        /**
         * Código da função (role) do usuário.
         * Este campo é obrigatório e não pode ser nulo.
         */
        ProfileView profileView

) {
}
