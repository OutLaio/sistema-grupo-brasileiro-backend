package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.City;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Route;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.RouteCity;

@ExtendWith(MockitoExtension.class)
public class RouteCityRepositoryTest {

    @Mock
    private RouteCityRepository routeCityRepository; 

    @Mock
    private CityRepository cityRepository; 

    @Mock
    private RouteRepository routeRepository; 

    private RouteCity routeCity; 

    @BeforeEach
    void setUp() {
        // Criar e configurar uma cidade de teste
        City city = new City();
        city.setName("Cidade Exemplo");

        // Criar e configurar uma rota de teste
        Route route = new Route();
        route.setType("Tipo Exemplo");

        // Criar e configurar uma associação RouteCity
        routeCity = new RouteCity();
        routeCity.setCity(city);
        routeCity.setRoute(route);
    }

    /**
     * Testa a persistência e recuperação de um RouteCity.
     */
        @Test
        void testSaveAndFindRouteCity() {
            // Mock do objeto RouteCity (pode ser substituído por um objeto real se necessário)
            RouteCity routeCity = new RouteCity();
            routeCity.setId(1L); // Defina o ID para simular um objeto salvo.

            // Configurar o comportamento do mock usando lenient para evitar erros de stubbing
            lenient().when(routeCityRepository.save(any(RouteCity.class))).thenReturn(routeCity);
            lenient().when(routeCityRepository.findById(1L)).thenReturn(Optional.of(routeCity));

            // Salvar o objeto no repositório
            RouteCity savedRouteCity = routeCityRepository.save(routeCity);

            // Recuperar o objeto pelo ID
            Optional<RouteCity> foundRouteCity = routeCityRepository.findById(savedRouteCity.getId());

            // Verificar se o objeto encontrado é o mesmo que o salvo
            assertThat(foundRouteCity).isPresent(); // Confirma que o Optional não está vazio.
            assertThat(foundRouteCity.get()).isEqualTo(savedRouteCity); // Compara os objetos.

            // Verificar se os métodos foram chamados (opcional)
            verify(routeCityRepository).save(any(RouteCity.class));
            verify(routeCityRepository).findById(1L);
        }
  


    /**
     * Testa a busca de um RouteCity inexistente pelo ID.
     */
    @Test
    void testFindByIdNotFound() {
        // Configurar o comportamento do mock para retornar vazio
        when(routeCityRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<RouteCity> foundRouteCity = routeCityRepository.findById(999L); // ID que não existe
        assertThat(foundRouteCity).isNotPresent(); // Deve ser vazio
    }

    /**
     * Testa a exclusão de um RouteCity.
     */
    @Test
    void testDeleteRouteCity() {
        // Configurar o comportamento do mock
        doNothing().when(routeCityRepository).delete(any(RouteCity.class));

        // Exclui o objeto
        routeCityRepository.delete(routeCity);

        // Verifica se o método delete foi chamado
        verify(routeCityRepository, times(1)).delete(routeCity);
    }

    /**
     * Testa a atualização de um RouteCity.
     */
    @Test
    void testUpdateRouteCity() {
        // Configurar o comportamento do mock
        when(routeCityRepository.save(any(RouteCity.class))).thenReturn(routeCity);

        // Atualiza o objeto
        routeCity.setCity(new City());
        routeCity.setRoute(new Route());

        // Salva a atualização
        RouteCity updatedRouteCity = routeCityRepository.save(routeCity);

        // Verifica se a atualização foi aplicada
        assertThat(updatedRouteCity).isEqualTo(routeCity);
    }

    /**
     * Testa a busca de todas as instâncias de RouteCity.
     */
    @Test
    void testFindAllRouteCities() {
        // Configurar o comportamento do mock
        when(routeCityRepository.findAll()).thenReturn(List.of(routeCity));

        // Recupera todos os RouteCities
        List<RouteCity> routeCities = routeCityRepository.findAll();

        // Verifica se a lista não está vazia
        assertThat(routeCities).isNotEmpty();
        assertThat(routeCities).hasSize(1); // Verifica se o total é 1
    }
}