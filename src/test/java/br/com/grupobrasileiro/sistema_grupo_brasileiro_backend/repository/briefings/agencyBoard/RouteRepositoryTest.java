package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Route;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.CompanyCity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

    /**
     * Testa a persistência e recuperação de um Route.
     */
    @Test
    public void testSaveAndFindRoute() {
        // Criação de um novo objeto BAgencyBoard
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        bAgencyBoard = bAgencyBoardRepository.save(bAgencyBoard);

        // Criação de um novo objeto CompanyCity
        CompanyCity companyCity = new CompanyCity();
        companyCity = companyCityRepository.save(companyCity);

        // Criação de um novo objeto Route
        Route route = new Route();
        route.setBAgencyBoard(bAgencyBoard);
        route.setCompanyCity(companyCity);
        route.setType("Tipo A");

        // Salva o objeto no repositório
        Route savedRoute = routeRepository.save(route);

        // Recupera o objeto pelo ID
        Optional<Route> foundRoute = routeRepository.findById(savedRoute.getId());

        // Verifica se o objeto encontrado é o mesmo que o salvo
        assertTrue(foundRoute.isPresent());
        assertEquals(savedRoute.getId(), foundRoute.get().getId());
    }
}