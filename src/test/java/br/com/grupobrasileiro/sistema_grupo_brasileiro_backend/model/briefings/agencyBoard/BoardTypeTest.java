package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

public class BoardTypeTest {

    private final Faker faker = new Faker();

    /**
     * Testa o construtor padrão da classe BoardType.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    void testDefaultConstructor() {
        BoardType boardType = new BoardType();
        assertThat(boardType).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe BoardType.
     * Verifica se o construtor com parâmetros define corretamente os atributos id e description.
     */
    @Test
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
    void testToString() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        BoardType boardType = new BoardType(id, description);
        String expectedToString = "BoardType(id=" + id + ", description=" + description + ")";
        assertThat(boardType.toString()).contains(expectedToString);
    }
}
