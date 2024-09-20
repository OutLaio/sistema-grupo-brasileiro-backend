package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.OtherRouteView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view.OtherRouteViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.OtherRoute;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.OtherRouteView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view.OtherRouteViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.OtherRoute;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.OtherRouteView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view.OtherRouteViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.OtherRoute;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.OtherRouteView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view.OtherRouteViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.OtherRoute;

/**
 * Testa a classe OtherRouteViewMapper.
 * Verifica se o mapeamento lida com OtherRoute nulo e campos nulos corretamente.
 */
public class OtherRouteViewMapperTest {

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
    void deveMapearOtherRouteParaOtherRouteView() {
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
        assertThat(result.cityView()).isEqualTo(otherRoute.getCity());
        assertThat(result.type()).isEqualTo(otherRoute.getType());
    }

    /**
     * Testa que o método map lança uma exceção ao receber OtherRoute nulo.
     * Verifica se o método lida corretamente com entradas nulas.
     */
    @Test
    @DisplayName("Should throw NullPointerException when mapping null OtherRoute")
    void deveLancarExcecaoParaOtherRouteNulo() {
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
    void deveMapearOtherRouteComCamposNulosParaOtherRouteView() {
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
        assertThat(result.cityView()).isNull();
        assertThat(result.type()).isNull();
    }
}
