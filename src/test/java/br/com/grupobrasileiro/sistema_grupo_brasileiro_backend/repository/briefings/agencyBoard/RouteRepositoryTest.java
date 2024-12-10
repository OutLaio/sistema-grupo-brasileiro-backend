package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.City;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Route;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.RouteCity;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
public class RouteRepositoryTest {

	    @Autowired
	    private RouteRepository routeRepository;

	    @Autowired
	    private TestEntityManager testEntityManager;

	    private BAgencyBoard agencyBoard;  
	    private City city;                  
	    private Company company;          

	    @BeforeEach
	    void setUp() {

	        testEntityManager.getEntityManager().createQuery("DELETE FROM Employee").executeUpdate();

	        testEntityManager.getEntityManager().createQuery("DELETE FROM User").executeUpdate();
	      
	        Profile profile = new Profile();
	        profile.setDescription("Test Profile");
	        testEntityManager.persist(profile);

	        // Criar User
	        User user = new User();
	        user.setEmail("test@example.com");
	        user.setPassword("password123");
	        user.setDisabled(false);
	        user.setProfile(profile);
	        testEntityManager.persist(user);

	        // Criar Employee e associar ao User
	        Employee client = new Employee();
	        client.setName("Test Client");
	        client.setLastName("Test LastName");
	        client.setPhoneNumber("1234567890");
	        client.setSector("Test Sector");
	        client.setOccupation("Test Occupation");
	        client.setAgency("Test Agency");
			client.setRegistrationNumber("123456");
	        client.setAvatar(1L);
	        client.setUser(user);
	        testEntityManager.persist(client);

	        // Atualizar User com Employee
	        user.setEmployee(client);
	        testEntityManager.merge(user);

	        // Criar AgencyBoardType
	        AgencyBoardType agencyBoardType = new AgencyBoardType();
	        agencyBoardType.setDescription("Test Agency Board Type");
	        testEntityManager.persist(agencyBoardType);

	        // Criar BoardType
	        BoardType boardType = new BoardType();
	        boardType.setDescription("Test Board Type");
	        testEntityManager.persist(boardType);

	        // Criar Project
	        Project project = new Project();
	        project.setTitle("Test Project");
	        project.setClient(client);
	        project.setStatus("Active");
	        project.setDisabled(false);
	        testEntityManager.persist(project);

	        // Criar BriefingType
	        BriefingType briefingType = new BriefingType();
	        briefingType.setDescription("Test Briefing Type");
	        testEntityManager.persist(briefingType);

	        // Criar Briefing
	        Briefing briefing = new Briefing();
	        briefing.setProject(project);
	        briefing.setBriefingType(briefingType);
	        briefing.setStartTime(LocalDate.now());
	        briefing.setExpectedTime(LocalDate.now().plusDays(30));
	        briefing.setDetailedDescription("Test Briefing");
	        testEntityManager.persist(briefing);

	        // Criar City
	        city = new City(); 
	        city.setName("Test City");
	        testEntityManager.persist(city);

	        // Criar Company
	        company = new Company(); 
	        company.setName("Test Company");
	        testEntityManager.persist(company);

	        // Criar BAgencyBoard
	        agencyBoard = new BAgencyBoard(); 
	        agencyBoard.setAgencyBoardType(agencyBoardType);
	        agencyBoard.setBoardType(boardType);
	        agencyBoard.setBriefing(briefing);
	        agencyBoard.setBoardLocation("Test Location");
	        agencyBoard.setObservations("Test Observations");
	        testEntityManager.persist(agencyBoard);

	        testEntityManager.flush();
	    }

	    @Test
	    void whenSaveRoute_thenFindByIdShouldReturnRoute() {
	        // Arrange - criar e salvar uma nova rota
	        Route route = new Route();
	        route.setBAgencyBoard(agencyBoard);
	        route.setCompany(company); 
	        route.setType("Urban");

	        // Criar e associar RouteCity
	        RouteCity routeCity = new RouteCity(); 
	        routeCity.setCity(city); // Atribui a cidade
	        routeCity.setRoute(route); // Atribui a rota
	        route.getRouteCities().add(routeCity); // Adiciona a RouteCity à rota

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
	        route.setCompany(company); // Use a instância de Company diretamente
	        route.setType("Intercity");

	        // Criar e associar RouteCity
	        RouteCity routeCity = new RouteCity(); 
	        routeCity.setCity(city); // Atribui a cidade
	        routeCity.setRoute(route); // Atribui a rota
	        route.getRouteCities().add(routeCity); // Adiciona a RouteCity à rota

	        Route savedRoute = testEntityManager.persistFlushFind(route);

	        // Act - deletar a rota
	        routeRepository.deleteById(savedRoute.getId());

	        // Assert - verificar se a rota foi removida
	        Optional<Route> deletedRoute = routeRepository.findById(savedRoute.getId());
	        assertFalse(deletedRoute.isPresent());
	    }
	}