package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.OtherRoute;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
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
public class OtherRouteRepositoryTest {

    @Autowired
    private OtherRouteRepository otherRouteRepository;

    @Autowired
    private BAgencyBoardRepository bAgencyBoardRepository;

    @Autowired
    private AgencyBoardTypeRepository agencyBoardTypeRepository;

    @Autowired
    private BriefingRepository briefingRepository;

    @Autowired
    private BriefingTypeRepository briefingTypeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private ProfileRepository profileRepository; 

    @Autowired
    private UserRepository userRepository;

    private BAgencyBoard bAgencyBoard;

    @BeforeEach
    public void setUp() {
        Profile userProfile = findOrCreateProfile();
        User user1 = createUser("user1@example.com", "password1", userProfile);
        User user2 = createUser("user2@example.com", "password2", userProfile);

        Employee collaborator = createEmployee("Colaborador", "Um", "123456789", "Setor A", "Desenvolvedor", "Agência X", 1L, user1);
        Employee client = createEmployee("Cliente", "Um", "987654321", "Setor B", "Gerente", "Agência Y", 2L, user2);

        Project project = createProject(client);
        AgencyBoardType agencyBoardType = createAgencyBoardType();
        BriefingType briefingType = createBriefingType();
        Briefing briefing = createBriefing(project, briefingType);

        bAgencyBoard = createBAgencyBoard(agencyBoardType, briefing);
    }

    @Test
    @DisplayName("Must save and find an OtherRoute")
    public void testSaveAndFindOtherRoute() {
        OtherRoute otherRoute = createOtherRoute("Example Company", "Example City", "Example Type");
        OtherRoute savedOtherRoute = otherRouteRepository.save(otherRoute);

        assertNotNull(savedOtherRoute);
        assertNotNull(savedOtherRoute.getId());

        OtherRoute foundOtherRoute = otherRouteRepository.findById(savedOtherRoute.getId()).orElse(null);
        assertNotNull(foundOtherRoute);
        assertThat(foundOtherRoute.getCompany()).isEqualTo("Example Company");
    }

    @Test
    @DisplayName("Must delete an OtherRoute")
    public void testDeleteOtherRoute() {
        OtherRoute otherRoute = createOtherRoute("Example Company", "Example City", "Example Type");
        OtherRoute savedOtherRoute = otherRouteRepository.save(otherRoute);

        otherRouteRepository.delete(savedOtherRoute);

        Optional<OtherRoute> foundOtherRoute = otherRouteRepository.findById(savedOtherRoute.getId());
        assertThat(foundOtherRoute).isNotPresent();
    }

    @Test
    @DisplayName("Must update an OtherRoute")
    public void testUpdateOtherRoute() {
        OtherRoute otherRoute = createOtherRoute("Example Company", "Example City", "Example Type");
        OtherRoute savedOtherRoute = otherRouteRepository.save(otherRoute);
        
        assertThat(savedOtherRoute.getId()).isNotNull();
        assertThat(savedOtherRoute.getCompany()).isEqualTo("Example Company");
        assertThat(savedOtherRoute.getCity()).isEqualTo("Example City");

        savedOtherRoute.setCity("Nova Cidade");
        savedOtherRoute.setCompany("Nova Empresa");
        otherRouteRepository.save(savedOtherRoute);

        OtherRoute updatedOtherRoute = otherRouteRepository.findById(savedOtherRoute.getId())
                .orElseThrow(() -> new AssertionError("OtherRoute não encontrada após atualização"));

        assertThat(updatedOtherRoute.getCity()).isEqualTo("Nova Cidade");
        assertThat(updatedOtherRoute.getCompany()).isEqualTo("Nova Empresa");
        assertThat(updatedOtherRoute.getType()).isEqualTo("Example Type"); 
    }
    
    @Test
    @DisplayName("Must find all OtherRoutes")
    public void testFindAllOtherRoutes() {
        OtherRoute otherRoute1 = createOtherRoute("Example Company", "Example City", "Example Type");
        otherRouteRepository.save(otherRoute1);

        OtherRoute otherRoute2 = createOtherRoute("Outra Empresa", "Outra Cidade", "Outro Tipo");
        otherRouteRepository.save(otherRoute2);

        Iterable<OtherRoute> otherRoutes = otherRouteRepository.findAll();

        assertThat(otherRoutes).isNotEmpty();
        assertThat(otherRoutes).hasSize(2);
    }

    // Métodos auxiliares para criar entidades

    private Profile findOrCreateProfile() {
        Optional<Profile> existingProfile = profileRepository.findById(1L);
        if (existingProfile.isPresent()) {
            return existingProfile.get();
        } else {
            Profile newProfile = new Profile();
            newProfile.setDescription("Perfil de Teste");
            return profileRepository.save(newProfile);
        }
    }

    private User createUser(String email, String password, Profile profile) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setDisabled(false);
        user.setProfile(profile);
        return userRepository.save(user);
    }

    private Employee createEmployee(String name, String lastName, String phoneNumber, String sector, String occupation, String agency, Long avatar, User user) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setLastName(lastName);
        employee.setPhoneNumber(phoneNumber);
        employee.setSector(sector);
        employee.setOccupation(occupation);
        employee.setAgency(agency);
        employee.setAvatar(avatar);
        employee.setUser(user);
        return employeeRepository.save(employee);
    }

    private Project createProject(Employee client) {
        Project project = new Project();
        project.setClient(client);
        project.setTitle("Título do Projeto Exemplo");
        project.setDisabled(false);
        return projectRepository.save(project);
    }

    private AgencyBoardType createAgencyBoardType() {
        AgencyBoardType agencyBoardType = new AgencyBoardType();
        agencyBoardType.setDescription("Tipo Exemplo");
        return agencyBoardTypeRepository.save(agencyBoardType);
    }

    private BriefingType createBriefingType() {
        BriefingType briefingType = new BriefingType();
        briefingType.setDescription("Tipo de Briefing Exemplo");
        return briefingTypeRepository.save(briefingType);
    }

    private Briefing createBriefing(Project project, BriefingType briefingType) {
        Briefing briefing = new Briefing();
        briefing.setProject(project);
        briefing.setBriefingType(briefingType);
        briefing.setStartTime(LocalDate.now());
        briefing.setExpectedTime(LocalDate.now().plusDays(7));
        briefing.setDetailedDescription("Descrição detalhada do briefing");
        return briefingRepository.save(briefing);
    }

    private BAgencyBoard createBAgencyBoard(AgencyBoardType agencyBoardType, Briefing briefing) {
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        bAgencyBoard.setAgencyBoardType(agencyBoardType);
        bAgencyBoard.setBriefing(briefing);
        bAgencyBoard.setBoardLocation("Localização Exemplo");
        bAgencyBoard.setObservations("Observações Exemplo");
        return bAgencyBoardRepository.save(bAgencyBoard);
    }

    private OtherRoute createOtherRoute(String company, String city, String type) {
        OtherRoute otherRoute = new OtherRoute();
        otherRoute.setBAgencyBoard(bAgencyBoard);
        otherRoute.setCompany(company);
        otherRoute.setCity(city);
        otherRoute.setType(type);
        return otherRoute;
    }
}