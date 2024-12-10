package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.companies;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.companies.CompaniesBriefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.CompanyRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.ProjectRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.ProfileRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;
import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CompaniesBriefingRepositoryTest {

    @Autowired
    private CompaniesBriefingRepository companiesBriefingRepository;

    @Autowired
    private BriefingRepository briefingRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BriefingTypeRepository briefingTypeRepository;

    @Autowired
    private CompanyRepository companyRepository; 

    @Autowired
    private EmployeeRepository employeeRepository; 

    @Autowired 
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository; 

    private Briefing briefing;
    private Project project;
    private BriefingType briefingType;
    private Employee client;
    private Profile profile; 

    @BeforeEach
    void setUp() {
        profile = new Profile();
        profile.setDescription("Standard User Profile");
        profile = profileRepository.save(profile);

        User clientUser = new User();
        clientUser.setEmail("client@example.com");
        clientUser.setPassword("securePasswordClient");
        clientUser.setDisabled(false);
        clientUser.setProfile(profile);
        clientUser = userRepository.save(clientUser);

        client = new Employee();
        client.setName("Client Name");
        client.setLastName("Client LastName");
        client.setPhoneNumber("123456789");
        client.setSector("IT");
        client.setOccupation("Developer");
        client.setAgency("Agency 1");
        client.setRegistrationNumber("123456");
        client.setAvatar(1L);
        client.setUser(clientUser);
        client = employeeRepository.save(client);

        briefingType = new BriefingType();
        briefingType.setDescription("Briefing Tipo A");
        briefingType = briefingTypeRepository.save(briefingType);

        project = new Project();
        project.setTitle("Projeto Teste");
        project.setDisabled(false);
        project.setClient(client);
        project = projectRepository.save(project);

        briefing = new Briefing();
        briefing.setProject(project);
        briefing.setBriefingType(briefingType);
        briefing.setStartTime(LocalDate.now());
        briefing.setExpectedTime(LocalDate.now().plusDays(5));
        briefing.setDetailedDescription("Descrição detalhada do briefing");
        briefing = briefingRepository.save(briefing);
    }

    @Test
    void testSaveCompaniesBriefing() {
        Company company = new Company();
        company.setName("Company Name");
        companyRepository.save(company); 

        CompaniesBriefing companiesBriefing = new CompaniesBriefing();
        companiesBriefing.setCompany(company); 
        companiesBriefing.setBriefing(briefing); 
        
        CompaniesBriefing savedCompaniesBriefing = companiesBriefingRepository.save(companiesBriefing);
        assertThat(savedCompaniesBriefing).isNotNull();
        assertThat(savedCompaniesBriefing.getId()).isGreaterThan(0);
    }

    @Test
    void testFindByBriefing() {
        Company company = new Company();
        company.setName("Company Name");
        companyRepository.save(company); 

        CompaniesBriefing companiesBriefing = new CompaniesBriefing();
        companiesBriefing.setCompany(company); 
        companiesBriefing.setBriefing(briefing); 
        
        companiesBriefingRepository.save(companiesBriefing); 

        Optional<CompaniesBriefing> found = companiesBriefingRepository.findById(companiesBriefing.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getBriefing()).isEqualTo(briefing); 
    }
}