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
        Employee client = createTestEmployee();

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

    private Employee createTestEmployee() {
        Employee employee = new Employee();
        employee.setName(faker.name().firstName());
        employee.setLastName(faker.name().lastName());
        employee.setPhoneNumber(faker.phoneNumber().phoneNumber());
        employee.setSector(faker.company().industry());
        employee.setOccupation(faker.job().title());
        employee.setAgency(faker.company().name());
        employee.setAvatar((long) faker.number().randomDigitNotZero()); // Correção aqui

        // Salvar o cliente
        return employeeRepository.save(employee);
    }
}

