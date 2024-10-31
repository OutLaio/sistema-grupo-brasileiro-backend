package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.form;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form.RoutesForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Route;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.CompanyCityRepository;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    @DisplayName("Should map RoutesForm to Route correctly")
    void shouldMap() {
       
        String fakeType = faker.lorem().word();

        // Criando o RoutesForm com o construtor padrão do record
        RoutesForm form = new RoutesForm(
                faker.number().randomNumber(), // Exemplo de idCompanyCity
                fakeType
        );

        // Mapeia o RoutesForm para Route
        Route result = mapper.map(form);

        // Verifica se o resultado não é nulo
        assertThat(result).isNotNull();

        // Verifica se o campo type foi mapeado corretamente
        assertThat(result.getType()).isEqualTo(fakeType);
    }

    /**
     * Testa o método map da classe RouteFormMapper com dados nulos.
     * Verifica se o método mapeia corretamente um RoutesForm com dados nulos para um Route.
     */
    @Test
    @DisplayName("Should map RoutesForm with null values to Route correctly")
    void shouldMapWithNulls() {
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
    @DisplayName("Should map RoutesForm with varied data to Route correctly")
    void shouldMapWithVariedData() {
        // Gerando dados de teste variados utilizando o Java Faker
        String fakeType = faker.lorem().word();
        Long fakeIdCompanyCity = faker.number().randomNumber();

        // Criando o RoutesForm com o construtor padrão do record
        RoutesForm form = new RoutesForm(
                fakeIdCompanyCity,
                fakeType
        );

        // Mapeia o RoutesForm para Route
        Route result = mapper.map(form);

        // Verifica se o resultado não é nulo
        assertThat(result).isNotNull();

        // Verifica se o campo type foi mapeado corretamente
        assertThat(result.getType()).isEqualTo(fakeType);
    }

    /**
     * Testa o método map da classe RouteFormMapper com campos em branco.
     * Verifica se o método lida corretamente com campos em branco no RoutesForm.
     */
    @Test
    @DisplayName("Should handle blank fields in RoutesForm")
    void shouldHandleBlanks() {
        // Criando o RoutesForm com campos em branco
        RoutesForm form = new RoutesForm(
                0L, // idCompanyCity em branco (ou 0 como placeholder)
                ""  // type em branco
        );

        // Mapeia o RoutesForm para Route
        Route result = mapper.map(form);

        // Verifica se o resultado não é nulo
        assertThat(result).isNotNull();

        // Verifica se os campos foram mapeados corretamente
        assertThat(result.getType()).isEqualTo("");
    }

    /**
     * Testa o método map da classe RouteFormMapper com valores máximos de caracteres.
     * Verifica se o método lida corretamente com strings de comprimento máximo.
     */
    @Test
    @DisplayName("Should handle maximum character values")
    void shouldHandleMaxChars() {
        String maxType = faker.lorem().characters(255); // 255 caracteres

        RoutesForm form = new RoutesForm(
                faker.number().randomNumber(), // idCompanyCity
                maxType
        );

        Route result = mapper.map(form);

        assertThat(result).isNotNull();
        assertThat(result.getType()).isEqualTo(maxType);
    }

    /**
     * Testa o método map da classe RouteFormMapper com caracteres especiais.
     * Verifica se o método lida corretamente com caracteres especiais nas strings.
     */
    @Test
    @DisplayName("Should handle special characters")
    void shouldHandleSpecialChars() {
        String specialType = "Type&%$#@!";

        RoutesForm form = new RoutesForm(
                faker.number().randomNumber(), // idCompanyCity
                specialType
        );

        Route result = mapper.map(form);

        assertThat(result).isNotNull();
        assertThat(result.getType()).isEqualTo(specialType);
    }

    /**
     * Testa o método map da classe RouteFormMapper com dados de entrada inválidos.
     * Verifica se o método lida corretamente com dados de entrada que não deveriam ser válidos.
     */
    @Test
    @DisplayName("Should handle invalid input data")
    void shouldHandleInvalidInput() {
        
            RoutesForm form = new RoutesForm(
                null,
                "Valid Type"
        );

        Route result = mapper.map(form);

        assertThat(result).isNotNull();
        assertThat(result.getType()).isEqualTo("Valid Type");
    }
}

