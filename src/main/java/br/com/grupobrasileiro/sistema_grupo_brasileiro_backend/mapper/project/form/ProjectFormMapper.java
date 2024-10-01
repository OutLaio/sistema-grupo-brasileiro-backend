package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectFormMapper implements Mapper<ProjectForm, Project> {

    @Override
    public Project map(ProjectForm projectForm) {
        return new Project(
                null,
                null,
                null,
                projectForm.title(),
                projectForm.status().toString(),
                false,
                null
        );    }
}
