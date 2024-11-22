package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.ProjectStatusEnum;
import com.fasterxml.jackson.annotation.JsonAlias;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record AlterStatusForm(
        @NotNull(message = "The status cannot be null!")
        @JsonAlias({"new_status", "new-status", "status"})
        ProjectStatusEnum newStatus
) {
}
