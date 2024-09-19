package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard;


import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;

/**
 * Testa a classe AgencyBoardType.
 * Verifica o funcionamento dos métodos gerados pelo Lombok, construtores e métodos toString.
 */
public class AgencyBoardTypeTest {

    private final Faker faker = new Faker();

    /**
     * Testa o construtor padrão da classe AgencyBoardType.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    void testDefaultConstructor() {
        AgencyBoardType agencyBoardType = new AgencyBoardType();
        assertThat(agencyBoardType).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe AgencyBoardType.
     * Verifica se o construtor com parâmetros define corretamente os atributos id e description.
     */
    @Test
    void testParameterizedConstructor() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        AgencyBoardType agencyBoardType = new AgencyBoardType(id, description);
        assertThat(agencyBoardType.getId()).isEqualTo(id);
        assertThat(agencyBoardType.getDescription()).isEqualTo(description);
    }

    /**
     * Testa os métodos setters e getters da classe AgencyBoardType.
     * Verifica se os métodos setId e setDescription definem corretamente os atributos
     * e se os métodos getId e getDescription retornam os valores esperados.
     */
    @Test
    void testSettersAndGetters() {
        AgencyBoardType agencyBoardType = new AgencyBoardType();
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        agencyBoardType.setId(id);
        agencyBoardType.setDescription(description);

        assertThat(agencyBoardType.getId()).isEqualTo(id);
        assertThat(agencyBoardType.getDescription()).isEqualTo(description);
    }

    /**
     * Testa os métodos equals e hashCode da classe AgencyBoardType.
     * Verifica se duas instâncias com os mesmos valores de id e description são iguais
     * e se têm o mesmo hashCode.
     */
    @Test
    void testEqualsAndHashCode() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        AgencyBoardType agencyBoardType1 = new AgencyBoardType(id, description);
        AgencyBoardType agencyBoardType2 = new AgencyBoardType(id, description);
        AgencyBoardType agencyBoardType3 = new AgencyBoardType(id + 1, description); // Instância com id diferente

        assertThat(agencyBoardType1).isEqualTo(agencyBoardType2);
        assertThat(agencyBoardType1.hashCode()).isEqualTo(agencyBoardType2.hashCode());

        // Verifica que objetos diferentes não são iguais
        assertThat(agencyBoardType1).isNotEqualTo(agencyBoardType3);
        assertThat(agencyBoardType1.hashCode()).isNotEqualTo(agencyBoardType3.hashCode());
    }

    /**
     * Testa o método toString da classe AgencyBoardType.
     * Verifica se o método toString retorna uma representação correta da instância
     * com os valores de id e description.
     */
    @Test
    void testToString() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        AgencyBoardType agencyBoardType = new AgencyBoardType(id, description);
        String expectedToString = "AgencyBoardType(id=" + id + ", description=" + description + ")";
        assertThat(agencyBoardType.toString()).contains(expectedToString);
    }
}
