package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.project.register;


import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * Representa um formulário de projeto, contendo informações detalhadas sobre um projeto específico.
 *
 * <p>Este record é usado para registrar ou atualizar informações sobre um projeto, incluindo o ID do cliente,
 * título, descrição, status, progresso e tipo de briefing.</p>
 */
public record ProjectForm(


        /**
         * Título do projeto.
         * Este campo é obrigatório e não pode estar em branco.
         */
        @NotBlank(message = "{title.required}")
        @Size(max = 100, message = "{title.size}")
        String title,

        /**
         * Descrição detalhada do projeto.
         * Este campo é opcional e pode estar em branco, mas não deve exceder o limite de tamanho especificado.
         */
        @Size(max = 500, message = "{description.size}")
        String description,

        /**
         * Status atual do projeto.
         * Este campo é obrigatório e não pode estar em branco.
         */
        @NotBlank(message = "{status.required}")
        @Size(max = 50, message = "{status.size}")
        String status,

        /**
         * Progresso atual do projeto em percentual.
         * Este campo é obrigatório e deve estar dentro do intervalo de 0.0 a 100.0.
         */
        @NotNull(message = "{progress.required}")
        @DecimalMin(value = "0.0", message = "{progress.min}")
        @DecimalMax(value = "100.0", message = "{progress.max}")
        BigDecimal progress,


        /**
         * Tipo de briefing do projeto.
         * Este campo é obrigatório e não pode estar em branco.
         */
        @NotNull(message = "{briefing_type.required}")
        Long briefing_type

) {
}
