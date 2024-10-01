package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.singpost;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
        // Criar e salvar um Profile fictício
        Profile profile = new Profile();
        profile.setDescription("Descrição do perfil de teste");
        profile = profileRepository.save(profile); // Salvar o Profile

        // Criar e salvar um User fictício
        User user = new User();
        user.setEmail("testuser@example.com");
        user.setPassword("password");
        user.setDisabled(false);
        user.setProfile(profile); // Associar o Profile ao User
        user = userRepository.save(user); // Salvar o User

        // Criar e salvar um Employee fictício
        Employee employee = new Employee();
        employee.setName("Funcionário de Teste");
        employee.setLastName("Sobrenome");
        employee.setPhoneNumber("123456789");
        employee.setSector("Setor de Teste");
        employee.setOccupation("Ocupação de Teste");
        employee.setAgency("Agência de Teste");
        employee.setAvatar(1L);
        employee.setUser(user); // Associar o User ao Employee
        employee = employeeRepository.save(employee); // Salvar o Employee 

        // Criar e salvar um Material fictício
        Material material = new Material();
        material.setDescription("Material de Teste"); 
        material = materialRepository.save(material); // Salvar o Material

        // Criar e salvar um BriefingType fictício
        briefingType = new BriefingType(); // Usar a variável de classe
        briefingType.setDescription("Tipo de Briefing de Teste");
        briefingType = briefingTypeRepository.save(briefingType); // Salvar o BriefingType

        // Criar e salvar um Project fictício
        project = new Project(); // Usar a variável de classe
        project.setTitle("Projeto de Teste");
        project.setClient(employee); // Associar o Employee ao Project
        project.setDisabled(false);
        project = projectRepository.save(project); // Salvar o Project

        // Criar e salvar um Briefing fictício
        briefing = new Briefing(); // Usar a variável de classe
        briefing.setStartTime(LocalDateTime.now());
        briefing.setExpectedTime(LocalDateTime.now().plusDays(7));
        briefing.setDetailedDescription("Descrição detalhada de teste");
        briefing.setBriefingType(briefingType); // Associar o BriefingType
        briefing.setProject(project); // Associar o Project
        briefing = briefingRepository.save(briefing); // Salvar o Briefing

        // Criar o BSignpost e associá-lo ao Material e Briefing
        signpost = new BSignpost(); // Usar a variável de classe
        signpost.setBoardLocation("Locação de Teste");
        signpost.setSector("Setor de Teste");
        signpost.setMaterial(material); // Associa o material persistido
        signpost.setBriefing(briefing); // Associa o briefing persistido
        
        // Salvar o BSignpost
        signpost = signpostRepository.save(signpost); // Salvar o BSignpost

        System.out.println("Signpost antes de salvar: " + signpost);
        System.out.println("Material: " + signpost.getMaterial());
        System.out.println("Briefing: " + signpost.getBriefing());
    } 

    @Test
    @DisplayName("Should delete Signpost correctly")
    void testDeleteSignpost() {
        // Act
        BSignpost savedSignpost = signpostRepository.save(signpost);
        signpostRepository.delete(savedSignpost);

        // Assert
        Optional<BSignpost> foundSignpost = signpostRepository.findById(savedSignpost.getId());
        assertThat(foundSignpost).isNotPresent();
    }

    @Test
    @DisplayName("Should update Signpost correctly")
    void testUpdateSignpost() {
        // Act
        BSignpost savedSignpost = signpostRepository.save(signpost);
        savedSignpost.setBoardLocation("Nova Locação de Teste");
        BSignpost updatedSignpost = signpostRepository.save(savedSignpost);

        // Assert
        Optional<BSignpost> foundSignpost = signpostRepository.findById(updatedSignpost.getId());
        assertThat(foundSignpost).isPresent();
        assertThat(foundSignpost.get().getBoardLocation()).isEqualTo("Nova Locação de Teste");
    }
}
