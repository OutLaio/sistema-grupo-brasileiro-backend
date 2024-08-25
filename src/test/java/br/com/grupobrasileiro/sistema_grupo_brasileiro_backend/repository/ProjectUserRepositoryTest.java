package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.ProjectUser;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.ProjectUserRepository;

@DataJpaTest
@ActiveProfiles("test") 
public class ProjectUserRepositoryTest {

    @Autowired
    private ProjectUserRepository projectUserRepository;

    private Project project;
    private User client;
    private User collaborator;
    private ProjectUser projectUser;

    @BeforeEach
    public void setUp() {
        // Create and save entities required for the tests
        project = new Project();
        project.setTitle("Test Project");
        project.setDescription("Description for Test Project");

        client = new User();
        client.setName("Client Name");

        collaborator = new User();
        collaborator.setName("Collaborator Name");

        projectUser = new ProjectUser(project, client);
        projectUserRepository.save(projectUser);
    }

    @Test
    public void testFindByProjectAndClientIsNotNull() {
        // Save a ProjectUser with a client
        ProjectUser savedProjectUser = projectUserRepository.save(projectUser);

        // Fetch the ProjectUser by project and non-null client
        Optional<ProjectUser> foundProjectUser = projectUserRepository.findByProjectAndClientIsNotNull(project);

        // Assertions
        assertThat(foundProjectUser).isPresent();
        assertThat(foundProjectUser.get().getClient()).isNotNull();
        assertThat(foundProjectUser.get().getProject()).isEqualTo(project);
    }

    @Test
    public void testExistsByProjectAndCollaborator() {
        // Save a ProjectUser with a collaborator
        ProjectUser savedProjectUser = new ProjectUser(project, client);
        savedProjectUser.setCollaborator(collaborator);
        projectUserRepository.save(savedProjectUser);

        // Check if the project has the specified collaborator
        boolean exists = projectUserRepository.existsByProjectAndCollaborator(project, collaborator);

        // Assertions
        assertThat(exists).isTrue();
    }

    @Test
    public void testExistsByProjectAndCollaboratorNotFound() {
        // Check if the project has a collaborator when none is assigned
        boolean exists = projectUserRepository.existsByProjectAndCollaborator(project, collaborator);

        // Assertions
        assertThat(exists).isFalse();
    }
}
