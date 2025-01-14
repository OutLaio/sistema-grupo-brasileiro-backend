package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;

import java.time.LocalDate;

public record ProjectView(
        Long id,
        String title,
        String status,
        EmployeeSimpleView client,
        EmployeeSimpleView collaborator,
        String briefingType,
        LocalDate startDate
) {
}
