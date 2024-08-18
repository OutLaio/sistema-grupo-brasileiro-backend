package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form;

import java.util.Set;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.ProjectUser;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;

@Component
public class ProjectFormMapper implements Mapper<ProjectForm, Project> {

    private final UserRepository userRepository;

    public ProjectFormMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
	 * Converte o ProjectForm em um objeto Project, associando o cliente ao projeto.
	 * @param projectForm - ProjectForm
	 * @return Project (id, title, description, progress, status, projectUsers)
	 * */
    @Override
    public Project map(ProjectForm projectForm) {
        // Busca o cliente pelo ID fornecido no formulário
        User client = userRepository.findById(projectForm.clientId())
            .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com o ID: " + projectForm.clientId()));

        // Cria a relação ProjectUser para o cliente
        ProjectUser projectUser = new ProjectUser();
        projectUser.setClient(client);

        // Cria o projeto com o cliente associado
        Project project = new Project(
            null,
            projectForm.title(),
            projectForm.description(),
            projectForm.progress(),
            projectForm.status(),
            Set.of(projectUser)
        );

        // Associa o projeto ao ProjectUser
        projectUser.setProject(project);

        return project;
    }
}
