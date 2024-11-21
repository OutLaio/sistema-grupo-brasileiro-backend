package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO responsável por armazenar as informações de login do usuário.
 *
 * Este objeto é utilizado na autenticação do usuário no sistema, onde o email e a senha
 * são necessários para validar as credenciais do usuário.
 *
 * @param email    O e-mail do usuário, utilizado para autenticação.
 *                 Este campo não pode estar em branco e deve ser um e-mail válido.
 * @param password A senha do usuário, necessária para autenticação.
 *                 Este campo não pode ser nulo e deve corresponder à senha registrada no sistema.
 */
@Schema(description = "DTO responsável pela autenticação do usuário, contendo email e senha para o login.")
public record LoginForm(
        /**
         * O e-mail do usuário.
         * Este campo é obrigatório e não pode ser deixado em branco. Além disso, deve seguir o formato de um e-mail válido.
         *
         * Exemplo: "user@example.com"
         */
        @NotBlank(message = "The email field cannot be blank")
        @Schema(description = "O e-mail do usuário. Este campo não pode ser em branco e deve seguir o formato de um e-mail válido.", example = "user@example.com")
        String email,

        /**
         * A senha do usuário.
         * Este campo é obrigatório e não pode ser nulo. A senha deve ser fornecida conforme registrado no sistema.
         */
        @NotNull(message = "The password field cannot be null")
        @Schema(description = "A senha do usuário, necessária para autenticação. Este campo não pode ser nulo.", example = "Password@123")
        String password
) {
}
