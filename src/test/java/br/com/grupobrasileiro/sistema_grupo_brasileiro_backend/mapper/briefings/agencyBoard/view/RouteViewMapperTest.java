package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.OtherRouteView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.OtherRoute;

/**
 * Testa a classe OtherRouteViewMapper.
 * Verifica se o mapeamento lida com OtherRoute nulo e campos nulos corretamente.
 */
public class RouteViewMapperTest {

    @InjectMocks
    private OtherRouteViewMapper otherRouteViewMapper;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    /**
     * Testa o mapeamento de OtherRoute para OtherRouteView.
     * Verifica se um OtherRoute é corretamente mapeado para um OtherRouteView.
     */
    @Test
    @DisplayName("Should map OtherRoute to OtherRouteView correctly")
    void mapOtherRoute() {
        // Dados de teste usando Faker
        OtherRoute otherRoute = new OtherRoute();
        otherRoute.setId(faker.number().randomNumber());
        otherRoute.setCompany(faker.company().name());
        otherRoute.setCity(faker.address().city());
        otherRoute.setType(faker.lorem().word());

        // Mapeamento
        OtherRouteView result = otherRouteViewMapper.map(otherRoute);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(otherRoute.getId());
        assertThat(result.company()).isEqualTo(otherRoute.getCompany());
        assertThat(result.city()).isEqualTo(otherRoute.getCity()); // Ajustado aqui
        assertThat(result.type()).isEqualTo(otherRoute.getType());
    }

    /**
     * Testa que o método map lança uma exceção ao receber OtherRoute nulo.
     * Verifica se o método lida corretamente com entradas nulas.
     */
    @Test
    @DisplayName("Should throw NullPointerException when mapping null OtherRoute")
    void throwExceptionForNullOtherRoute() {
        assertThrows(NullPointerException.class, () -> {
            otherRouteViewMapper.map(null);
        });
    }

    /**
     * Testa o mapeamento de OtherRoute com campos nulos para OtherRouteView.
     * Verifica se o método lida corretamente com campos nulos no OtherRoute.
     */
    @Test
    @DisplayName("Should map OtherRoute with null fields to OtherRouteView with null fields")
    void mapOtherRouteWithNullFields() {
        OtherRoute otherRoute = new OtherRoute();
        otherRoute.setId(null);
        otherRoute.setCompany(null);
        otherRoute.setCity(null);
        otherRoute.setType(null);

        // Mapeamento
        OtherRouteView result = otherRouteViewMapper.map(otherRoute);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isNull();
        assertThat(result.company()).isNull();
        assertThat(result.city()).isNull(); // Ajustado aqui
        assertThat(result.type()).isNull();
    }

    /**
     * Testa o mapeamento de OtherRoute com ID nulo e campos não nulos.
     * Verifica se o método lida corretamente com ID nulo.
     */
    @Test
    @DisplayName("Should map OtherRoute with null ID and non-null fields")
    void mapOtherRouteWithNullId() {
        OtherRoute otherRoute = new OtherRoute();
        otherRoute.setId(null);
        otherRoute.setCompany(faker.company().name());
        otherRoute.setCity(faker.address().city());
        otherRoute.setType(faker.lorem().word());

        // Mapeamento
        OtherRouteView result = otherRouteViewMapper.map(otherRoute);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isNull();
        assertThat(result.company()).isEqualTo(otherRoute.getCompany());
        assertThat(result.city()).isEqualTo(otherRoute.getCity()); // Ajustado aqui
        assertThat(result.type()).isEqualTo(otherRoute.getType());
    }

    /**
     * Testa o mapeamento de OtherRoute com ID não nulo e campos nulos.
     * Verifica se o método lida corretamente com campos nulos.
     */
    @Test
    @DisplayName("Should map OtherRoute with non-null ID and null fields")
    void mapOtherRouteWithNonNullIdAndNullFields() {
        OtherRoute otherRoute = new OtherRoute();
        otherRoute.setId(faker.number().randomNumber());
        otherRoute.setCompany(null);
        otherRoute.setCity(null);
        otherRoute.setType(null);

        // Mapeamento
        OtherRouteView result = otherRouteViewMapper.map(otherRoute);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(otherRoute.getId());
        assertThat(result.company()).isNull();
        assertThat(result.city()).isNull(); // Ajustado aqui
        assertThat(result.type()).isNull();
    }

    /**
     * Testa o mapeamento de OtherRoute com valores válidos, mas sem nome de empresa.
     * Verifica se o método lida corretamente com nome de empresa vazio.
     */
    @Test
    @DisplayName("Should map OtherRoute with valid ID and empty company name")
    void mapOtherRouteWithEmptyCompany() {
        OtherRoute otherRoute = new OtherRoute();
        otherRoute.setId(faker.number().randomNumber());
        otherRoute.setCompany("");
        otherRoute.setCity(faker.address().city());
        otherRoute.setType(faker.lorem().word());

        // Mapeamento
        OtherRouteView result = otherRouteViewMapper.map(otherRoute);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(otherRoute.getId());
        assertThat(result.company()).isEqualTo(""); 
        assertThat(result.city()).isEqualTo(otherRoute.getCity()); 
        assertThat(result.type()).isEqualTo(otherRoute.getType());
    }
}