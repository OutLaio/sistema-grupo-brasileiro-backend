package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.form;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form.OtherRoutesForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.OtherRoute;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testa a classe OtherRouteFormMapper.
 * Verifica o funcionamento do mapeamento de OtherRoutesForm para OtherRoute.
 */
public class OtherRouteFormMapperTest {

    private final Faker faker = new Faker();
    private OtherRouteFormMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new OtherRouteFormMapper();
    }

    /**
     * Testa o método map da classe OtherRouteFormMapper.
     * Verifica se o método mapeia corretamente um OtherRoutesForm para um OtherRoute.
     */
    @Test
    void deveMapearOtherRoutesFormParaOtherRoute() {
        // Gerando dados de teste utilizando o Java Faker
        String companyFalsa = faker.company().name();
        String cityFalsa = faker.address().city();
        String typeFalso = faker.lorem().word();

        // Criando o OtherRoutesForm
        OtherRoutesForm form = new OtherRoutesForm(
                companyFalsa,
                cityFalsa,
                typeFalso
        );

        // Mapeia o OtherRoutesForm para OtherRoute
        OtherRoute result = mapper.map(form);

        // Verifica se o resultado não é nulo
        assertThat(result).isNotNull();

        // Verifica se os campos foram mapeados corretamente
        assertThat(result.getCompany()).isEqualTo(companyFalsa);
        assertThat(result.getCity()).isEqualTo(cityFalsa);
        assertThat(result.getType()).isEqualTo(typeFalso);
    }

    /**
     * Testa o método map da classe OtherRouteFormMapper com valores nulos.
     * Verifica se o método mapeia corretamente um OtherRoutesForm com valores nulos para um OtherRoute.
     */
    @Test
    void deveMapearOtherRoutesFormComValoresNulosParaOtherRoute() {
        // Cria uma instância de OtherRoutesForm com valores nulos
        OtherRoutesForm form = new OtherRoutesForm(
                null, // company
                null, // city
                null  // type
        );

        // Mapeia o OtherRoutesForm para OtherRoute
        OtherRoute result = mapper.map(form);

        // Verifica se o resultado não é nulo
        assertThat(result).isNotNull();

        // Verifica se os campos foram mapeados corretamente, incluindo valores nulos
        assertThat(result.getCompany()).isNull();
        assertThat(result.getCity()).isNull();
        assertThat(result.getType()).isNull();
    }

    /**
     * Testa o método map da classe OtherRouteFormMapper com dados variados.
     * Verifica se o método mapeia corretamente um OtherRoutesForm com dados variados para um OtherRoute.
     */
    @Test
    void deveMapearOtherRoutesFormComDadosVariadosParaOtherRoute() {
        // Gerando dados de teste variados utilizando o Java Faker
        String companyFalsa = faker.company().name();
        String cityFalsa = faker.address().city();
        String typeFalso = faker.lorem().word();

        // Criando o OtherRoutesForm
        OtherRoutesForm form = new OtherRoutesForm(
                companyFalsa,
                cityFalsa,
                typeFalso
        );

        // Mapeia o OtherRoutesForm para OtherRoute
        OtherRoute result = mapper.map(form);

        // Verifica se o resultado não é nulo
        assertThat(result).isNotNull();

        // Verifica se os campos foram mapeados corretamente
        assertThat(result.getCompany()).isEqualTo(companyFalsa);
        assertThat(result.getCity()).isEqualTo(cityFalsa);
        assertThat(result.getType()).isEqualTo(typeFalso);
    }
}
