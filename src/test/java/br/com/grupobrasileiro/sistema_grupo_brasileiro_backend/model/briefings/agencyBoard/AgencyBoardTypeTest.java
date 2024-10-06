package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Should create a non-null AgencyBoardType using the default constructor")
    void testDefaultConstructor() {
        AgencyBoardType agencyBoardType = new AgencyBoardType();
        assertThat(agencyBoardType).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe AgencyBoardType.
     * Verifica se o construtor com parâmetros define corretamente os atributos id e description.
     */
    @Test
    @DisplayName("Should set id and description using the parameterized constructor")
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
    @DisplayName("Should set and get id and description correctly")
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
    @DisplayName("Should verify equals and hashCode methods")
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
    @DisplayName("Should return the correct string representation of AgencyBoardType")
    void testToString() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        AgencyBoardType agencyBoardType = new AgencyBoardType(id, description);
        
        String toStringResult = agencyBoardType.toString();
        
        assertThat(toStringResult).startsWith("AgencyBoardType{");
        assertThat(toStringResult).contains("id=" + id);
        assertThat(toStringResult).contains("description='" + description + "'");
        assertThat(toStringResult).endsWith("}");
    }

    /**
     * Testa o comportamento do método toString quando a descrição é nula.
     * Verifica se o método toString ainda retorna uma representação correta.
     */
    @Test
    @DisplayName("Should handle null description in toString method")
    void testToStringWithNullDescription() {
        Long id = faker.number().randomNumber();
        AgencyBoardType agencyBoardType = new AgencyBoardType(id, null);
        
        String toStringResult = agencyBoardType.toString();
        
        assertThat(toStringResult).startsWith("AgencyBoardType{");
        assertThat(toStringResult).contains("id=" + id);
        assertThat(toStringResult).contains("description='null'");
        assertThat(toStringResult).endsWith("}");
    }

    /**
     * Testa o comportamento do método equals quando a descrição é nula.
     * Verifica se dois objetos com a mesma id e descrição nula são considerados iguais.
     */
    @Test
    @DisplayName("Should consider AgencyBoardTypes with the same id and null description as equal")
    void testEqualsWithNullDescription() {
        Long id = faker.number().randomNumber();
        AgencyBoardType agencyBoardType1 = new AgencyBoardType(id, null);
        AgencyBoardType agencyBoardType2 = new AgencyBoardType(id, null);

        assertThat(agencyBoardType1).isEqualTo(agencyBoardType2);
    }
}
