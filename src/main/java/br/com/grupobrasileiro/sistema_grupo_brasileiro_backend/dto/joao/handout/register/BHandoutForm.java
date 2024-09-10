package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.handout.register;

import jakarta.validation.constraints.NotNull;

/**
 * Objeto de Transferência de Dados (DTO) para o cadastro de Comunicado (Handout).
 * Este DTO encapsula as informações necessárias para o cadastro de um comunicado associada a um tipo e briefing.
 *
 * @param idHandoutType O ID do tipo de comunicado.
 *                      Não pode ser nulo.
 */
public record BHandoutForm(

        @NotNull
        Long idHandoutType

) {
}
