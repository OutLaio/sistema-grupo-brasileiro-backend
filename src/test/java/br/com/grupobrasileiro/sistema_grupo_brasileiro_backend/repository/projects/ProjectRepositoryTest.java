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

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private UserRepository userRepository; 

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
    public void testCreateAndRetrieveEmployee() {
        // Criar um novo User
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("securePassword");
        user.setDisabled(false);
        
        // Aqui você precisa de um Profile válido. Certifique-se de que exista um Profile no seu banco de dados ou crie um temporariamente.
        Profile profile = new Profile(); // Crie um Profile válido, se necessário.
        profile.setDescription("User Profile Description"); // Ajuste conforme necessário
        user.setProfile(profile);
        
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

