package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.City;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.CompanyCity;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Route;
import jakarta.transaction.Transactional;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityManager;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityManager;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)  
public class RouteRepositoryTest {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private EntityManager entityManager;

    private BAgencyBoard agencyBoard;
    private CompanyCity companyCity;

    @BeforeEach
    void setUp() {
        // Criar dependências obrigatórias (AgencyBoardType, BoardType, City, Company, Briefing)
        AgencyBoardType agencyBoardType = new AgencyBoardType();
        agencyBoardType.setName("Test Agency Board Type");
        entityManager.persist(agencyBoardType);

        BoardType boardType = new BoardType();
        boardType.setName("Test Board Type");
        entityManager.persist(boardType);

        Briefing briefing = new Briefing();
        briefing.setDescription("Test Briefing");
        entityManager.persist(briefing);

        City city = new City();
        city.setName("Test City");
        entityManager.persist(city);

        Company company = new Company();
        company.setName("Test Company");
        entityManager.persist(company);

        // Persistir CompanyCity
        companyCity = new CompanyCity();
        companyCity.setCity(city);
        companyCity.setCompany(company);
        entityManager.persist(companyCity);

        // Persistir BAgencyBoard
        agencyBoard = new BAgencyBoard();
        agencyBoard.setAgencyBoardType(agencyBoardType);
        agencyBoard.setBoardType(boardType);
        agencyBoard.setBriefing(briefing);
        agencyBoard.setBoardLocation("Test Location");
        agencyBoard.setObservations("Test Observations");
        entityManager.persist(agencyBoard);

        entityManager.flush();
    }

    @Test
    void whenSaveRoute_thenFindByIdShouldReturnRoute() {
        // Arrange - criar e salvar uma nova rota
        Route route = new Route();
        route.setBAgencyBoard(agencyBoard);
        route.setCompanyCity(companyCity);
        route.setType("Urban");

        Route savedRoute = routeRepository.save(route);

        // Act - buscar a rota pelo ID
        Optional<Route> foundRoute = routeRepository.findById(savedRoute.getId());

        // Assert - verificar se a rota foi salva e recuperada corretamente
        assertTrue(foundRoute.isPresent());
        assertEquals(foundRoute.get().getId(), savedRoute.getId());
        assertEquals(foundRoute.get().getType(), "Urban");
    }

    @Test
    void whenDeleteRoute_thenRouteShouldBeDeleted() {
        // Arrange - criar e salvar uma rota
        Route route = new Route();
        route.setBAgencyBoard(agencyBoard);
        route.setCompanyCity(companyCity);
        route.setType("Intercity");

        Route savedRoute = entityManager.persistFlushFind(route);

        // Act - deletar a rota
        routeRepository.deleteById(savedRoute.getId());

        // Assert - verificar se a rota foi removida
        Optional<Route> deletedRoute = routeRepository.findById(savedRoute.getId());
        assertFalse(deletedRoute.isPresent());
    }

    @Test
    void whenSaveRouteWithoutMandatoryFields_thenThrowException() {
        // Arrange - criar uma rota sem os campos obrigatórios (CompanyCity ou BAgencyBoard)
        Route route = new Route();
        route.setType("Express");

        // Act & Assert - deve lançar uma exceção de integridade de dados
        assertThrows(DataIntegrityViolationException.class, () -> {
            routeRepository.save(route);
            entityManager.flush();
        });
    }

    // Outros testes como encontrar por atributos específicos podem ser adicionados aqui...
}
