package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.form;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;

/**
 * Objeto de Transferência de Dados (DTO) para o cadastro de Placa (Signpost).
 * Este DTO encapsula as informações necessárias para o registro de uma placa.
 *
 * @param idMaterial    O ID do material da placa.
 * @param boardLocation A localização da placa.
 * @param sector        O setor onde a placa está localizada.
 */
public record BSignpostForm(

        @NotNull(message = "The id_material cannot be null")
        @JsonAlias("id_material")
        Long idMaterial,

        @NotNull(message = "The board_location cannot be null")
        @JsonAlias("board_location")
        String boardLocation,

        @NotNull(message = "The sector cannot be null")
        String sector
) {
}
