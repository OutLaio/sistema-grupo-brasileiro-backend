package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.annotations.PhoneNumber;
import com.fasterxml.jackson.annotation.JsonAlias;
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
        @NotBlank(message = "The employee name cannot be blank")
        String name,

        /**
         * Sobrenome do usuário.
         * Este campo é obrigatório e não pode estar em branco.
         */
        @NotBlank(message = "The employee lastname cannot be blank")
        String lastname,

        /**
         * Número de telefone do usuário.
         * Este campo é validado usando a anotação personalizada {@link PhoneNumber},
         * que garante o formato correto do número de telefone.
         */
        @PhoneNumber
        @JsonAlias({"phone_number"})
        String phoneNumber,

        /**
         * Setor de trabalho do usuário.
         * Este campo é obrigatório e não pode estar em branco.
         */
        @NotBlank(message = "The employee sector cannot be blank")
        String sector,

        /**
         * Ocupação do usuário.
         * Este campo é obrigatório e não pode estar em branco.
         */
        @NotBlank(message = "The employee occupation cannot be blank")
        String occupation,

        /**
         * Núcleo Operacional de Origem / Agência do usuário.
         * Este campo é obrigatório e não pode estar em branco.
         */
        @NotBlank(message = "The employee agency cannot be blank")
        String agency,

        /**
         * Indicação do avatar do usuário.
         * Este campo é obrigatório.
         */
        @NotNull(message = "The employee avatar cannot be null")
        Long avatar
) {
}
