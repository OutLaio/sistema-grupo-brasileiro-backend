package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form;

import com.fasterxml.jackson.annotation.JsonAlias;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record AlterDateForm(
    @NotNull(message = "The date cannot be null!")
    @JsonAlias({"new_date", "new-date", "date"})
    @Future(message = "The date must be in future!")
    LocalDate newDate
) {
}
