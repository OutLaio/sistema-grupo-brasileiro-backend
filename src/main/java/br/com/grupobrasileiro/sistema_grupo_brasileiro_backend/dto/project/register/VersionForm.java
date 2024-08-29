package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.project.register;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

/**
 * Representa um formulário para registrar ou atualizar uma versão de um projeto.
 *
 * <p>Este registro contém informações sobre a versão de um projeto, incluindo IDs, título, feedback, datas de início e fim,
 * número da versão, link do produto e status de aprovação do cliente e supervisão.</p>
 */
public record VersionForm(

        /**
         * ID do colaborador associado à versão do projeto.
         * Este campo é obrigatório e não pode ser nulo.
         */
        @NotNull(message = "{versionForm.idColaborator.required}")
        Long idColaborator,

        /**
         * ID do projeto ao qual a versão pertence.
         * Este campo é obrigatório e não pode ser nulo.
         */
        @NotNull(message = "{versionForm.idProject.required}")
        Long idProject,

        /**
         * Título da versão do projeto.
         * Este campo é obrigatório e não pode estar em branco.
         */
        @NotBlank(message = "{versionForm.title.required}")
        @Size(max = 100, message = "{versionForm.title.size}")
        String title,

        /**
         * Feedback relacionado à versão do projeto.
         * Este campo é opcional e pode estar em branco, mas não deve exceder o limite de tamanho especificado.
         */
        @Size(max = 500, message = "{versionForm.feedback.size}")
        String feedback,

        /**
         * Data e hora de início da versão do projeto.
         * Este campo é obrigatório e não pode ser nulo.
         */
        @NotNull(message = "{versionForm.begin.required}")
        LocalDateTime begin,

        /**
         * Data e hora de término da versão do projeto.
         * Este campo é obrigatório e não pode ser nulo.
         */
        @NotNull(message = "{versionForm.end.required}")
        LocalDateTime end,

        /**
         * Número da versão do projeto.
         * Este campo é obrigatório e deve estar dentro do intervalo de 1 a 999.
         */
        @NotNull(message = "{versionForm.numVersion.required}")
        @Min(value = 1, message = "{versionForm.numVersion.min}")
        Long numVersion,

        /**
         * Link do produto associado à versão do projeto.
         * Este campo é opcional e pode estar em branco, mas não deve exceder o limite de tamanho especificado.
         */
        @Size(max = 255, message = "{versionForm.productLink.size}")
        String productLink,

        /**
         * Indica se a versão foi aprovada pelo cliente.
         * Este campo é obrigatório e não pode ser nulo.
         */
        @NotNull(message = "{versionForm.clientApproved.required}")
        Boolean clientApproved,

        /**
         * Indica se a versão foi aprovada pela supervisão.
         * Este campo é obrigatório e não pode ser nulo.
         */
        @NotNull(message = "{versionForm.supervisionApproved.required}")
        Boolean supervisionApproved
) {
}
