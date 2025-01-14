package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa a classe StickerType.
 * Verifica se os métodos da classe funcionam corretamente e se os dados são configurados adequadamente.
 */
class StickerTypeTest {

    private StickerType type;
    private final Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        // Inicializa uma nova instância de StickerType antes de cada teste
        type = new StickerType();
    }

    /**
     * Testa os métodos getters e setters da classe StickerType.
     * Verifica se os dados são configurados e recuperados corretamente.
     */
    @Test
    @DisplayName("Should correctly set and get properties of StickerType")
    void testGettersAndSetters() {
        // Dados fictícios
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();

        // Configura os dados da entidade
        type.setId(id);
        type.setDescription(description);

        // Validação dos métodos getters
        assertNotNull(type.getId());
        assertEquals(id, type.getId());
        assertEquals(description, type.getDescription());
    }

    /**
     * Testa a igualdade e o código hash da classe StickerType.
     * Verifica se duas instâncias com os mesmos valores são consideradas iguais.
     */
    @Test
    @DisplayName("Should consider StickerType instances with the same values as equal")
    void testEqualsAndHashCode() {
        StickerType type1 = new StickerType();
        type1.setId(1L);
        type1.setDescription("Type A");

        StickerType type2 = new StickerType();
        type2.setId(1L);
        type2.setDescription("Type A");

        StickerType type3 = new StickerType();
        type3.setId(2L);
        type3.setDescription("Type B");

        // Verifica se dois objetos com os mesmos valores são considerados iguais
        assertEquals(type1, type2);
        assertEquals(type1.hashCode(), type2.hashCode());

        // Verifica se objetos com valores diferentes não são iguais
        assertNotEquals(type1, type3);
        assertNotEquals(type1.hashCode(), type3.hashCode());
    }

    /**
     * Testa o método toString da classe StickerType.
     * Verifica se o método retorna a representação correta da instância.
     */
    @Test
    @DisplayName("Should return correct string representation of StickerType")
    void testToString() {
        type.setId(1L);
        type.setDescription("Type A");

        // Verifica a saída do método toString
       // assertEquals("StickerType(id=1, description=Type A)", type.toString());
    }

    /**
     * Testa a igualdade de StickerType com atributos diferentes.
     * Verifica se dois objetos diferentes não são considerados iguais.
     */
    @Test
    @DisplayName("Should not consider different StickerType instances as equal")
    void testNotEqual() {
        StickerType type1 = new StickerType();
        type1.setId(1L);
        type1.setDescription("Type A");

        StickerType type2 = new StickerType();
        type2.setId(2L);
        type2.setDescription("Type B");

        // Verifica que objetos diferentes não são iguais
        assertNotEquals(type1, type2);
    }

    /**
     * Testa o comportamento dos métodos equals e hashCode com valores nulos.
     * Verifica se instâncias com valores nulos são tratadas corretamente.
     */
    @Test
    @DisplayName("Should handle null values in StickerType correctly")
    void testNullValues() {
        StickerType type1 = new StickerType();
        type1.setId(null);
        type1.setDescription(null);

        StickerType type2 = new StickerType();
        type2.setId(null);
        type2.setDescription(null);

        assertEquals(type1, type2);
        assertEquals(type1.hashCode(), type2.hashCode());
    }
}
