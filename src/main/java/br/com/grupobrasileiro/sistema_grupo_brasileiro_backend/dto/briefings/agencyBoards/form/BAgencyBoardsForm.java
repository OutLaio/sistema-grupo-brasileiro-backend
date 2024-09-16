package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Route;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BAgencyBoardsForm(

        @NotNull(message = "The id_agency_board_type cannot be null")
        @JsonAlias({"id_agency_board_type"})
        Long idAgencyBoardType,

        Long idBoardType,

        @NotBlank(message = "The board_location cannot be blank")
        @JsonAlias({"board_location"})
        String boardLocation,

        @NotBlank(message = "The observation cannot be blank")
        String observation,

        @JsonAlias({"other_routes"})
        List<OtherRoutesForm> otherRoutes,

        List<RoutesForm> routes
) {
}
