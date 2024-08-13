package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;

@Component
public class ProjectViewMapper implements Mapper<Project, ProjectView> {

    @Override
    public ProjectView map(Project i) {
 	
        return new ProjectView(
            i.getId(),
            i.getTitle()

        );
    }
}
