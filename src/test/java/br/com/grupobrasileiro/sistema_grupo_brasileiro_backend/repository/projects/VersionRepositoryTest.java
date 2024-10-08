package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects;

import com.github.javafaker.Faker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Version;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.ProfileRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class VersionRepositoryTest {

    @Autowired
    private VersionRepository versionRepository;

    @Autowired
    private BriefingRepository briefingRepository;

    @Autowired
    private BriefingTypeRepository briefingTypeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;
    
    @Autowired
    private EntityManager entityManager;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    @Test
    @DisplayName("Should return 0 when no version is associated with the briefingg")
    void testCountVersionsByNonExistingBriefingId() {
        long count = versionRepository.countVersionsByBriefingId(999L);
        assertThat(count).isEqualTo(0);
    }

    private Briefing createTestBriefing() {
        Project project = createOrFetchProject();
        BriefingType briefingType = createOrFetchBriefingType();

        Briefing briefing = new Briefing();
        briefing.setProject(project);
        briefing.setBriefingType(briefingType);
        briefing.setStartTime(LocalDate.now());
        briefing.setExpectedTime(LocalDate.now().plusDays(5));
        briefing.setDetailedDescription(faker.lorem().sentence());

        return briefingRepository.save(briefing);
    }

    private BriefingType createOrFetchBriefingType() {
        BriefingType briefingType = new BriefingType();
        briefingType.setDescription("Tipo de Briefing Teste");
        return briefingTypeRepository.save(briefingType);
    }

    private Project createOrFetchProject() {
        Employee client = createTestClient();

        Project project = new Project();
        project.setTitle("Projeto Teste");
        project.setDisabled(false);
        project.setClient(client);
        return projectRepository.save(project);
    }

    private Employee createTestClient() {
        Profile profile = new Profile();
        profile.setDescription("Perfil de Teste");
        profile = profileRepository.save(profile);

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setDisabled(false);
        user.setProfile(profile);
        user = userRepository.save(user);

        Employee client = new Employee();
        client.setName("Cliente Teste");
        client.setLastName("Sobrenome Teste");
        client.setPhoneNumber("123456789");
        client.setSector("Setor Teste");
        client.setOccupation("Ocupação Teste");
        client.setAgency("Agência Teste");
        client.setAvatar(1L);
        client.setUser(user);
        return employeeRepository.save(client);
    }

    private Version saveTestVersion(Briefing briefing, int versionNumber, String feedback, boolean clientApprove, boolean supervisorApprove) {
        Version version = new Version();
        version.setBriefing(briefing);
        version.setNumVersion(versionNumber);
        version.setProductLink(faker.internet().url());
        version.setClientApprove(clientApprove);
        version.setSupervisorApprove(supervisorApprove);
        version.setFeedback(feedback);
        return versionRepository.save(version);
    }

    @Test
    @DisplayName("You must create and count versions of a brief correctly")
    void testCreateAndCountVersions() {
        Briefing briefing = createTestBriefing();
        
        saveTestVersion(briefing, 1, "Feedback 1", false, false);
        saveTestVersion(briefing, 2, "Feedback 2", true, false);
        saveTestVersion(briefing, 3, "Feedback 3", true, true);

        long count = versionRepository.countVersionsByBriefingId(briefing.getId());
        assertThat(count).isEqualTo(3);
    }
}