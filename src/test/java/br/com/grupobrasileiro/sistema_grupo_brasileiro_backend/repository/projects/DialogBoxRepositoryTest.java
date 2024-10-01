package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.DialogBox;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.ProfileRepository;
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

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class DialogBoxRepositoryTest {

    @Autowired
    private DialogBoxRepository dialogBoxRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BriefingRepository briefingRepository;

    @Autowired
    private UserRepository userRepository; // Injetando o UserRepository

    @Autowired
    private ProfileRepository profileRepository; // Injetando o ProfileRepository

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    private Profile createTestProfile() {
        Profile profile = new Profile();
        profile.setDescription("Test Profile Description"); // Defina uma descrição para o perfil
        return profileRepository.save(profile); // Salvar o perfil no repositório
    }

    private User createTestUser() {
        User user = new User();
        user.setEmail(faker.internet().emailAddress()); // Gera um email aleatório
        user.setPassword("password"); // Defina uma senha adequada
        user.setDisabled(false); // O usuário não está desativado
        user.setProfile(createTestProfile()); // Associe o perfil ao usuário
        return userRepository.save(user); // Salvar o usuário no repositório
    }

    private Employee createTestEmployee() {
        User user = createTestUser(); // Cria um usuário antes de criar o empregado

        Employee employee = new Employee();
        employee.setName(faker.name().firstName());
        employee.setLastName(faker.name().lastName());
        employee.setPhoneNumber(faker.phoneNumber().phoneNumber());
        employee.setSector("Development");
        employee.setOccupation("Developer");
        employee.setAgency("Agency XYZ");
        employee.setAvatar(1L); // Defina um valor apropriado para o avatar
        employee.setUser(user); // Associar o usuário ao empregado

        return employeeRepository.save(employee); // Salvar o empregado no repositório
    }

    private Briefing createTestBriefing() {
        Briefing briefing = new Briefing();
        briefing.setDetailedDescription(faker.lorem().sentence()); // Define a descrição detalhada
        return briefingRepository.save(briefing); // Salvar o briefing no repositório
    }

    

    @Test
    @DisplayName("Should return empty when retrieving non-existing dialog box")
    void testRetrieveNonExistingDialogBox() {
        // Act
        Optional<DialogBox> retrievedDialogBox = dialogBoxRepository.findById(999L); // ID que não existe

        // Assert
        assertThat(retrievedDialogBox).isNotPresent();
    }
}
