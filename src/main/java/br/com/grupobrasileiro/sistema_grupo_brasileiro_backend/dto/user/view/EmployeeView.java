package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view;

/**
 * Representa uma visão das informações do funcionário para exibição.
 *
 * <p>Este record contém os detalhes básicos do funcionário, incluindo o nome, sobrenome,
 * número de telefone, setor, ocupação, número de operações (NOP) e o ID do usuário associado,
 * sem incluir informações sensíveis.</p>
 */
public record EmployeeView(

        /**
         * Identificador único do funcionário.
         * Este campo é obrigatório e não pode ser nulo.
         */
        Long id,

        /**
         * Identificador único do usuário associado ao funcionário.
         * Este campo é obrigatório e não pode ser nulo.
         */
        UserView userView,

        /**
         * Nome do funcionário.
         * Este campo exibe o nome do funcionário.
         */
        String name,

        /**
         * Sobrenome do funcionário.
         * Este campo exibe o sobrenome do funcionário.
         */
        String lastname,

        /**
         * Número de telefone do funcionário.
         * Este campo exibe o número de telefone formatado do funcionário.
         */
        String phonenumber,

        /**
         * Setor de trabalho do funcionário.
         * Este campo exibe o setor onde o funcionário trabalha.
         */
        String sector,

        /**
         * Ocupação do funcionário.
         * Este campo exibe a ocupação ou cargo do funcionário.
         */
        String occupation,

        /**
         * Nucleo Operacional de Origem / Agencia do funcionario.
         * Este campo exibe o número de operações associadas ao funcionário.
         */
        String agency,

        Long avatar
) {
}
