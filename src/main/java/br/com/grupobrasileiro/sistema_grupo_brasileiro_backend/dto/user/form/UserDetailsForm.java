package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * Representa um formulário de registro de usuário completo, incluindo informações de
 * empregado e informações de usuário, agrupadas em um único objeto.
 *
 * <p>Este registro combina as informações de perfil do empregado e as credenciais de
 * usuário, facilitando a criação e manipulação dos dados de registro de forma coesa.</p>
 */
public record UserDetailsForm(

        /**
         * Informações de perfil do empregado.
         *
         * <p>Este campo contém os dados do empregado, como nome, sobrenome, número de telefone,
         * setor de trabalho, ocupação e número de operações (NOP). Estas informações são
         * fornecidas através do record {@link EmployeeForm}.</p>
         */
        @JsonAlias({"employee"})
        EmployeeForm employeeForm,

        /**
         * Informações de usuário.
         *
         * <p>Este campo contém as credenciais do usuário, incluindo o e-mail, a senha e o
         * código do perfil (profile). Estas informações são fornecidas através do record
         * {@link UserForm}.</p>
         */
        @JsonAlias({"user"})
        UserForm userForm
) {


}
