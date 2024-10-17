package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue; 
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.ProfileRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

public class BriefingRepositoryTest {

    @Mock
    private BriefingRepository briefingRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private BriefingTypeRepository briefingTypeRepository;

    @Mock
    private EmployeeRepository employeeRepository;

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

    @Test
    @DisplayName("Should return empty when searching for a briefing that does not exist")
    void testRetrieveNonExistingBriefing() {
        // Act
        when(briefingRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Briefing> retrievedBriefing = briefingRepository.findById(999L);
        
        // Assert
        assertThat(retrievedBriefing).isNotPresent();
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar briefing com campos nulos")
    void testCreateBriefingWithNullFields() {
        Briefing briefing = new Briefing();
        // Não definimos nenhum campo, deixando todos nulos

        Exception exception = assertThrows(Exception.class, () -> {
            briefingRepository.save(briefing);
            briefingRepository.flush(); // Força a validação imediata
        });

        assertThat(exception instanceof DataIntegrityViolationException || exception instanceof ConstraintViolationException)
                .isTrue();

    }  
    private Briefing createTestBriefing() {
        Employee client = createTestEmployee();

        Project project = new Project();
        project.setTitle("Projeto de Teste");
        project.setDisabled(false);
        project.setClient(client);
        
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        BriefingType briefingType = new BriefingType();
        briefingType.setDescription("Tipo de Briefing de Teste");
        
        when(briefingTypeRepository.save(any(BriefingType.class))).thenReturn(briefingType);

        Briefing briefing = new Briefing();
        briefing.setProject(project);
        briefing.setBriefingType(briefingType);
        briefing.setStartTime(LocalDate.now());
        briefing.setExpectedTime(LocalDate.now().plusDays(5));
        briefing.setDetailedDescription(faker.lorem().paragraph());
        briefing.setOtherCompany(faker.company().name());

        return briefing;
    }

    private Employee createTestEmployee() {
        Profile profile = new Profile();
        profile.setDescription("Perfil de Teste");
        
        when(profileRepository.save(any(Profile.class))).thenReturn(profile);

        User user = new User();
        user.setEmail(faker.internet().emailAddress());
        user.setPassword("senha123");
        user.setDisabled(false);
        user.setProfile(profile);
        
        when(userRepository.save(any(User.class))).thenReturn(user);

        Employee employee = new Employee();
        employee.setName("Funcionário de Teste");
        employee.setLastName("Sobrenome de Teste");
        employee.setPhoneNumber("123456789");
        employee.setSector("Setor de Teste");
        employee.setOccupation("Ocupação de Teste");
        employee.setAgency("Agência de Teste");
        employee.setAvatar(1L);
        employee.setUser(user);
        
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        return employee;
    }

    @Test
    @DisplayName("Must create and retrieve a brief successfully")
    void testCreateAndRetrieveBriefing() {
        Briefing briefing = createTestBriefing();
        when(briefingRepository.save(any(Briefing.class))).thenReturn(briefing);

        Briefing savedBriefing = briefingRepository.save(briefing);

        assertThat(savedBriefing.getId()).isNotNull();

        when(briefingRepository.findById(savedBriefing.getId())).thenReturn(Optional.of(savedBriefing));
        Optional<Briefing> retrievedBriefing = briefingRepository.findById(savedBriefing.getId());
        
        assertThat(retrievedBriefing).isPresent();
        assertThat(retrievedBriefing.get().getDetailedDescription()).isEqualTo(briefing.getDetailedDescription());
    }
}
