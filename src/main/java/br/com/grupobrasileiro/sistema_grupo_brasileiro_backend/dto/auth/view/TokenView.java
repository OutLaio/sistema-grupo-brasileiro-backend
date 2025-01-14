package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeView;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO que representa a resposta de um token gerado para o usuário autenticado.
 *
 * Este objeto contém o token JWT gerado após a autenticação do usuário, junto com informações do
 * colaborador (employee) autenticado. O token é utilizado para autenticar futuras requisições.
 */
@Schema(description = "DTO que contém o token gerado para autenticação e informações do colaborador autenticado.")
public record TokenView(

        /**
         * Token JWT gerado para o usuário autenticado.
         * Este token é utilizado para autenticar o usuário nas próximas requisições.
         *
         * Exemplo: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
         */
        @Schema(description = "Token JWT gerado para o usuário autenticado. Este campo contém o token necessário para autenticar o usuário em futuras requisições.",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token,

        /**
         * Informações do colaborador (employee) autenticado.
         * Contém dados do usuário autenticado, como ID, nome, cargo, etc.
         */
        @Schema(description = "Informações do colaborador autenticado, como ID, nome e cargo.",
                implementation = EmployeeView.class)
        EmployeeView employee
) { }
