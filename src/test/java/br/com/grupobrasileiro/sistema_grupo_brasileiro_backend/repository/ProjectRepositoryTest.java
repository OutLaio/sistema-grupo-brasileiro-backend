package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.ProjectRepository;

@DataJpaTest
@ActiveProfiles("test") 
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void testSaveAndFindProject() {
        // Create a new Project entity
        Project project = new Project();
        project.setTitle("Test Project");
        project.setDescription("Description for Test Project");

        // Save the entity
        Project savedProject = projectRepository.save(project);

        // Fetch the project by ID
        Project foundProject = projectRepository.findById(savedProject.getId()).orElse(null);

        // Assertions
        assertThat(foundProject).isNotNull();
        assertThat(foundProject.getTitle()).isEqualTo("Test Project");
        assertThat(foundProject.getDescription()).isEqualTo("Description for Test Project");
    }

    @Test
    public void testFindByIdNotFound() {
        // Fetch a project by an ID that does not exist
        Project foundProject = projectRepository.findById(Long.MAX_VALUE).orElse(null);

        // Assertions
        assertThat(foundProject).isNull();
    }
}
