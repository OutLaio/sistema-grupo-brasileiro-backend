package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.OtherRoute;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.ProjectRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
import jakarta.transaction.Transactional;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Use a configuração do seu banco de dados real
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


    private BAgencyBoard bAgencyBoard;

    @BeforeEach
    public void setUp() {

    	    // Criação de um colaborador
    	    Employee collaborator = new Employee();
    	    collaborator.setName("Colaborador 1");
    	    // Salve o colaborador para que ele tenha um ID
    	    collaborator = employeeRepository.save(collaborator);

    	    // Criação de um cliente (que é também um Employee no seu caso)
    	    Employee client = new Employee();
    	    client.setName("Cliente 1");
    	    // Salve o cliente para que ele tenha um ID
    	    client = employeeRepository.save(client);

    	    // Criação de um projeto
    	    Project project = new Project();
    	    project.setClient(client); // Aqui garantimos que estamos passando um cliente válido
    	    project.setCollaborator(collaborator); // Atribuindo um colaborador
    	    project.setTitle("Project Title Example");
    	    project.setStatus("Em andamento");
    	    project.setDisabled(false); // Definindo se o projeto está desativado

    	    // Salve o projeto
    	    projectRepository.save(project);
    	    
        // Inicialize o AgencyBoardType
        AgencyBoardType agencyBoardType = new AgencyBoardType();
        agencyBoardType.setDescription("Type Example");
        agencyBoardType = agencyBoardTypeRepository.save(agencyBoardType);

        // Inicialize o BriefingType
        BriefingType briefingType = new BriefingType();
        briefingType.setDescription("Briefing Type Example");
        briefingType = briefingTypeRepository.save(briefingType);

                
        // Inicialize o Briefing
        Briefing briefing = new Briefing();
        briefing.setProject(project);
        briefing.setBriefingType(briefingType);
        briefing.setStartTime(LocalDateTime.now());
        briefing.setExpectedTime(LocalDateTime.now().plusDays(7));
        briefing.setDetailedDescription("Descrição detalhada do briefing");
        briefing = briefingRepository.save(briefing); // Salva o briefing

        // Inicialize o BAgencyBoard
        bAgencyBoard = new BAgencyBoard();
        bAgencyBoard.setAgencyBoardType(agencyBoardType);
        bAgencyBoard.setBriefing(briefing);
        bAgencyBoard.setBoardLocation("Location Example");
        bAgencyBoard.setObservations("Observations Example");
        bAgencyBoard = bAgencyBoardRepository.save(bAgencyBoard);
    }

    @Test
    @DisplayName("Should save and find an OtherRoute")
    public void testSaveAndFindOtherRoute() {
        // Inicialize o OtherRoute
        OtherRoute otherRoute = new OtherRoute();
        otherRoute.setBAgencyBoard(bAgencyBoard); // Associar o BAgencyBoard
        otherRoute.setCompany("Example Company");
        otherRoute.setCity("Example City");
        otherRoute.setType("Example Type");

        // Salvar a entidade OtherRoute
        OtherRoute savedOtherRoute = otherRouteRepository.save(otherRoute);

        // Verifique se a entidade foi salva corretamente
        assertNotNull(savedOtherRoute);
        assertNotNull(savedOtherRoute.getId()); // Verifique se o ID foi gerado

        // Teste a recuperação
        OtherRoute foundOtherRoute = otherRouteRepository.findById(savedOtherRoute.getId()).orElse(null);
        assertNotNull(foundOtherRoute);
        assertThat(foundOtherRoute.getCompany()).isEqualTo("Example Company");
    }



	 
	    @Test
	    @DisplayName("Should delete an OtherRoute")
	    public void testDeleteOtherRoute() {
	        // Salva o objeto no repositório
	        OtherRoute otherRoute = new OtherRoute();
	        otherRoute.setBAgencyBoard(bAgencyBoard); // Associar o BAgencyBoard
	        otherRoute.setCompany("Example Company");
	        otherRoute.setCity("Example City");
	        otherRoute.setType("Example Type");

	        OtherRoute savedOtherRoute = otherRouteRepository.save(otherRoute);

	        // Exclui o objeto
	        otherRouteRepository.delete(savedOtherRoute);

	        // Verifica se o objeto foi excluído
	        Optional<OtherRoute> foundOtherRoute = otherRouteRepository.findById(savedOtherRoute.getId());
	        assertThat(foundOtherRoute).isNotPresent();
	    }

	    @Test
	    @DisplayName("Should update an OtherRoute")
	    public void testUpdateOtherRoute() {
	        // Salva o objeto no repositório
	        OtherRoute otherRoute = new OtherRoute();
	        otherRoute.setBAgencyBoard(bAgencyBoard); // Associar o BAgencyBoard
	        otherRoute.setCompany("Example Company");
	        otherRoute.setCity("Example City");
	        otherRoute.setType("Example Type");

	        OtherRoute savedOtherRoute = otherRouteRepository.save(otherRoute);

	        // Atualiza a cidade e a empresa da OtherRoute
	        savedOtherRoute.setCity("Nova Cidade B");
	        savedOtherRoute.setCompany("Nova Empresa B");
	        OtherRoute updatedOtherRoute = otherRouteRepository.save(savedOtherRoute);

	        // Verifica se os dados foram atualizados corretamente
	        assertThat(updatedOtherRoute.getCity()).isEqualTo("Nova Cidade B");
	        assertThat(updatedOtherRoute.getCompany()).isEqualTo("Nova Empresa B");
	    }

	    @Test
	    @DisplayName("Should find all OtherRoutes")
	    public void testFindAllOtherRoutes() {
	        // Salva múltiplas OtherRoutes
	        OtherRoute otherRoute = new OtherRoute();
	        otherRoute.setBAgencyBoard(bAgencyBoard); // Associar o BAgencyBoard
	        otherRoute.setCompany("Example Company");
	        otherRoute.setCity("Example City");
	        otherRoute.setType("Example Type");
	        otherRouteRepository.save(otherRoute);

	        OtherRoute anotherRoute = new OtherRoute();
	        anotherRoute.setBAgencyBoard(bAgencyBoard); // Associar o BAgencyBoard
	        anotherRoute.setCompany("Outra Empresa B");
	        anotherRoute.setCity("Outra Cidade B");
	        anotherRoute.setType("Outro Tipo B");
	        otherRouteRepository.save(anotherRoute);

	        // Recupera todas as OtherRoutes
	        Iterable<OtherRoute> otherRoutes = otherRouteRepository.findAll();

	        // Verifica se a lista não está vazia
	        assertThat(otherRoutes).isNotEmpty();
	        assertThat(otherRoutes).hasSize(2);
	    }
	}
