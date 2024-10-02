package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

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
    @DisplayName("Should throw exception when creating briefing with null fields")
    void testCreateBriefingWithNullFields() {
        Briefing briefing = new Briefing();
        briefing.setProject(null);
        briefing.setBriefingType(null);
        briefing.setStartTime(null);
        briefing.setExpectedTime(null);
        briefing.setDetailedDescription(null);

        assertThrows(Exception.class, () -> {
            briefingRepository.save(briefing);
        });
    }

    private Briefing createTestBriefing() {
        Employee client = createTestEmployee();

        Project project = new Project();
        project.setTitle("Test Project");
        project.setDisabled(false);
        project.setClient(client);
        project = projectRepository.save(project);

        BriefingType briefingType = new BriefingType();
        briefingType.setId(1L);
        briefingType.setDescription("Test Briefing Type");
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
        Employee employee = new Employee();
        employee.setName("Test Employee");
        employee.setLastName("Test LastName");
        employee.setPhoneNumber("123456789");
        employee.setSector("Test Sector");
        employee.setOccupation("Test Occupation");
        employee.setAgency("Test Agency");
        employee.setAvatar(1L);
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