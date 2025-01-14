package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CityView;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.City;

/**
 * Testa a classe CityViewMapper.
 * Verifica se o mapeamento lida com City nulo e campos nulos corretamente.
 */
public class CityViewMapperTest {

    @InjectMocks
    private CityViewMapper cityViewMapper;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    /**
     * Testa o mapeamento de City para CityView.
     * Verifica se um City é corretamente mapeado para um CityView.
     */
    @Test
    @DisplayName("Should map City to CityView correctly")
    void mapCityToCityView() {
        // Dados de teste usando Faker
        City city = new City();
        city.setId(faker.number().randomNumber());
        city.setName(faker.address().city());

        // Mapeamento
        CityView result = cityViewMapper.map(city);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(city.getId());
        assertThat(result.name()).isEqualTo(city.getName());
    }

    /**
     * Testa que o método map lança uma exceção ao receber City nulo.
     * Verifica se o método lida corretamente com entradas nulas.
     */
    @Test
    @DisplayName("Should throw NullPointerException when mapping null City")
    void throwExceptionForNullCity() {
        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(() -> cityViewMapper.map(null));
    }

    /**
     * Testa o mapeamento de City com campos nulos para CityView.
     * Verifica se o método lida corretamente com campos nulos no City.
     */
    @Test
    @DisplayName("Should map City with null fields to CityView with null fields")
    void mapCityWithNullFields() {
        City city = new City();
        city.setId(null);
        city.setName(null);

        // Mapeamento
        CityView result = cityViewMapper.map(city);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isNull();
        assertThat(result.name()).isNull();
    }

    /**
     * Testa o mapeamento de City com ID nulo e nome não nulo.
     * Verifica se o método lida corretamente com ID nulo.
     */
    @Test
    @DisplayName("Should map City with null ID and non-null name")
    void mapCityWithNullId() {
        City city = new City();
        city.setId(null);
        city.setName(faker.address().city());

        // Mapeamento
        CityView result = cityViewMapper.map(city);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isNull();
        assertThat(result.name()).isEqualTo(city.getName());
    }

    /**
     * Testa o mapeamento de City com ID não nulo e nome vazio.
     * Verifica se o método lida corretamente com nome vazio.
     */
    @Test
    @DisplayName("Should map City with non-null ID and empty name")
    void mapCityWithEmptyName() {
        City city = new City();
        city.setId(faker.number().randomNumber());
        city.setName(""); // Nome vazio

        // Mapeamento
        CityView result = cityViewMapper.map(city);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(city.getId());
        assertThat(result.name()).isEqualTo(""); // Esperando string vazia
    }

    /**
     * Testa o mapeamento de City com espaços em branco no nome.
     * Verifica se o método lida corretamente com nome que contém apenas espaços.
     */
    @Test
    @DisplayName("Should map City with name containing only whitespace")
    void mapCityWithWhitespaceName() {
        City city = new City();
        city.setId(faker.number().randomNumber());
        city.setName("   "); // Apenas espaços em branco

        // Mapeamento
        CityView result = cityViewMapper.map(city);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(city.getId());
        assertThat(result.name()).isEqualTo("   "); // Esperando espaços em branco
    }
}
