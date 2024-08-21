package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form;

import java.util.HashSet;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.ProjectStatusEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Project;

@Component
public class ProjectFormMapper implements Mapper<ProjectForm, Project> {

    /**
	 * Converte os objetos ProjectForm num conjunto de Project
	 * @param i - ProjectForm
	 * @return Project (id, title, description, progress, status, projectUsers)
	 * */
    @Override
    public Project map(ProjectForm projectForm) {
      
        return new Project(
            null,
            projectForm.title(),
            projectForm.description(),
            0,
            ProjectStatusEnum.A_FAZER.getCode(),
            new HashSet<>()
        );
    }
}
