package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.project.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.UserDetailsView;

import java.time.LocalDateTime;

/**
 * Representa uma visão detalhada de uma versão de projeto.
 *
 * <p>Este record é usado para exibir informações sobre uma versão específica de um projeto.</p>
 */
public record VersionView(

        /**
         * ID da versão do projeto.
         * Este campo exibe o identificador único da versão.
         */
        Long id,

        /**
         * Colaborador associado à versão do projeto.
         * Este o identificador do colaborador responsável pela versão.
         */
        UserDetailsView userDetailsView,

        /**
         * ID do projeto ao qual a versão pertence.
         * Este campo exibe o projeto relacionado à versão.
         */
        ProjectView projectView,

        /**
         * Título da versão do projeto.
         * Este campo exibe o título da versão.
         */
        String title,

        /**
         * Feedback relacionado à versão do projeto.
         * Este campo exibe o feedback recebido para a versão.
         */
        String feedback,

        /**
         * Data e hora de início da versão do projeto.
         * Este campo exibe o início da versão.
         */
        LocalDateTime begin,

        /**
         * Data e hora de término da versão do projeto.
         * Este campo exibe o término da versão.
         */
        LocalDateTime end,

        /**
         * Número da versão do projeto.
         * Este campo exibe o número da versão.
         */
        Long numVersion,

        /**
         * Link do produto associado à versão do projeto.
         * Este campo exibe o link para o produto relacionado à versão.
         */
        String productLink,

        /**
         * Indica se a versão foi aprovada pelo cliente.
         * Este campo exibe a aprovação do cliente para a versão.
         */
        Boolean clientApproved,

        /**
         * Indica se a versão foi aprovada pela supervisão.
         * Este campo exibe a aprovação da supervisão para a versão.
         */
        Boolean supervisionApproved

) {
}
