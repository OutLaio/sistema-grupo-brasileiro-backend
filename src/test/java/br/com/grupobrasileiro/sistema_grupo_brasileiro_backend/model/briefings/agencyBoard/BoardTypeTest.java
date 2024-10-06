package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

public class BoardTypeTest {

    private final Faker faker = new Faker();

    /**
     * Testa o construtor padrão da classe BoardType.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    @DisplayName("Should create a non-null instance with default constructor")
    void testDefaultConstructor() {
        BoardType boardType = new BoardType();
        assertThat(boardType).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe BoardType.
     * Verifica se o construtor com parâmetros define corretamente os atributos id e description.
     */
    @Test
    @DisplayName("Should correctly set id and description with parameterized constructor")
    void testParameterizedConstructor() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        BoardType boardType = new BoardType(id, description);
        assertThat(boardType.getId()).isEqualTo(id);
        assertThat(boardType.getDescription()).isEqualTo(description);
    }

    /**
     * Testa os métodos setters e getters da classe BoardType.
     * Verifica se os métodos setId e setDescription definem corretamente os atributos
     * e se os métodos getId e getDescription retornam os valores esperados.
     */
    @Test
    @DisplayName("Should correctly set and get id and description")
    void testSettersAndGetters() {
        BoardType boardType = new BoardType();
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        boardType.setId(id);
        boardType.setDescription(description);

        assertThat(boardType.getId()).isEqualTo(id);
        assertThat(boardType.getDescription()).isEqualTo(description);
    }

    /**
     * Testa os métodos equals e hashCode da classe BoardType.
     * Verifica se duas instâncias com os mesmos valores de id e description são iguais
     * e se têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should be equal and have the same hashCode for equal instances")
    void testEqualsAndHashCode() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        BoardType boardType1 = new BoardType(id, description);
        BoardType boardType2 = new BoardType(id, description);

        assertThat(boardType1).isEqualTo(boardType2);
        assertThat(boardType1.hashCode()).isEqualTo(boardType2.hashCode());
    }

    /**
     * Testa o método toString da classe BoardType.
     * Verifica se o método toString retorna uma representação correta da instância
     * com os valores de id e description.
     */
    @Test
    @DisplayName("Should return correct string representation in toString method")
    void testToString() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        BoardType boardType = new BoardType(id, description);
        
        String toStringResult = boardType.toString();
        
        assertThat(toStringResult).startsWith("BoardType{");
        assertThat(toStringResult).contains("id=" + id);
        assertThat(toStringResult).contains("description='" + description + "'");
        assertThat(toStringResult).endsWith("}");
    }
    /**
     * Testa o comportamento do método equals quando a descrição é nula.
     * Verifica se duas instâncias com o mesmo id e descrição nula são consideradas iguais.
     */
    @Test
    @DisplayName("Should consider BoardTypes with the same id and null description as equal")
    void testEqualsWithNullDescription() {
        Long id = faker.number().randomNumber();
        BoardType boardType1 = new BoardType(id, null);
        BoardType boardType2 = new BoardType(id, null);

        assertThat(boardType1).isEqualTo(boardType2);
    }

    /**
     * Testa o comportamento do método toString quando a descrição é nula.
     * Verifica se o método toString retorna uma representação correta da instância com descrição nula.
     */
    @Test
    @DisplayName("Should handle null description in toString method")
    void testToStringWithNullDescription() {
        Long id = faker.number().randomNumber();
        BoardType boardType = new BoardType(id, null);
        String expectedToString = String.format("BoardType{id=%d, description='null'}", id);
        assertThat(boardType.toString()).isEqualTo(expectedToString);
    }
}
