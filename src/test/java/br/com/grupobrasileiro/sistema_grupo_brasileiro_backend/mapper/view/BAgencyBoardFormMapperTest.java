//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import com.github.javafaker.Faker;
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.BAgencyBoardForm;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form.BAgencyBoardFormMapper;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.BAgencyBoard;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//
//import org.mockito.Mockito;
//
//public class BAgencyBoardFormMapperTest {
//
//    private BAgencyBoardFormMapper mapper;
//    private EmployeeRepository projectRepository;
//    private Faker faker;
//
//    @BeforeEach
//    public void setUp() {
//        projectRepository = Mockito.mock(EmployeeRepository.class);
//        mapper = new BAgencyBoardFormMapper(projectRepository);
//        faker = new Faker();
//    }
//
//    @Test
//    public void testMapSuccess() {
//        // Dados fictícios
//        String boardLocation = faker.address().city();
//        Boolean companySharing = faker.bool().bool();
//        String boardType = faker.commerce().productName();
//        String material = faker.commerce().material();
//        String observations = faker.lorem().sentence();
//        Long projectId = faker.number().randomNumber();
//        Project project = new Project(); // Supondo que você tenha uma classe Project
//
//        // Configuração do mock
//        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
//
//        // Criando a instância do BAgencyBoardForm
//        BAgencyBoardForm bAgencyBoardForm = new BAgencyBoardForm(
//            boardLocation,
//            companySharing,
//            boardType,
//            material,
//            observations,
//            projectId
//        );
//
//        // Mapeando
//        BAgencyBoard bAgencyBoard = mapper.map(bAgencyBoardForm);
//
//        // Asserções
//        assertNotNull(bAgencyBoard);
//        assertEquals(boardLocation, bAgencyBoard.getBoardLocation());
//        assertEquals(companySharing, bAgencyBoard.getCompanySharing());
//        assertEquals(boardType, bAgencyBoard.getBoardType());
//        assertEquals(material, bAgencyBoard.getMaterial());
//        assertEquals(observations, bAgencyBoard.getObservations());
//        assertEquals(project, bAgencyBoard.getProject());
//    }
//
//    @Test
//    public void testMapProjectNotFound() {
//        // Dados fictícios
//        String boardLocation = faker.address().city();
//        Boolean companySharing = faker.bool().bool();
//        String boardType = faker.commerce().productName();
//        String material = faker.commerce().material();
//        String observations = faker.lorem().sentence();
//        Long projectId = faker.number().randomNumber();
//
//        // Configuração do mock para não encontrar o projeto
//        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());
//
//        // Criando a instância do BAgencyBoardForm
//        BAgencyBoardForm bAgencyBoardForm = new BAgencyBoardForm(
//            boardLocation,
//            companySharing,
//            boardType,
//            material,
//            observations,
//            projectId
//        );
//
//        // Verificando se a exceção é lançada
//        IllegalArgumentException thrown = assertThrows(
//            IllegalArgumentException.class,
//            () -> mapper.map(bAgencyBoardForm),
//            "Esperava-se uma exceção IllegalArgumentException"
//        );
//
//        assertEquals("Projeto não encontrado com o ID: " + projectId, thrown.getMessage());
//    }
//
//    @Test
//    public void testMapProjectIdNull() {
//        // Dados fictícios
//        String boardLocation = faker.address().city();
//        Boolean companySharing = faker.bool().bool();
//        String boardType = faker.commerce().productName();
//        String material = faker.commerce().material();
//        String observations = faker.lorem().sentence();
//        Long projectId = null; // Definindo como null
//
//        // Configuração do mock para não encontrar o projeto
//        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());
//
//        // Criando a instância do BAgencyBoardForm
//        BAgencyBoardForm bAgencyBoardForm = new BAgencyBoardForm(
//            boardLocation,
//            companySharing,
//            boardType,
//            material,
//            observations,
//            projectId
//        );
//
//        // Verificando se a exceção é lançada
//        IllegalArgumentException thrown = assertThrows(
//            IllegalArgumentException.class,
//            () -> mapper.map(bAgencyBoardForm),
//            "Esperava-se uma exceção IllegalArgumentException"
//        );
//
//        assertEquals("Projeto não encontrado com o ID: " + projectId, thrown.getMessage());
//    }
//    @Test
//    public void testMapRequiredFields() {
//        // Criando a instância do BAgencyBoardForm com campos obrigatórios nulos
//        BAgencyBoardForm bAgencyBoardForm = new BAgencyBoardForm(
//            null, // boardLocation
//            null, // companySharing
//            null, // boardType
//            null, // material
//            null, // observations
//            faker.number().randomNumber() // projectId válido
//        );
//
//        // Verificando se a exceção é lançada
//        // Adapte o código para verificar exceções de validação específicas
//        // Exemplo se houver validação customizada que pode lançar exceções:
//        IllegalArgumentException thrown = assertThrows(
//            IllegalArgumentException.class,
//            () -> mapper.map(bAgencyBoardForm),
//            "Esperava-se uma exceção IllegalArgumentException"
//        );
//
//        assertTrue(thrown.getMessage().contains("Campo obrigatório"));
//    }
//}
