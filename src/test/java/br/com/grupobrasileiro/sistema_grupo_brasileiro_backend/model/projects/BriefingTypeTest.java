package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

/**
 * Testa a classe BriefingType.
 * Verifica o funcionamento dos métodos gerados pelo Lombok, construtores e métodos toString.
 */
public class BriefingTypeTest {

    private final Faker faker = new Faker();
    private BriefingType briefingType;

    @BeforeEach
    void setUp() {
        // Inicializa uma nova instância de BriefingType antes de cada teste
        briefingType = new BriefingType();
    }

    /**
     * Testa o construtor padrão da classe BriefingType.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    @DisplayName("Should create an instance with the default constructor")
    void testDefaultConstructor() {
        assertThat(briefingType).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe BriefingType.
     * Verifica se o construtor com parâmetros define corretamente os atributos id e description.
     */
    @Test
    @DisplayName("Should set properties correctly with the parameterized constructor")
    void testParameterizedConstructor() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        BriefingType briefingType = new BriefingType(id, description);

        assertThat(briefingType.getId()).isEqualTo(id);
        assertThat(briefingType.getDescription()).isEqualTo(description);
    }

    /**
     * Testa os métodos setters e getters da classe BriefingType.
     * Verifica se os métodos setId e setDescription definem corretamente os atributos
     * e se os métodos getId e getDescription retornam os valores esperados.
     */
    @Test
    @DisplayName("Should set and get properties correctly")
    void testSettersAndGetters() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        briefingType.setId(id);
        briefingType.setDescription(description);

        assertThat(briefingType.getId()).isEqualTo(id);
        assertThat(briefingType.getDescription()).isEqualTo(description);
    }

    /**
     * Testa os métodos equals e hashCode da classe BriefingType.
     * Verifica se duas instâncias com os mesmos valores de id e description são iguais
     * e se têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should consider equal instances with the same values")
    void testEqualsAndHashCode() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        BriefingType briefingType1 = new BriefingType(id, description);
        BriefingType briefingType2 = new BriefingType(id, description);
        BriefingType briefingType3 = new BriefingType(id + 1, description); // Instância com id diferente

        assertThat(briefingType1).isEqualTo(briefingType2);
        assertThat(briefingType1.hashCode()).isEqualTo(briefingType2.hashCode());

        // Verifica que objetos diferentes não são iguais
        assertThat(briefingType1).isNotEqualTo(briefingType3);
        assertThat(briefingType1.hashCode()).isNotEqualTo(briefingType3.hashCode());
    }

    /**
     * Testa o método toString da classe BriefingType.
     * Verifica se o método toString retorna uma representação correta da instância
     * com os valores de id e description.
     */
    @Test
    @DisplayName("Should return the correct string representation")
    void testToString() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        BriefingType briefingType = new BriefingType(id, description);
        String expectedToString = "BriefingType(id=" + id + ", description=" + description + ")";
        
        assertThat(briefingType.toString()).isEqualTo(expectedToString);
    }

    /**
     * Testa a igualdade de BriefingType com atributos nulos.
     * Verifica se a classe trata corretamente casos em que valores nulos são atribuídos.
     */
    @Test
    @DisplayName("Should handle null attributes correctly")
    void testNullAttributes() {
        briefingType.setId(null);
        briefingType.setDescription(null);

        assertThat(briefingType.getId()).isNull();
        assertThat(briefingType.getDescription()).isNull();
    }

    /**
     * Testa a igualdade de BriefingType com atributos diferentes.
     * Verifica se dois objetos diferentes não são considerados iguais.
     */
    @Test
    @DisplayName("Should not consider different BriefingType instances as equal")
    void testNotEqual() {
        Long id1 = faker.number().randomNumber();
        String description1 = faker.lorem().word();
        Long id2 = faker.number().randomNumber();
        String description2 = faker.lorem().word();

        BriefingType briefingType1 = new BriefingType(id1, description1);
        BriefingType briefingType2 = new BriefingType(id2, description2);

        // Verifica que objetos diferentes não são iguais
        assertThat(briefingType1).isNotEqualTo(briefingType2);
    }
}
