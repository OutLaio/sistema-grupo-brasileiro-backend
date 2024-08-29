package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.register;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.annotations.PhoneNumber;
import jakarta.validation.constraints.*;

/**
 * Representa um formulário de registro de usuário com vários campos de entrada
 * validados usando anotações personalizadas e padrão Jakarta Bean Validation.
 */
public record EmployeeForm(

        /**
         * Nome do usuário.
         * Este campo é obrigatório e não pode estar em branco.
         */
        @NotBlank(message = "{name.required}")
        String name,

        /**
         * Sobrenome do usuário.
         * Este campo é obrigatório e não pode estar em branco.
         */
        @NotBlank(message = "{lastname.required}")
        String lastname,

        /**
         * Número de telefone do usuário.
         * Este campo é validado usando a anotação personalizada {@link PhoneNumber},
         * que garante o formato correto do número de telefone.
         */
        @PhoneNumber
        String phonenumber,

        /**
         * Setor de trabalho do usuário.
         * Este campo é obrigatório e não pode estar em branco.
         */
        @NotBlank(message = "{sector.required}")
        String sector,

        /**
         * Ocupação do usuário.
         * Este campo é obrigatório e não pode estar em branco.
         */
        @NotBlank(message = "{occupation.required}")
        String occupation,

        /**
         * Número de operações (NOP) do usuário.
         * Este campo é obrigatório e não pode estar em branco.
         */
        @NotBlank(message = "{nop.required}")
        String nop

) {
}
