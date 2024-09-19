package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;

/**
 * Testa a classe BriefingType.
 * Verifica o funcionamento dos métodos gerados pelo Lombok, construtores e métodos toString.
 */
public class BriefingTypeTest {

    private final Faker faker = new Faker();

    /**
     * Testa o construtor padrão da classe BriefingType.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    void testDefaultConstructor() {
        BriefingType briefingType = new BriefingType();
        assertThat(briefingType).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe BriefingType.
     * Verifica se o construtor com parâmetros define corretamente os atributos id e description.
     */
    @Test
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
    void testSettersAndGetters() {
        BriefingType briefingType = new BriefingType();
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
    void testToString() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        BriefingType briefingType = new BriefingType(id, description);
        String expectedToString = "BriefingType(id=" + id + ", description=" + description + ")";
        assertThat(briefingType.toString()).contains(expectedToString);
    }
}
