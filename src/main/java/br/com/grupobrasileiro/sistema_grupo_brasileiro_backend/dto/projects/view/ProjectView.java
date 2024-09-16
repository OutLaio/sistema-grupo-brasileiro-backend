package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;

public record ProjectView(
        Long idProject,
        String title,
        String status,
        EmployeeSimpleView client,
        EmployeeSimpleView collaborator
) {
}
