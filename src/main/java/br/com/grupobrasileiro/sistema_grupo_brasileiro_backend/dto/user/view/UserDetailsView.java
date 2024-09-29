package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view;

/**
 * Representa uma visão consolidada das informações de registro de um usuário,
 * excluindo detalhes sensíveis, como a senha.
 *
 * <p>Este record combina as informações do empregado e as credenciais do usuário,
 * facilitando a visualização de dados de registro de forma segura e coesa.</p>
 */
public record UserDetailsView(

        /**
         * Identificador único do registro.
         * Este campo é obrigatório e não pode ser nulo.
         */
        Long id,

        /**
         * Informações de perfil do empregado.
         *
         * <p>Contém os dados do empregado, como nome, sobrenome, número de telefone,
         * setor de trabalho, ocupação e número de operações (NOP). Estas informações
         * são extraídas do record {@link EmployeeView}.</p>
         */
        EmployeeView employeeView
) {
}
