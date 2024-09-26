package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Route;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.CompanyCity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class RouteRepositoryTest {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private BAgencyBoardRepository bAgencyBoardRepository;

    @Autowired
    private CompanyCityRepository companyCityRepository;

    private BAgencyBoard bAgencyBoard;
    private CompanyCity companyCity;
    private Route route;

    @BeforeEach
    public void setUp() {
        // Criação de um novo objeto BAgencyBoard
        bAgencyBoard = new BAgencyBoard();
        bAgencyBoard = bAgencyBoardRepository.save(bAgencyBoard);

        // Criação de um novo objeto CompanyCity
        companyCity = new CompanyCity();
        companyCity = companyCityRepository.save(companyCity);

        // Criação de um novo objeto Route
        route = new Route();
        route.setBAgencyBoard(bAgencyBoard);
        route.setCompanyCity(companyCity);
        route.setType("Tipo A");
    }

    /**
     * Testa a persistência e recuperação de um Route.
     */
    @Test
    @DisplayName("Should save and find a Route")
    public void testSaveAndFindRoute() {
        // Salva o objeto no repositório
        Route savedRoute = routeRepository.save(route);

        // Recupera o objeto pelo ID
        Optional<Route> foundRoute = routeRepository.findById(savedRoute.getId());

        // Verifica se o objeto encontrado é o mesmo que o salvo
        assertTrue(foundRoute.isPresent());
        assertEquals(savedRoute.getId(), foundRoute.get().getId());
        assertEquals(savedRoute.getType(), foundRoute.get().getType());
    }

    /**
     * Testa a atualização de um Route.
     */
    @Test
    @DisplayName("Should update a Route")
    public void testUpdateRoute() {
        // Salva o objeto no repositório
        Route savedRoute = routeRepository.save(route);

        // Atualiza o tipo da Route
        savedRoute.setType("Tipo B");
        Route updatedRoute = routeRepository.save(savedRoute);

        // Verifica se o tipo foi atualizado corretamente
        assertEquals("Tipo B", updatedRoute.getType());
    }

    /**
     * Testa a exclusão de um Route.
     */
    @Test
    @DisplayName("Should delete a Route")
    public void testDeleteRoute() {
        // Salva o objeto no repositório
        Route savedRoute = routeRepository.save(route);

        // Exclui o objeto
        routeRepository.delete(savedRoute);

        // Verifica se o objeto foi excluído
        Optional<Route> foundRoute = routeRepository.findById(savedRoute.getId());
        assertThat(foundRoute).isNotPresent();
    }

    /**
     * Testa a recuperação de todos os Routes.
     */
    @Test
    @DisplayName("Should find all Routes")
    public void testFindAllRoutes() {
        // Salva múltiplas Routes
        routeRepository.save(route);
        Route anotherRoute = new Route();
        anotherRoute.setBAgencyBoard(bAgencyBoard);
        anotherRoute.setCompanyCity(companyCity);
        anotherRoute.setType("Tipo C");
        routeRepository.save(anotherRoute);

        // Recupera todos os Routes
        Iterable<Route> routes = routeRepository.findAll();

        // Verifica se a lista não está vazia e se contém os objetos salvos
        assertThat(routes).isNotEmpty();
        assertThat(routes).hasSize(2); // Deve ter dois elementos
    }
}
