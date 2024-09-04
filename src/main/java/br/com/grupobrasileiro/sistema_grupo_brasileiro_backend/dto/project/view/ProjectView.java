package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.project.view;

import java.math.BigDecimal;

/**
 * Representa uma visão detalhada de um projeto.
 *
 * <p>Este record é usado para exibir informações detalhadas sobre um projeto, incluindo título, descrição, status,
 * progresso e tipo de briefing.</p>
 */
public record ProjectView(


        Long id,

        /**
         * Título do projeto.
         * Este campo exibe o título do projeto.
         */
        String title,

        /**
         * Descrição detalhada do projeto.
         * Este campo exibe a descrição do projeto.
         */
        String description,

        /**
         * Status atual do projeto.
         * Este campo exibe o status do projeto.
         */
        String status,

        /**
         * Progresso atual do projeto em percentual.
         * Este campo exibe o progresso do projeto.
         */
        BigDecimal progress,

        /**
         * Tipo de briefing do projeto.
         * Este campo exibe o tipo de briefing associado ao projeto.
         */
        Long briefingType

) {
}
