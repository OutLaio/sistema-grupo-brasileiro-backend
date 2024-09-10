//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.BAgencyBoardForm;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.BAgencyBoard;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.ProjectRepository;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import java.util.Optional;
//
//public class BAgencyBoardFormMapperTest {
//
//    @Mock
//    private ProjectRepository projectRepository;
//
//    @InjectMocks
//    private BAgencyBoardFormMapper bAgencyBoardFormMapper;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testMap_Success() {
//        // Given
//        Long projectId = 1L;
//        BAgencyBoardForm form = new BAgencyBoardForm(
//            "Test Location",
//            true,
//            "Test Type",
//            "Test Material",
//            "Test Observations",
//            projectId
//        );
//
//        Project project = new Project();
//        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
//
//        // When
//        BAgencyBoard result = bAgencyBoardFormMapper.map(form);
//
//        // Then
//        assertNotNull(result);
//        assertEquals("Test Location", result.getBoardLocation());
//        assertTrue(result.getCompanySharing());
//        assertEquals("Test Type", result.getBoardType());
//        assertEquals("Test Material", result.getMaterial());
//        assertEquals("Test Observations", result.getObservations());
//        assertEquals(project, result.getProject());
//    }
//
//    @Test
//    void testMap_ProjectNotFound() {
//        // Given
//        Long projectId = 1L;
//        BAgencyBoardForm form = new BAgencyBoardForm(
//            "Test Location",
//            true,
//            "Test Type",
//            "Test Material",
//            "Test Observations",
//            projectId
//        );
//
//        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());
//
//        // When & Then
//        IllegalArgumentException thrown = assertThrows(
//            IllegalArgumentException.class,
//            () -> bAgencyBoardFormMapper.map(form),
//            "Expected map() to throw, but it didn't"
//        );
//        assertTrue(thrown.getMessage().contains("Project not found with ID: " + projectId));
//    }
//}
