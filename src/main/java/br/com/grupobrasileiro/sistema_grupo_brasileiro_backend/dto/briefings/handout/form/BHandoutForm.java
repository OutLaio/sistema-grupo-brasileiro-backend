package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.form;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;

/**
 * Objeto de Transferência de Dados (DTO) para o cadastro de Comunicado (Handout).
 * Este DTO encapsula as informações necessárias para o cadastro de um comunicado associada a um tipo e briefing.
 *
 * @param idHandoutType O ID do tipo de comunicado.
 *                      Não pode ser nulo.
 */
public record BHandoutForm(

        @NotNull(message = "The handout type cannot be null")
        @JsonAlias("id_handout_type")
        Long idHandoutType

) {
}
