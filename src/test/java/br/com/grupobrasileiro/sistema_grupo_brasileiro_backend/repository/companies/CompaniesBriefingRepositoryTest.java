package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.companies;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
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
	private Employee collaborator;

	private Profile profile; 

	@BeforeEach
	void setUp() {
	    // Criação e persistência do perfil
	    profile = new Profile();
	    profile.setDescription("Standard User Profile");
	    profile = profileRepository.save(profile); // Salve o perfil e obtenha a instância persistida

	    // Criação e persistência de um usuário para o cliente
	    User clientUser = new User();
	    clientUser.setEmail("client@example.com");
	    clientUser.setPassword("securePasswordClient");
	    clientUser.setDisabled(false); // Preenchendo o campo 'disabled'
	    clientUser.setProfile(profile); // Associando o perfil ao usuário
	    clientUser = userRepository.save(clientUser); // Salve o usuário do cliente

	    // Criação e persistência do cliente
	    client = new Employee();
	    client.setName("Client Name");
	    client.setLastName("Client LastName");
	    client.setPhoneNumber("123456789");
	    client.setSector("IT");
	    client.setOccupation("Developer");
	    client.setAgency("Agency 1");
	    client.setAvatar(1L); // Preenchendo o campo avatar
	    client.setUser(clientUser); // Associando o usuário ao empregado
	    client = employeeRepository.save(client); // Salve e obtenha a instância persistida

	    // Criação e persistência de um usuário para o colaborador
	    User collaboratorUser = new User();
	    collaboratorUser.setEmail("collaborator@example.com");
	    collaboratorUser.setPassword("securePasswordCollaborator");
	    collaboratorUser.setDisabled(false); // Preenchendo o campo 'disabled'
	    collaboratorUser.setProfile(profile); // Associando o perfil ao usuário
	    collaboratorUser = userRepository.save(collaboratorUser); // Salve o usuário do colaborador

	    // Criação e persistência do colaborador
	    collaborator = new Employee();
	    collaborator.setName("Collaborator Name");
	    collaborator.setLastName("Collaborator LastName");
	    collaborator.setPhoneNumber("987654321");
	    collaborator.setSector("Development");
	    collaborator.setOccupation("Senior Developer");
	    collaborator.setAgency("Agency 2");
	    collaborator.setAvatar(2L); // Preenchendo o campo avatar
	    collaborator.setUser(collaboratorUser); // Associando o usuário ao empregado
	    collaborator = employeeRepository.save(collaborator); // Salve e obtenha a instância persistida

	    // Criação de um tipo de briefing
	    briefingType = new BriefingType();
	    briefingType.setDescription("Briefing Tipo A");
	    briefingType = briefingTypeRepository.save(briefingType); // Salve e obtenha a instância persistida

	    // Criação de um projeto
	    project = new Project();
	    project.setTitle("Projeto Teste");
	    project.setDisabled(false);
	    project.setClient(client);
	    project.setCollaborator(collaborator);
	    project = projectRepository.save(project); // Salve e obtenha a instância persistida

	    // Criação de um briefing
	    briefing = new Briefing();
	    briefing.setProject(project);
	    briefing.setBriefingType(briefingType);
	    briefing.setStartTime(LocalDateTime.now());
	    briefing.setExpectedTime(LocalDateTime.now().plusDays(5));
	    briefing.setDetailedDescription("Descrição detalhada do briefing");
	    briefing = briefingRepository.save(briefing); // Salve e obtenha a instância persistida
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
