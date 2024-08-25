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
import jakarta.transaction.Transactional;

@DataJpaTest
@ActiveProfiles("test")
@Transactional
public class ProjectUserRepositoryTest {

    @Autowired
    private ProjectUserRepository projectUserRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

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
        project = projectRepository.save(project); // Save the project using ProjectRepository

        client = new User();
        client.setName("Client Name");
        client = userRepository.save(client); // Save the client using UserRepository

        collaborator = new User();
        collaborator.setName("Collaborator Name");
        collaborator = userRepository.save(collaborator); // Save the collaborator using UserRepository

        projectUser = new ProjectUser(project, client);
        projectUser = projectUserRepository.save(projectUser); // Save the ProjectUser using ProjectUserRepository
    }

    @Test
    public void testFindByProjectAndClientIsNotNull() {
        // Act
        Optional<ProjectUser> foundProjectUser = projectUserRepository.findByProjectAndClientIsNotNull(project);

        // Assert
        assertThat(foundProjectUser).isPresent();
        assertThat(foundProjectUser.get().getClient()).isNotNull();
        assertThat(foundProjectUser.get().getProject()).isEqualTo(project);
    }

    @Test
    public void testExistsByProjectAndCollaborator() {
        // Arrange
        ProjectUser savedProjectUser = new ProjectUser(project, client);
        savedProjectUser.setCollaborator(collaborator);
        projectUserRepository.save(savedProjectUser);

        // Act
        boolean exists = projectUserRepository.existsByProjectAndCollaborator(project, collaborator);

        // Assert
        assertThat(exists).isTrue();
    }

    @Test
    public void testExistsByProjectAndCollaboratorNotFound() {
        // Act
        boolean exists = projectUserRepository.existsByProjectAndCollaborator(project, collaborator);

        // Assert
        assertThat(exists).isFalse();
    }

    @Test
    public void testFindByProjectAndClientIsNotNullWhenNoMatch() {
        // Arrange
        Project anotherProject = new Project();
        anotherProject.setTitle("Another Project");
        anotherProject.setDescription("Description for Another Project");
        anotherProject = projectRepository.save(anotherProject); // Save the new project using ProjectRepository

        // Act
        Optional<ProjectUser> foundProjectUser = projectUserRepository.findByProjectAndClientIsNotNull(anotherProject);

        // Assert
        assertThat(foundProjectUser).isEmpty();
    }
}
