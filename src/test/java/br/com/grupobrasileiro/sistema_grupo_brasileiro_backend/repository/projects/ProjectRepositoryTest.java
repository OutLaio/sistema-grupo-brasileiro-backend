package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    /**
     * Testa a criação e recuperação de um projeto.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should create and retrieve a project")
    void testCreateAndRetrieveProject() {
        // Arrange
        Employee client = new Employee();
        client.setName(faker.name().firstName());
        client.setLastName(faker.name().lastName());
        client.setPhoneNumber(faker.phoneNumber().phoneNumber());
        client.setSector(faker.company().industry());
        client.setOccupation(faker.job().title());
        client.setAgency(faker.company().name());
        client.setAvatar((long) faker.number().randomDigitNotZero()); // Correção aqui
        // Salvar o cliente
        employeeRepository.save(client);

        Project project = new Project();
        project.setClient(client);
        project.setTitle(faker.lorem().sentence());
        project.setStatus("Active");
        project.setDisabled(false);

        // Act
        projectRepository.save(project);
        Optional<Project> retrievedProject = projectRepository.findById(project.getId());

        // Assert
        assertThat(retrievedProject).isPresent();
        assertThat(retrievedProject.get().getTitle()).isEqualTo(project.getTitle());
        assertThat(retrievedProject.get().getClient()).isEqualTo(client);
    }

    /**
     * Testa a recuperação de um projeto que não existe.
     */
    @Test
    @DisplayName("Should return empty when retrieving non-existing project")
    void testRetrieveNonExistingProject() {
        // Act
        Optional<Project> retrievedProject = projectRepository.findById(999L); // ID que não existe

        // Assert
        assertThat(retrievedProject).isNotPresent();
    }
}
