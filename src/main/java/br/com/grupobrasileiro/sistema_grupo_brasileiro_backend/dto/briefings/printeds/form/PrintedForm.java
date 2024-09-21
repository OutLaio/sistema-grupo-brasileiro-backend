package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.form;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;

public record PrintedForm(

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
