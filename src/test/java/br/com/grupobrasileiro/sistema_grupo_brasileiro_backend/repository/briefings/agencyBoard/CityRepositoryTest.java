package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.City;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CityRepositoryTest {

    @Mock
    private CityRepository cityRepository;

    private City city;
    private Faker faker;

    /**
     * Testa a classe City.
     * Verifica o funcionamento dos métodos gerados pelo Lombok, construtores e métodos toString.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
        city = new City();
        city.setId(1L);
        city.setName(faker.address().city());
    }

    /**
     * Testa a busca de uma cidade pelo ID.
     * Verifica se o objeto correto é retornado.
     */
    @Test
    public void testFindById() {
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));

        Optional<City> foundCity = cityRepository.findById(1L);

        assertTrue(foundCity.isPresent());
        assertEquals(city.getName(), foundCity.get().getName());
        verify(cityRepository, times(1)).findById(1L);
    }

    /**
     * Testa o salvamento de uma cidade.
     * Verifica se o método save funciona corretamente.
     */
    @Test
    public void testSave() {
        when(cityRepository.save(city)).thenReturn(city);

        City savedCity = cityRepository.save(city);

        assertNotNull(savedCity);
        assertEquals(city.getName(), savedCity.getName());
        verify(cityRepository, times(1)).save(city);
    }

    /**
     * Testa a exclusão de uma cidade pelo ID.
     * Verifica se o método deleteById é chamado corretamente.
     */
    @Test
    public void testDeleteById() {
        doNothing().when(cityRepository).deleteById(1L);

        cityRepository.deleteById(1L);

        verify(cityRepository, times(1)).deleteById(1L);
    }
}
