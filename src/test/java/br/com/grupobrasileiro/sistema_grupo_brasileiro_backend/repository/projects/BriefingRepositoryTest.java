package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects;

import static org.assertj.core.api.Assertions.assertThat;
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

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class BriefingRepositoryTest {

    @Autowired
    private BriefingRepository briefingRepository;
    
    @Autowired
    private ProjectRepository projectRepository; 

    @Autowired
    private BriefingTypeRepository briefingTypeRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository; 
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProfileRepository profileRepository; 

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    @Test
    @DisplayName("Should return empty when searching for a briefing that does not exist")
    void testRetrieveNonExistingBriefing() {
        Optional<Briefing> retrievedBriefing = briefingRepository.findById(999L);
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

        // Imprima a exceção para debug
        exception.printStackTrace();

        // Verifique se a exceção é do tipo esperado
        assertTrue(exception instanceof DataIntegrityViolationException || exception instanceof ConstraintViolationException,
                "Esperava-se uma DataIntegrityViolationException ou ConstraintViolationException, mas foi lançada: " + exception.getClass().getName());

        // Se for uma DataIntegrityViolationException, você pode verificar a causa raiz
        if (exception instanceof DataIntegrityViolationException) {
            DataIntegrityViolationException dive = (DataIntegrityViolationException) exception;
            System.out.println("Mensagem de erro: " + dive.getMostSpecificCause().getMessage());
        }

        // Se for uma ConstraintViolationException, você pode verificar as violações específicas
        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException cve = (ConstraintViolationException) exception;
            Set<ConstraintViolation<?>> violations = cve.getConstraintViolations();
            for (ConstraintViolation<?> violation : violations) {
                System.out.println("Violação: " + violation.getPropertyPath() + " " + violation.getMessage());
            }
        }
    }
    
    private Briefing createTestBriefing() {
        Employee client = createTestEmployee();

        Project project = new Project();
        project.setTitle("Projeto de Teste");
        project.setDisabled(false);
        project.setClient(client);
        project = projectRepository.save(project);

        BriefingType briefingType = new BriefingType();
        briefingType.setDescription("Tipo de Briefing de Teste");
        briefingType = briefingTypeRepository.save(briefingType);

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
        // Criar um Profile
        Profile profile = new Profile();
        profile.setDescription("Perfil de Teste");
        profile = profileRepository.save(profile);

        // Criar um User
        User user = new User();
        user.setEmail(faker.internet().emailAddress());
        user.setPassword("senha123");
        user.setDisabled(false);
        user.setProfile(profile);
        user = userRepository.save(user);

        // Criar um Employee
        Employee employee = new Employee();
        employee.setName("Funcionário de Teste");
        employee.setLastName("Sobrenome de Teste");
        employee.setPhoneNumber("123456789");
        employee.setSector("Setor de Teste");
        employee.setOccupation("Ocupação de Teste");
        employee.setAgency("Agência de Teste");
        employee.setAvatar(1L);
        employee.setUser(user);

        return employeeRepository.save(employee);
    }

    @Test
    @DisplayName("Must create and retrieve a brief successfully")
    void testCreateAndRetrieveBriefing() {
        Briefing briefing = createTestBriefing();
        Briefing savedBriefing = briefingRepository.save(briefing);

        assertThat(savedBriefing.getId()).isNotNull();

        Optional<Briefing> retrievedBriefing = briefingRepository.findById(savedBriefing.getId());
        assertThat(retrievedBriefing).isPresent();
        assertThat(retrievedBriefing.get().getDetailedDescription()).isEqualTo(briefing.getDetailedDescription());
    }
    
    
}