package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form;

import java.util.HashSet;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.ProjectStatusEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.ProjectUser;


@Component
public class ProjectFormMapper implements Mapper<ProjectForm, Project> {

    /**
	 * Converte os objetos ProjectForm num conjunto de Project
	 * @param i - ProjectForm
	 * @return Project (id, title, description, progress, status, projectUsers)
	 * */
    @Override
    public Project map(ProjectForm projectForm) {
        // Busca o cliente pelo ID fornecido no formulário

        // Cria a relação ProjectUser para o cliente
        ProjectUser projectUser = new ProjectUser();


        // Cria o projeto com o cliente associado
        Project project = new Project(
            null,
            projectForm.title(),
            projectForm.description(),
            0,
            ProjectStatusEnum.A_FAZER.getCode(),
            new HashSet<>()
        );

        // Associa o projeto ao ProjectUser
        projectUser.setProject(project);

        return project;
    }
}
