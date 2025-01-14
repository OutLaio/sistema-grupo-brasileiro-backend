package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.agencyBoard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
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
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.form.BAgencyBoardFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.form.OtherRouteFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.form.RouteFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.City;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Route;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.AgencyBoardTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.BAgencyBoardRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.BoardTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.CityRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.OtherRouteRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.RouteCityRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.RouteRepository;

public class BAgencyBoardServiceTest {

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
    private OtherRouteFormMapper otherRouteFormMapper;

    @Mock
    private BAgencyBoardRepository bAgencyBoardRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private RouteCityRepository routeCityRepository;

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

        // Criando um conjunto de IDs de cidades
        Set<Long> idCities = new HashSet<>();
        idCities.add(faker.number().randomNumber()); // Adicionando uma cidade fictícia

        // Formulário para criação
        RoutesForm routesForm = new RoutesForm(
            faker.number().randomNumber(), // idCompany
            idCities, // idCities
            faker.lorem().word() // type
        );

        BAgencyBoardsForm form = new BAgencyBoardsForm(
            idAgencyBoardType,
            idBoardType,
            boardLocation,
            observation,
            List.of(), // Sem outras rotas para este teste
            List.of(routesForm)  // Adicionando a rota para o teste
        );

        // Briefing fictício
        Briefing briefing = new Briefing(); 
        
        // Mocks
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        when(bAgencyBoardFormMapper.map(any(BAgencyBoardsForm.class))).thenReturn(bAgencyBoard);

        AgencyBoardType agencyBoardType = new AgencyBoardType();
        when(agencyBoardTypeRepository.findById(anyLong())).thenReturn(Optional.of(agencyBoardType));

        BoardType boardType = new BoardType();
        when(boardTypeRepository.findById(anyLong())).thenReturn(Optional.of(boardType));

        // Mock para a cidade
        City city = new City(); // Supondo que City é uma classe válida
        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(city));

        // Mock para o RouteFormMapper
        Route route = new Route(); // Crie uma instância de Route
        when(routeFormMapper.map(any(RoutesForm.class))).thenReturn(route); // Certifique-se de que o mapeamento retorna uma instância válida

        // Act
        bAgencyBoardService.register(form, briefing);

        // Assert
        verify(bAgencyBoardRepository, times(1)).saveAndFlush(bAgencyBoard);
        verify(routeRepository, times(1)).saveAndFlush(route); // Verifica se a rota foi salva
        verify(routeCityRepository, times(1)).saveAll(anySet()); // Verifica se as cidades da rota foram salvas
    }

}