package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view.EmployeeSimpleViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectViewMapper implements Mapper<Project, ProjectView> {

    @Autowired
    private EmployeeSimpleViewMapper employeeMapperSimpleView;

    @Override
    public ProjectView map(Project project) {
        EmployeeSimpleView clientView = project.getClient() != null ? employeeMapperSimpleView.map(project.getClient()) : null;
        EmployeeSimpleView collaboratorView = project.getCollaborator() != null ? employeeMapperSimpleView.map(project.getCollaborator()) : null;

        return new ProjectView(
                project.getId(),
                project.getTitle(),
                project.getStatus(),
                clientView,
                collaboratorView
        );
    }
}
