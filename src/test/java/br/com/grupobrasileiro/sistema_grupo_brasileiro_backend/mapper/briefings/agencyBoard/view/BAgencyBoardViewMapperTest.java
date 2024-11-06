package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.AgencyBoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CityView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.OtherRouteView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.RouteView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.OtherRoute;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Route;

@ExtendWith(MockitoExtension.class)
class BAgencyBoardViewMapperTest {

    @InjectMocks
    private BAgencyBoardViewMapper bAgencyBoardViewMapper;

    @Mock
    private BoardTypeViewMapper boardTypeViewMapper;

    @Mock
    private AgencyBoardTypeViewMapper agencyBoardTypeViewMapper;

    @Mock
    private RouteViewMapper routeViewMapper;

    @Mock
    private OtherRouteViewMapper otherRouteViewMapper;

    private BAgencyBoard bAgencyBoard;

    @BeforeEach
    void setup() {
        // Inicializa um objeto BAgencyBoard com dados de teste
        bAgencyBoard = new BAgencyBoard();
        bAgencyBoard.setId(1L);
        bAgencyBoard.setAgencyBoardType(mock(AgencyBoardType.class));
        bAgencyBoard.setBoardType(mock(BoardType.class));
        bAgencyBoard.setRoutes(Set.of(mock(Route.class)));
        bAgencyBoard.setOtherRoutes(Set.of(mock(OtherRoute.class)));
        bAgencyBoard.setBoardLocation("Localização de teste");
        bAgencyBoard.setObservations("Observação de teste");
    }

    @Test
    void shouldMapBAgencyBoardToBAgencyBoardViewSuccessfully() {
        // Configura retornos simulados para os mapeadores
        AgencyBoardTypeView agencyBoardTypeView = new AgencyBoardTypeView(1L, "Descrição tipo agência");
        BoardTypeView boardTypeView = new BoardTypeView(1L, "Descrição tipo placa");

        // Simula dados para RouteView e seus campos aninhados
        CompanyView companyView = new CompanyView(1L, "Empresa Teste");
        CityView cityView = new CityView(1L, "Cidade Teste");
        RouteView routeView = new RouteView(1L, companyView, Set.of(cityView), "Tipo de Rota");
        OtherRouteView otherRouteView = new OtherRouteView(1L, "Empresa Teste", "Cidade Teste", "Tipo Teste");

        when(agencyBoardTypeViewMapper.map(any())).thenReturn(agencyBoardTypeView);
        when(boardTypeViewMapper.map(any())).thenReturn(boardTypeView);
        when(routeViewMapper.map(any())).thenReturn(routeView);
        when(otherRouteViewMapper.map(any())).thenReturn(otherRouteView);

        // Executa o mapeamento
        BAgencyBoardView result = bAgencyBoardViewMapper.map(bAgencyBoard);

        // Verifica se os valores foram mapeados corretamente
        assertNotNull(result);
        assertEquals(bAgencyBoard.getId(), result.id());
        assertEquals(agencyBoardTypeView, result.agencyBoardType());
        assertEquals(boardTypeView, result.boardType());
        assertEquals(Set.of(routeView), result.routes());
        assertEquals(Set.of(otherRouteView), result.otherRoutes());
        assertEquals(bAgencyBoard.getBoardLocation(), result.boardLocation());
        assertEquals(bAgencyBoard.getObservations(), result.observations());
    }

    @Test
    void shouldThrowNullPointerExceptionWhenBAgencyBoardIsNull() {
        // Verifica se NullPointerException é lançada quando o BAgencyBoard é nulo
        Exception exception = assertThrows(NullPointerException.class, () -> bAgencyBoardViewMapper.map(null));
        assertEquals("BAgencyBoard at Mapper is null", exception.getMessage());
    }

    @Test
    void shouldMapWithNullRoutesAndOtherRoutes() {
        // Configura BAgencyBoard com routes e otherRoutes nulos
        bAgencyBoard.setRoutes(null);
        bAgencyBoard.setOtherRoutes(null);

        // Configura retornos simulados para mapeadores
        AgencyBoardTypeView agencyBoardTypeView = new AgencyBoardTypeView(1L, "Descrição tipo agência");
        BoardTypeView boardTypeView = new BoardTypeView(1L, "Descrição tipo placa");

        when(agencyBoardTypeViewMapper.map(any())).thenReturn(agencyBoardTypeView);
        when(boardTypeViewMapper.map(any())).thenReturn(boardTypeView);

        // Executa o mapeamento
        BAgencyBoardView result = bAgencyBoardViewMapper.map(bAgencyBoard);

        // Verifica se os valores foram mapeados corretamente mesmo com routes e otherRoutes nulos
        assertNotNull(result);
        assertEquals(agencyBoardTypeView, result.agencyBoardType());
        assertEquals(boardTypeView, result.boardType());
        assertTrue(result.routes().isEmpty());
        assertTrue(result.otherRoutes().isEmpty());
    }

    @Test
    void shouldMapWithNullAgencyBoardTypeAndBoardType() {
        // Configura BAgencyBoard com agencyBoardType e boardType nulos
        bAgencyBoard.setAgencyBoardType(null);
        bAgencyBoard.setBoardType(null);

        // Executa o mapeamento
        BAgencyBoardView result = bAgencyBoardViewMapper.map(bAgencyBoard);

        // Verifica se os valores foram mapeados corretamente mesmo com agencyBoardType e boardType nulos
        assertNotNull(result);
        assertNull(result.agencyBoardType());
        assertNull(result.boardType());
    }
}
