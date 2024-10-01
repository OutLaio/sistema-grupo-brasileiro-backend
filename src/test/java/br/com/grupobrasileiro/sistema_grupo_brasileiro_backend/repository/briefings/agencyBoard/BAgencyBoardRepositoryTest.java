package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BoardType;
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
public class BAgencyBoardRepositoryTest {

    @Autowired
    private BAgencyBoardRepository bAgencyBoardRepository;

    @Autowired
    private AgencyBoardTypeRepository agencyBoardTypeRepository;

    @Autowired
    private BoardTypeRepository boardTypeRepository;

    @Autowired
    private BriefingRepository briefingRepository;

    @Autowired
    private BriefingTypeRepository briefingTypeRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProfileRepository profileRepository;
    
    @Autowired
    private ProjectRepository projectRepository;

    // Objetos mock
    private AgencyBoardType agencyBoardType;
    private BoardType boardType;
    private Briefing briefing;
    private BriefingType briefingType;

    @BeforeEach
    void setUp() {
        // Criar e salvar um novo perfil
        Profile profile = new Profile();
        profile.setDescription("Perfil de Exemplo");
        profile = profileRepository.save(profile); // Salvar o perfil de exemplo

        // Verificar se o usuário já existe
        Optional<User> existingUserOpt = userRepository.findByEmail("usuario@example.com");
        User user;

        // Usar o usuário existente ou criar um novo
        if (existingUserOpt.isPresent()) {
            user = existingUserOpt.get();
        } else {
            // Criar e salvar um novo usuário de teste
            user = new User();
            user.setProfile(profile); // Usar o perfil recém-criado
            user.setEmail("usuario@example.com");
            user.setPassword("senha");
            user.setDisabled(false);
            user = userRepository.save(user); // Salvar o novo usuário
        }

        // Criar e salvar um colaborador de teste
        Employee collaborator = new Employee();
        collaborator.setName("Colaborador de Teste");
        collaborator.setLastName("Sobrenome do Colaborador");
        collaborator.setPhoneNumber("123456789");
        collaborator.setSector("Setor do Colaborador");
        collaborator.setOccupation("Ocupação do Colaborador");
        collaborator.setAgency("Agência do Colaborador");
        collaborator.setAvatar(1L);
        collaborator.setUser(user); // Associar o usuário ao colaborador
        employeeRepository.save(collaborator);
        
        // Criar e salvar um cliente de teste
        Employee client = new Employee();
        client.setName("Cliente de Teste");
        client.setLastName("Sobrenome do Cliente");
        client.setPhoneNumber("987654321");
        client.setSector("Setor do Cliente");
        client.setOccupation("Ocupação do Cliente");
        client.setAgency("Agência do Cliente");
        client.setAvatar(2L);
        client.setUser(user); // Também associar o mesmo usuário
        employeeRepository.save(client);
        
        // Criar e salvar um projeto de teste
        Project project = new Project();
        project.setTitle("Projeto de Teste"); 
        project.setDisabled(false); 
        project.setClient(client); 
        project.setCollaborator(collaborator); 
        project = projectRepository.save(project); 

        // Criar e salvar um briefing associando ao projeto
        Briefing briefing = new Briefing();
        briefing.setProject(project); 
        briefing.setBriefingType(briefingType); 
        briefing.setStartTime(LocalDateTime.now());
        briefing.setExpectedTime(LocalDateTime.now().plusDays(7));
        briefing.setDetailedDescription("Descrição detalhada do briefing");
        briefingRepository.save(briefing);

        // Criar e salvar um empregado de teste
        Employee employee = new Employee();
        employee.setAgency("Agência Teste");
        employee.setAvatar(3L);
        employee.setLastName("Sobrenome Teste");
        employee.setName("Nome Teste");
        employee.setOccupation("Ocupação Teste");
        employee.setPhoneNumber("123456789");
        employee.setSector("Setor Teste");
        employee.setUser(user); // Associar o usuário ao empregado
        employeeRepository.save(employee);

        // Inicializar AgencyBoardType e BoardType
        agencyBoardType = new AgencyBoardType();
        agencyBoardType.setDescription("Tipo de Quadro de Agência");
        agencyBoardTypeRepository.save(agencyBoardType);

        boardType = new BoardType();
        boardType.setDescription("Tipo de Quadro");
        boardTypeRepository.save(boardType);

        // Inicializar BriefingType e Briefing
        briefingType = new BriefingType();
        briefingType.setDescription("Tipo de Briefing");
        briefingTypeRepository.save(briefingType);

        briefing = new Briefing();
        briefing.setProject(null); 
        briefing.setBriefingType(briefingType);
        briefing.setStartTime(LocalDateTime.now());
        briefing.setExpectedTime(LocalDateTime.now().plusDays(7));
        briefing.setDetailedDescription("Descrição detalhada do briefing");
        briefingRepository.save(briefing);
    }


    @Test
    void testInsertEmployee() {
        // Verifica se um empregado foi salvo corretamente
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).isNotEmpty();
        assertThat(employees.get(0).getName()).isEqualTo("Nome Teste");
    }

    @Test
    @Rollback(false)
    @DisplayName("Should create and retrieve a BAgencyBoard")
    void testCreateAndRetrieveBAgencyBoard() {
        // Arrange
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        bAgencyBoard.setAgencyBoardType(agencyBoardType);
        bAgencyBoard.setBoardType(boardType);
        bAgencyBoard.setBriefing(briefing);
        bAgencyBoard.setBoardLocation("Localização do Quadro");
        bAgencyBoard.setObservations("Observações do Quadro");

        // Act
        BAgencyBoard savedBAgencyBoard = bAgencyBoardRepository.save(bAgencyBoard);
        BAgencyBoard retrievedBAgencyBoard = bAgencyBoardRepository.findById(savedBAgencyBoard.getId()).orElse(null);

        // Assert
        assertThat(retrievedBAgencyBoard).isNotNull();
        assertThat(retrievedBAgencyBoard.getId()).isEqualTo(savedBAgencyBoard.getId());
        assertThat(retrievedBAgencyBoard.getBoardLocation()).isEqualTo("Localização do Quadro");
        assertThat(retrievedBAgencyBoard.getObservations()).isEqualTo("Observações do Quadro");
    }

    @Test
    @Rollback(false)
    @DisplayName("Should delete a BAgencyBoard")
    void testDeleteBAgencyBoard() {
        // Arrange
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        bAgencyBoard.setAgencyBoardType(agencyBoardType);
        bAgencyBoard.setBoardType(boardType);
        bAgencyBoard.setBriefing(briefing);
        bAgencyBoard.setBoardLocation("Localização do Quadro");
        bAgencyBoard.setObservations("Observações do Quadro");

        // Salvando o BAgencyBoard
        BAgencyBoard savedBAgencyBoard = bAgencyBoardRepository.save(bAgencyBoard);

        // Act
        bAgencyBoardRepository.deleteById(savedBAgencyBoard.getId());
        BAgencyBoard retrievedBAgencyBoard = bAgencyBoardRepository.findById(savedBAgencyBoard.getId()).orElse(null);

        // Assert
        assertThat(retrievedBAgencyBoard).isNull();
    }

    @Test
    @Rollback(false)
    @DisplayName("Should update a BAgencyBoard")
    void testUpdateBAgencyBoard() {
        // Arrange
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        bAgencyBoard.setAgencyBoardType(agencyBoardType);
        bAgencyBoard.setBoardType(boardType);
        bAgencyBoard.setBriefing(briefing);
        bAgencyBoard.setBoardLocation("Localização do Quadro");
        bAgencyBoard.setObservations("Observações do Quadro");

        // Salvando o BAgencyBoard
        BAgencyBoard savedBAgencyBoard = bAgencyBoardRepository.save(bAgencyBoard);

        // Act: Atualizando o BAgencyBoard
        savedBAgencyBoard.setBoardLocation("Localização Atualizada");
        savedBAgencyBoard.setObservations("Observações Atualizadas");
        bAgencyBoardRepository.save(savedBAgencyBoard);

        // Assert: Recuperando o BAgencyBoard atualizado
        BAgencyBoard updatedBAgencyBoard = bAgencyBoardRepository.findById(savedBAgencyBoard.getId()).orElse(null);
        assertThat(updatedBAgencyBoard).isNotNull();
        assertThat(updatedBAgencyBoard.getBoardLocation()).isEqualTo("Localização Atualizada");
        assertThat(updatedBAgencyBoard.getObservations()).isEqualTo("Observações Atualizadas");
    }

    @Test
    @Rollback(false)
    @DisplayName("Should retrieve all BAgencyBoards")
    void testFindAllBAgencyBoards() {
        // Arrange
        BAgencyBoard bAgencyBoard1 = new BAgencyBoard();
        bAgencyBoard1.setAgencyBoardType(agencyBoardType);
        bAgencyBoard1.setBoardType(boardType);
        bAgencyBoard1.setBriefing(briefing);
        bAgencyBoard1.setBoardLocation("Localização 1");
        bAgencyBoard1.setObservations("Observações 1");
        bAgencyBoardRepository.save(bAgencyBoard1);

        BAgencyBoard bAgencyBoard2 = new BAgencyBoard();
        bAgencyBoard2.setAgencyBoardType(agencyBoardType);
        bAgencyBoard2.setBoardType(boardType);
        bAgencyBoard2.setBriefing(briefing);
        bAgencyBoard2.setBoardLocation("Localização 2");
        bAgencyBoard2.setObservations("Observações 2");
        bAgencyBoardRepository.save(bAgencyBoard2);

        // Act
        List<BAgencyBoard> allBAgencyBoards = bAgencyBoardRepository.findAll();

        // Assert
        assertThat(allBAgencyBoards).hasSize(2); // Deve encontrar 2 BAgencyBoards
    }

    @Test
    @Rollback(false)
    @DisplayName("Should return null for a non-existent BAgencyBoard")
    void testFindNonExistentBAgencyBoard() {
        // Act
        BAgencyBoard retrievedBAgencyBoard = bAgencyBoardRepository.findById(999L).orElse(null); // ID que não existe

        // Assert
        assertThat(retrievedBAgencyBoard).isNull(); 
    }
}
