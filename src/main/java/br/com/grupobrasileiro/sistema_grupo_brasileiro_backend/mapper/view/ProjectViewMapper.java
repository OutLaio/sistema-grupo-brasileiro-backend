package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.ProjectUser;

@Component
public class ProjectViewMapper implements Mapper<Project, ProjectView> {

    /**
     * Converte um objeto Project em uma ProjectView.
     * @param project - entidade Project.
     * @return ProjectView - visão do projeto.
     */
    @Override
    public ProjectView map(Project project) {
        // Obtém o ProjectUserId do cliente associado ao projeto
        Long projectUserId = project.getUsers().stream()
            .filter(projectUser -> projectUser.getClient() != null)
            .map(ProjectUser::getId)
            .findFirst()
            .orElse(null);

        // Retorna a ProjectView com os dados do projeto
        return new ProjectView(
            project.getId(),
            project.getTitle(),
            project.getDescription(),
            project.getProgress(),
            project.getStatus(),
            projectUserId
        );
    }
}
