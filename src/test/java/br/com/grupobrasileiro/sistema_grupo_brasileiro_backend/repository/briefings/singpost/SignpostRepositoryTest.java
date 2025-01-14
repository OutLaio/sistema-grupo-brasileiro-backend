package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.singpost;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.Material;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SignpostRepositoryTest {

    @Autowired
    private SignpostRepository signpostRepository;
    
    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private BriefingRepository briefingRepository;

    @Autowired
    private BriefingTypeRepository briefingTypeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository; 

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private BSignpost signpost;
    private Briefing briefing; 
    private BriefingType briefingType; 
    private Project project; 

    @BeforeEach
    void setUp() {
        Profile profile = new Profile();
        profile.setDescription("Descrição do perfil de teste");
        profile = profileRepository.save(profile);

        User user = new User();
        user.setEmail("testuser@example.com");
        user.setPassword("password");
        user.setDisabled(false);
        user.setProfile(profile);
        user = userRepository.save(user);

        Employee employee = new Employee();
        employee.setName("Funcionário de Teste");
        employee.setLastName("Sobrenome");
        employee.setPhoneNumber("123456789");
        employee.setSector("Setor de Teste");
        employee.setOccupation("Ocupação de Teste");
        employee.setAgency("Agência de Teste");
        employee.setRegistrationNumber("123456");
        employee.setAvatar(1L);
        employee.setUser(user);
        employee = employeeRepository.save(employee);

        Material material = new Material();
        material.setDescription("Material de Teste"); 
        material = materialRepository.save(material);

        briefingType = new BriefingType();
        briefingType.setDescription("Tipo de Briefing de Teste");
        briefingType = briefingTypeRepository.save(briefingType);

        project = new Project();
        project.setTitle("Projeto de Teste");
        project.setClient(employee);
        project.setDisabled(false);
        project = projectRepository.save(project);

        briefing = new Briefing();
        briefing.setStartTime(LocalDate.now());
        briefing.setExpectedTime(LocalDate.now().plusDays(7));
        briefing.setDetailedDescription("Descrição detalhada de teste");
        briefing.setBriefingType(briefingType);
        briefing.setProject(project);
        briefing = briefingRepository.save(briefing);

        signpost = new BSignpost();
        signpost.setBoardLocation("Locação de Teste");
        signpost.setSector("Setor de Teste");
        signpost.setMaterial(material);
        signpost.setBriefing(briefing);
        
        // Não salve o signpost aqui
    }

    @Test
    @DisplayName("Must save and retrieve Signpost correctly")
    void testSaveAndRetrieveSignpost() {
        BSignpost savedSignpost = signpostRepository.save(signpost);

        Optional<BSignpost> foundSignpost = signpostRepository.findById(savedSignpost.getId());
        assertThat(foundSignpost).isPresent();
        assertThat(foundSignpost.get().getBoardLocation()).isEqualTo("Locação de Teste");
        assertThat(foundSignpost.get().getMaterial()).isNotNull();
        assertThat(foundSignpost.get().getBriefing()).isNotNull();
    }

    @Test
    @DisplayName("Must delete Signpost correctly")
    void testDeleteSignpost() {
        BSignpost savedSignpost = signpostRepository.save(signpost);
        signpostRepository.delete(savedSignpost);

        Optional<BSignpost> foundSignpost = signpostRepository.findById(savedSignpost.getId());
        assertThat(foundSignpost).isNotPresent();
    }

    @Test
    @DisplayName("Must update Signpost correctly")
    void testUpdateSignpost() {
        BSignpost savedSignpost = signpostRepository.save(signpost);
        savedSignpost.setBoardLocation("Nova Locação de Teste");
        BSignpost updatedSignpost = signpostRepository.save(savedSignpost);

        Optional<BSignpost> foundSignpost = signpostRepository.findById(updatedSignpost.getId());
        assertThat(foundSignpost).isPresent();
        assertThat(foundSignpost.get().getBoardLocation()).isEqualTo("Nova Locação de Teste");
    }
}