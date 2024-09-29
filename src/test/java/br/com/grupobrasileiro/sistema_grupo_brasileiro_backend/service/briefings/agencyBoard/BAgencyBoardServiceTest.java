package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.agencyBoard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form.BAgencyBoardsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form.RoutesForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.AgencyBoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CityView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.OtherRouteView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.RouteView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.form.BAgencyBoardFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.form.OtherRouteFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.form.RouteFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view.BAgencyBoardDetailedViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.AgencyBoardTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.BAgencyBoardRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.BoardTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.CompanyCityRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.OtherRouteRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.RouteRepository;

@DisplayName("BAgencyBoardService Tests")
class BAgencyBoardServiceTest {

    @Mock
    private BoardTypeRepository boardTypeRepository;

    @Mock
    private AgencyBoardTypeRepository agencyBoardTypeRepository;

    @Mock
    private RouteRepository routeRepository;

    @Mock
    private OtherRouteRepository otherRouteRepository;

    @Mock
    private BAgencyBoardFormMapper bAgencyBoardFormMapper;

    @Mock
    private RouteFormMapper routeFormMapper;

    @Mock
    private CompanyCityRepository companyCityRepository;

    @Mock
    private OtherRouteFormMapper otherRouteFormMapper;

    @Mock
    private BAgencyBoardRepository bAgencyBoardRepository;

    @Mock
    private BAgencyBoardDetailedViewMapper bAgencyBoardRegisterViewMapper;

    @InjectMocks
    private BAgencyBoardService bAgencyBoardService;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    @DisplayName("Should successfully register a BAgencyBoard")
    void register_Successful() {
        // Arrange
        Long idAgencyBoardType = faker.number().randomNumber();
        Long idBoardType = faker.number().randomNumber();
        String boardLocation = faker.address().city();
        String observation = faker.lorem().sentence();

        // Formulário para criação
        BAgencyBoardsForm form = new BAgencyBoardsForm(
            idAgencyBoardType,
            idBoardType,
            boardLocation,
            observation,
            List.of(), // Sem outras rotas para este teste
            List.of()  // Sem rotas para este teste
        );

        // Briefing fictício
        Briefing briefing = new Briefing(); // Use uma implementação real ou mock

        // Mocks
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        when(bAgencyBoardFormMapper.map(any(BAgencyBoardsForm.class))).thenReturn(bAgencyBoard);

        AgencyBoardType agencyBoardType = new AgencyBoardType();
        when(agencyBoardTypeRepository.findById(anyLong())).thenReturn(Optional.of(agencyBoardType));

        BoardType boardType = new BoardType();
        when(boardTypeRepository.findById(anyLong())).thenReturn(Optional.of(boardType));

        // Preencher dados fictícios para views
        RouteView routeView = new RouteView(
            faker.number().randomNumber(),
            new CompanyView(faker.number().randomNumber(), faker.lorem().word()), 
            List.of(new CityView(faker.number().randomNumber(), faker.lorem().word())), 
            faker.lorem().word()
        );

        OtherRouteView otherRouteView = new OtherRouteView(
            faker.number().randomNumber(),
            faker.lorem().word(),
            faker.lorem().word(),
            faker.lorem().word()
        );

        BAgencyBoardView bAgencyBoardView = new BAgencyBoardView(
            faker.number().randomNumber(),
            new AgencyBoardTypeView(faker.number().randomNumber(), faker.lorem().word()), 
            new BoardTypeView(faker.number().randomNumber(), faker.lorem().word()), 
            Set.of(routeView),
            Set.of(otherRouteView), 
            boardLocation,
            observation
        );

        ProjectView projectView = new ProjectView(
            faker.number().randomNumber(),
            faker.lorem().word(),
            faker.lorem().word(),
            new EmployeeSimpleView(faker.number().randomNumber(), faker.lorem().word(), idBoardType),
            new EmployeeSimpleView(faker.number().randomNumber(), faker.lorem().word(), idBoardType)
        );

        BriefingView briefingView = new BriefingView(
            faker.number().randomNumber(),
            new BriefingTypeView(faker.number().randomNumber(), faker.lorem().word()),
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(1),
            LocalDateTime.now().plusDays(2),
            faker.lorem().sentence()
        );

        when(bAgencyBoardRegisterViewMapper.map(any(BAgencyBoard.class))).thenReturn(
            new BAgencyBoardDetailedView(bAgencyBoardView, projectView, briefingView)
        );

        // Act
        BAgencyBoardDetailedView result = bAgencyBoardService.register(form, briefing);

        // Assert
        assertNotNull(result);
        verify(bAgencyBoardRepository, times(1)).saveAndFlush(any(BAgencyBoard.class));
    }


    @Test
    @DisplayName("Should throw EntityNotFoundException when AgencyBoardType is not found")
    void register_AgencyBoardTypeNotFound() {
        // Arrange
        Long idAgencyBoardType = faker.number().randomNumber();
        BAgencyBoardsForm form = new BAgencyBoardsForm(
            idAgencyBoardType,
            null,
            faker.address().city(),
            faker.lorem().sentence(),
            List.of(),
            List.of()
        );

        Briefing briefing = new Briefing();

        when(bAgencyBoardFormMapper.map(any(BAgencyBoardsForm.class))).thenReturn(new BAgencyBoard());

        when(agencyBoardTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> bAgencyBoardService.register(form, briefing),
            () -> "Expected EntityNotFoundException to be thrown"
        );

        assertEquals("Agency Board Type not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when BoardType is not found")
    void register_BoardTypeNotFound() {
        // Arrange
        Long idAgencyBoardType = faker.number().randomNumber();
        Long idBoardType = faker.number().randomNumber();
        BAgencyBoardsForm form = new BAgencyBoardsForm(
            idAgencyBoardType,
            idBoardType,
            faker.address().city(),
            faker.lorem().sentence(),
            List.of(),
            List.of()
        );

        Briefing briefing = new Briefing();

        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        when(bAgencyBoardFormMapper.map(any(BAgencyBoardsForm.class))).thenReturn(bAgencyBoard);

        AgencyBoardType agencyBoardType = new AgencyBoardType();
        when(agencyBoardTypeRepository.findById(anyLong())).thenReturn(Optional.of(agencyBoardType));

        when(boardTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> bAgencyBoardService.register(form, briefing),
            () -> "Expected EntityNotFoundException to be thrown"
        );

        assertEquals("Board Type not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when CompanyCity is not found")
    void register_CompanyCityNotFound() {
        // Arrange
        Long idAgencyBoardType = faker.number().randomNumber();
        Long idBoardType = faker.number().randomNumber();
        Long idCompanyCity = faker.number().randomNumber();
        BAgencyBoardsForm form = new BAgencyBoardsForm(
            idAgencyBoardType,
            idBoardType,
            faker.address().city(),
            faker.lorem().sentence(),
            List.of(),
            List.of(new RoutesForm(idCompanyCity, null))
        );

        Briefing briefing = new Briefing();

        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        when(bAgencyBoardFormMapper.map(any(BAgencyBoardsForm.class))).thenReturn(bAgencyBoard);

        AgencyBoardType agencyBoardType = new AgencyBoardType();
        when(agencyBoardTypeRepository.findById(anyLong())).thenReturn(Optional.of(agencyBoardType));

        BoardType boardType = new BoardType();
        when(boardTypeRepository.findById(anyLong())).thenReturn(Optional.of(boardType));

        when(companyCityRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> bAgencyBoardService.register(form, briefing),
            () -> "Expected IllegalArgumentException to be thrown"
        );

        assertEquals("CompanyCity not found", exception.getMessage());
    }
 
}
