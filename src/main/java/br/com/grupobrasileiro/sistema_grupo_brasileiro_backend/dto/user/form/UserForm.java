package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.annotations.Password;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.annotations.ValidEmail;


/**
 * Representa um formulário de informações de usuário para registro ou atualização de conta.
 *
 * <p>Este record contém as credenciais e o perfil do usuário, incluindo o e-mail, a senha e
 * o código da função (role). Os campos são validados para garantir que atendam aos requisitos de
 * segurança e formato.</p>
 */
public record UserForm(


        /**
         * Endereço de e-mail do usuário.
         * Este campo é validado usando a anotação personalizada {@link ValidEmail},
         * que verifica se o e-mail está em um formato válido e não está em branco.
         */
        @ValidEmail
        String email,

        /**
         * Senha do usuário.
         * Este campo é validado usando a anotação personalizada {@link PasswordForm},
         * que garante que a senha atenda a todos os critérios de segurança, incluindo
         * comprimento mínimo, letras maiúsculas e minúsculas, dígitos e caracteres especiais.
         */
        @Password
        String password,

        /**
         * Código da função (role) do usuário.
         * Este campo é obrigatório e não pode ser nulo.
         */
        long profile

) {
}
