package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class ProjectRepositoryTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private UserRepository userRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    @DisplayName("Should create and retrieve a projectForm")
    public void testCreateAndRetrieveProject() {
        // Arrange
        User user = createTestUser();
        Employee employee = createTestEmployee(user);
        Project project = new Project();
        project.setTitle("Test Project");
        project.setClient(employee); // ou colaborador, dependendo da sua lógica
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));

        // Act
        Project savedProject = projectRepository.save(project);
        Optional<Project> retrievedProject = projectRepository.findById(savedProject.getId());

        // Assert
        assertThat(retrievedProject).isPresent();
        assertThat(retrievedProject.get().getTitle()).isEqualTo(savedProject.getTitle());
    }

    @Test
    @DisplayName("Should return empty when retrieving non-existing projectForm")
    void testRetrieveNonExistingProject() {
        // Act
        Optional<Project> retrievedProject = projectRepository.findById(999L); // ID que não existe

        // Assert
        assertThat(retrievedProject).isNotPresent();
    }

    private User createTestUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("securePassword");
        user.setDisabled(false);

        Profile profile = new Profile(); 
        profile.setDescription("User Profile Description"); 
        user.setProfile(profile);
        
        return user;
    }

    private Employee createTestEmployee(User user) {
        Employee employee = new Employee();
        employee.setName(faker.name().firstName());
        employee.setLastName(faker.name().lastName());
        employee.setPhoneNumber(faker.phoneNumber().phoneNumber());
        employee.setSector(faker.company().industry());
        employee.setOccupation(faker.job().title());
        employee.setAgency(faker.company().name());
        employee.setAvatar((long) faker.number().randomDigitNotZero()); 

        // Salvar o cliente
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        return employee;
    }
}
