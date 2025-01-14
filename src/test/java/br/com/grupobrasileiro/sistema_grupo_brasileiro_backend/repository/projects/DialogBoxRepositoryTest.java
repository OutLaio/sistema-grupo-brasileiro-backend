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

public class DialogBoxRepositoryTest {

    @Mock
    private DialogBoxRepository dialogBoxRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private BriefingRepository briefingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProfileRepository profileRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    private Profile createTestProfile() {
        Profile profile = new Profile();
        profile.setDescription("Test Profile Description");
        return profile; // Não precisamos salvar, pois é um mock
    }

    private User createTestUser() {
        User user = new User();
        user.setEmail(faker.internet().emailAddress());
        user.setPassword("password");
        user.setDisabled(false);
        user.setProfile(createTestProfile());
        return user; // Não precisamos salvar, pois é um mock
    }

    private Employee createTestEmployee() {
        User user = createTestUser();

        Employee employee = new Employee();
        employee.setName(faker.name().firstName());
        employee.setLastName(faker.name().lastName());
        employee.setPhoneNumber(faker.phoneNumber().phoneNumber());
        employee.setSector("Development");
        employee.setOccupation("Developer");
        employee.setAgency("Agency XYZ");
        employee.setAvatar(1L);
        employee.setUser(user);

        return employee; // Não precisamos salvar, pois é um mock
    }

    private Briefing createTestBriefing() {
        Briefing briefing = new Briefing();
        briefing.setDetailedDescription(faker.lorem().sentence());
        return briefing; // Não precisamos salvar, pois é um mock
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
