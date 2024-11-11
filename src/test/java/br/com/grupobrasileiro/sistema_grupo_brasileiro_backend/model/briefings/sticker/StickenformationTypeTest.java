package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa a classe StickerInformationType.
 * Verifica se os métodos da classe funcionam corretamente e se os dados são configurados adequadamente.
 */
class StickerInformationTypeTest {

    private StickerInformationType type;
    private final Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        // Inicializa uma nova instância de StickerInformationType antes de cada teste
        type = new StickerInformationType();
    }

    /**
     * Testa os métodos getters e setters da classe StickerInformationType.
     * Verifica se os dados são configurados e recuperados corretamente.
     */
    @Test
    @DisplayName("Should correctly set and get properties of StickerInformationType")
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
     * Testa a igualdade e o código hash da classe StickerInformationType.
     * Verifica se duas instâncias com os mesmos valores são consideradas iguais.
     */
    @Test
    @DisplayName("Should consider StickerInformationType instances with the same values as equal")
    void testEqualsAndHashCode() {
        StickerInformationType type1 = new StickerInformationType();
        type1.setId(1L);
        type1.setDescription("Description A");

        StickerInformationType type2 = new StickerInformationType();
        type2.setId(1L);
        type2.setDescription("Description A");

        StickerInformationType type3 = new StickerInformationType();
        type3.setId(2L);
        type3.setDescription("Description B");

        // Verifica se dois objetos com os mesmos valores são considerados iguais
        assertEquals(type1, type2);
        assertEquals(type1.hashCode(), type2.hashCode());

        // Verifica se objetos com valores diferentes não são iguais
        assertNotEquals(type1, type3);
        assertNotEquals(type1.hashCode(), type3.hashCode());
    }

    /**
     * Testa o método toString da classe StickerInformationType.
     * Verifica se o método retorna a representação correta da instância.
     */
    @Test
    @DisplayName("Should return correct string representation of StickerInformationType")
    void testToString() {
        // Configura os dados para o teste
        Long id = 1L;
        String description = "Description A";
        type.setId(id);
        type.setDescription(description);

        // Cria a string esperada com base nos dados configurados
        String expectedToString = "StickerInformationType{id=" + id + ", description='" + description + "'}";

        // Verifica se o método toString retorna a string correta
        assertEquals(expectedToString, type.toString());
    }

    /**
     * Testa a igualdade de StickerInformationType com atributos diferentes.
     * Verifica se dois objetos diferentes não são considerados iguais.
     */
    @Test
    @DisplayName("Should not consider different StickerInformationType instances as equal")
    void testNotEqual() {
        StickerInformationType type1 = new StickerInformationType();
        type1.setId(1L);
        type1.setDescription("Description A");

        StickerInformationType type2 = new StickerInformationType();
        type2.setId(2L);
        type2.setDescription("Description B");

        // Verifica que objetos diferentes não são iguais
        assertNotEquals(type1, type2);
    }

    /**
     * Testa o comportamento dos métodos equals e hashCode com valores nulos.
     * Verifica se instâncias com valores nulos são tratadas corretamente.
     */
    @Test
    @DisplayName("Should handle null values in StickerInformationType correctly")
    void testNullValues() {
        StickerInformationType type1 = new StickerInformationType();
        type1.setId(null);
        type1.setDescription(null);

        StickerInformationType type2 = new StickerInformationType();
        type2.setId(null);
        type2.setDescription(null);

        assertEquals(type1, type2);
        assertEquals(type1.hashCode(), type2.hashCode());
    }
    
    
}
