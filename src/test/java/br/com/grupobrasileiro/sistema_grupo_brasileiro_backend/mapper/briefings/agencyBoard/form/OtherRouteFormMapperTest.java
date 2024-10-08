package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.form;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form.OtherRoutesForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.OtherRoute;



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
    @DisplayName("Should map OtherRoutesForm to OtherRoute correctly")
    void shouldMap() {
        // Gerando dados de teste utilizando o Java Faker
        String fakeCompany = faker.company().name();
        String fakeCity = faker.address().city();
        String fakeType = faker.lorem().word();

        // Criando o OtherRoutesForm
        OtherRoutesForm form = new OtherRoutesForm(
                fakeCompany,
                fakeCity,
                fakeType
        );

        // Mapeia o OtherRoutesForm para OtherRoute
        OtherRoute result = mapper.map(form);

        // Verifica se o resultado não é nulo
        assertThat(result).isNotNull();

        // Verifica se os campos foram mapeados corretamente
        assertThat(result.getCompany()).isEqualTo(fakeCompany);
        assertThat(result.getCity()).isEqualTo(fakeCity);
        assertThat(result.getType()).isEqualTo(fakeType);
    }

    /**
     * Testa o método map da classe OtherRouteFormMapper com valores nulos.
     * Verifica se o método mapeia corretamente um OtherRoutesForm com valores nulos para um OtherRoute.
     */
    @Test
    @DisplayName("Should map OtherRoutesForm with null values to OtherRoute correctly")
    void shouldMapWithNulls() {
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
    @DisplayName("Should map OtherRoutesForm with varied data to OtherRoute correctly")
    void shouldMapWithVariedData() {
        // Gerando dados de teste variados utilizando o Java Faker
        String fakeCompany = faker.company().name();
        String fakeCity = faker.address().city();
        String fakeType = faker.lorem().word();

        // Criando o OtherRoutesForm
        OtherRoutesForm form = new OtherRoutesForm(
                fakeCompany,
                fakeCity,
                fakeType
        );

        // Mapeia o OtherRoutesForm para OtherRoute
        OtherRoute result = mapper.map(form);

        // Verifica se o resultado não é nulo
        assertThat(result).isNotNull();

        // Verifica se os campos foram mapeados corretamente
        assertThat(result.getCompany()).isEqualTo(fakeCompany);
        assertThat(result.getCity()).isEqualTo(fakeCity);
        assertThat(result.getType()).isEqualTo(fakeType);
    }

    /**
     * Testa o método map da classe OtherRouteFormMapper com campos em branco.
     * Verifica se o método lida corretamente com campos em branco no OtherRoutesForm.
     */
    @Test
    @DisplayName("Should handle blank fields in OtherRoutesForm")
    void shouldHandleBlanks() {
        // Criando o OtherRoutesForm com campos em branco
        OtherRoutesForm form = new OtherRoutesForm(
                "", // company
                "", // city
                ""  // type
        );

        // Mapeia o OtherRoutesForm para OtherRoute
        OtherRoute result = mapper.map(form);

        // Verifica se o resultado não é nulo
        assertThat(result).isNotNull();

        // Verifica se os campos foram mapeados corretamente
        assertThat(result.getCompany()).isEqualTo("");
        assertThat(result.getCity()).isEqualTo("");
        assertThat(result.getType()).isEqualTo("");
    }

    /**
     * Testa o método map da classe OtherRouteFormMapper com valores máximos de caracteres.
     * Verifica se o método lida corretamente com strings de comprimento máximo.
     */
    @Test
    @DisplayName("Should handle maximum character values")
    void shouldHandleMaxChars() {
        String maxCompany = faker.lorem().characters(255); // 255 caracteres
        String maxCity = faker.lorem().characters(255); // 255 caracteres
        String maxType = faker.lorem().characters(255); // 255 caracteres

        OtherRoutesForm form = new OtherRoutesForm(
                maxCompany,
                maxCity,
                maxType
        );

        OtherRoute result = mapper.map(form);

        assertThat(result).isNotNull();
        assertThat(result.getCompany()).isEqualTo(maxCompany);
        assertThat(result.getCity()).isEqualTo(maxCity);
        assertThat(result.getType()).isEqualTo(maxType);
    }

    /**
     * Testa o método map da classe OtherRouteFormMapper com caracteres especiais.
     * Verifica se o método lida corretamente com caracteres especiais nas strings.
     */
    @Test
    @DisplayName("Should handle special characters")
    void shouldHandleSpecialChars() {
        String specialCompany = "Company&%$#@!";
        String specialCity = "City#%$^&*";
        String specialType = "Type@!";

        OtherRoutesForm form = new OtherRoutesForm(
                specialCompany,
                specialCity,
                specialType
        );

        OtherRoute result = mapper.map(form);

        assertThat(result).isNotNull();
        assertThat(result.getCompany()).isEqualTo(specialCompany);
        assertThat(result.getCity()).isEqualTo(specialCity);
        assertThat(result.getType()).isEqualTo(specialType);
    }

    /**
     * Testa o método map da classe OtherRouteFormMapper com dados de entrada inválidos.
     * Verifica se o método lida corretamente com dados de entrada que não deveriam ser válidos.
     */
    @Test
    @DisplayName("Should handle invalid input data")
    void shouldHandleInvalidInput() {
        // Aqui, a lógica de mapeamento deve ser adaptada para lidar com entradas inválidas, se necessário.
        // Caso contrário, o teste pode falhar dependendo da implementação.
        OtherRoutesForm form = new OtherRoutesForm(
                null,
                "Valid City",
                "Valid Type"
        );

        OtherRoute result = mapper.map(form);

        assertThat(result).isNotNull();
        assertThat(result.getCompany()).isNull(); // Verifica se a empresa foi mapeada como nula
        assertThat(result.getCity()).isEqualTo("Valid City");
        assertThat(result.getType()).isEqualTo("Valid Type");
    }
}
