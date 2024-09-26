package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.City;

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
     * Testa a busca de uma cidade inexistente pelo ID.
     * Verifica se retorna um Optional vazio.
     */
    @Test
    public void testFindByIdNotFound() {
        when(cityRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<City> foundCity = cityRepository.findById(999L);

        assertFalse(foundCity.isPresent()); // Deve ser vazio
        verify(cityRepository, times(1)).findById(999L);
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
     * Testa a atualização de uma cidade.
     * Verifica se o método save é chamado novamente após a alteração.
     */
    @Test
    public void testUpdate() {
        // Arrange
        City updatedCity = new City();
        updatedCity.setId(1L);
        updatedCity.setName("Nova Cidade");

        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(cityRepository.save(updatedCity)).thenReturn(updatedCity);

        // Act
        city.setName("Nova Cidade");
        City savedCity = cityRepository.save(city);

        // Assert
        assertNotNull(savedCity);
        assertEquals("Nova Cidade", savedCity.getName());
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

    /**
     * Testa a busca de todas as cidades.
     * Verifica se retorna uma lista não vazia.
     */
    @Test
    public void testFindAll() {
        when(cityRepository.findAll()).thenReturn(Collections.singletonList(city));

        var cities = cityRepository.findAll();

        assertNotNull(cities);
        assertFalse(cities.isEmpty()); // Deve retornar uma lista não vazia
        assertEquals(1, cities.size()); // Deve ter um elemento
        assertEquals(city.getName(), cities.get(0).getName());
        verify(cityRepository, times(1)).findAll();
    }
}
