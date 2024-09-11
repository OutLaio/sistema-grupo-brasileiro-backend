package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.printeds;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;

public record BPrintedsForm(

        @NotNull(message = "The id_printed_type cannot be null")
        @JsonAlias({"id_printed_type"})
        Long idPrintedType,

        @JsonAlias({"id_printing_type"})
        Long idPrintingType,

        @JsonAlias({"paper_type"})
        String paperType,

        Integer folds,

        Integer pages
) {
}
