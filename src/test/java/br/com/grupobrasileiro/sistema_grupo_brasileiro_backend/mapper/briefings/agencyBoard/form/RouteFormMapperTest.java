package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.form;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form.RoutesForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Route;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.CompanyCityRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testa a classe RouteFormMapper.
 * Verifica o funcionamento do mapeamento de RoutesForm para Route.
 */
public class RouteFormMapperTest {

    private final Faker faker = new Faker();

    @Mock
    private CompanyCityRepository companyCityRepository;

    @InjectMocks
    private RouteFormMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testa o método map da classe RouteFormMapper.
     * Verifica se o método mapeia corretamente um RoutesForm para um Route.
     */
    @Test
    void deveMapearRoutesFormParaRoute() {
        // Gerando dados de teste utilizando o Java Faker
        String typeFalso = faker.lorem().word();

        // Criando o RoutesForm com o construtor padrão do record
        RoutesForm form = new RoutesForm(
                faker.number().randomNumber(), // Exemplo de idCompanyCity
                typeFalso
        );

        // Mapeia o RoutesForm para Route
        Route result = mapper.map(form);

        // Verifica se o resultado não é nulo
        assertThat(result).isNotNull();

        // Verifica se o campo type foi mapeado corretamente
        assertThat(result.getType()).isEqualTo(typeFalso);
    }

    /**
     * Testa o método map da classe RouteFormMapper com dados nulos.
     * Verifica se o método mapeia corretamente um RoutesForm com dados nulos para um Route.
     */
    @Test
    void deveMapearRoutesFormComDadosNulosParaRoute() {
        // Criando o RoutesForm com dados nulos
        RoutesForm form = new RoutesForm(
                null,  // idCompanyCity nulo
                null   // type nulo
        );

        // Mapeia o RoutesForm para Route
        Route result = mapper.map(form);

        // Verifica se o resultado não é nulo
        assertThat(result).isNotNull();

        // Verifica se o campo type foi mapeado corretamente, incluindo valores nulos
        assertThat(result.getType()).isNull();
    }

    /**
     * Testa o método map da classe RouteFormMapper com dados variados.
     * Verifica se o método mapeia corretamente um RoutesForm com dados variados para um Route.
     */
    @Test
    void deveMapearRoutesFormComDadosVariadosParaRoute() {
        // Gerando dados de teste variados utilizando o Java Faker
        String typeFalso = faker.lorem().word();
        Long idCompanyCityFalso = faker.number().randomNumber();

        // Criando o RoutesForm com o construtor padrão do record
        RoutesForm form = new RoutesForm(
                idCompanyCityFalso,
                typeFalso
        );

        // Mapeia o RoutesForm para Route
        Route result = mapper.map(form);

        // Verifica se o resultado não é nulo
        assertThat(result).isNotNull();

        // Verifica se o campo type foi mapeado corretamente
        assertThat(result.getType()).isEqualTo(typeFalso);
    }
}

