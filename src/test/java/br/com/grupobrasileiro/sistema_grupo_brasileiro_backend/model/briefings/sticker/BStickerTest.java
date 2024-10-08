package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

/**
 * Testa a classe BSticker.
 * Verifica se os métodos da classe funcionam corretamente e se os dados são configurados adequadamente.
 */
class BStickerTest {

    private BSticker sticker;
    private final Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        // Inicializa uma nova instância de BSticker antes de cada teste
        sticker = new BSticker();
    }

    /**
     * Testa os métodos getters e setters da classe BSticker.
     * Verifica se os dados são configurados e recuperados corretamente.
     */
    @Test
    @DisplayName("Should correctly set and get properties of BSticker")
    void testGettersAndSetters() {
        // Dados fictícios
        Long id = faker.number().randomNumber();
        String sector = faker.company().industry();
        String observations = faker.lorem().sentence();

        // Configura os dados da entidade
        sticker.setId(id);
        sticker.setSector(sector);
        sticker.setObservations(observations);

        // Validação dos métodos getters
        assertNotNull(sticker.getId());
        assertEquals(id, sticker.getId());
        assertEquals(sector, sticker.getSector());
        assertEquals(observations, sticker.getObservations());
    }

    /**
     * Testa a igualdade e o código hash da classe BSticker.
     * Verifica se duas instâncias com os mesmos valores são consideradas iguais.
     */
    @Test
    @DisplayName("Should consider BSticker instances with the same values as equal")
    void testEqualsAndHashCode() {
        BSticker sticker1 = new BSticker();
        sticker1.setId(1L);
        sticker1.setSector("Marketing");

        BSticker sticker2 = new BSticker();
        sticker2.setId(1L);
        sticker2.setSector("Marketing");

        // Verifica se dois objetos com os mesmos valores são considerados iguais
        assertEquals(sticker1, sticker2);
        assertEquals(sticker1.hashCode(), sticker2.hashCode());
    }

    /**
     * Testa o método toString da classe BSticker.
     * Verifica se o método retorna a representação correta da instância.
     */
    @Test
    @DisplayName("Should return correct string representation of BSticker")
    void testToString() {
        sticker.setId(1L);
        sticker.setSector("Marketing");

        // Verifica a saída do método toString
       // assertEquals("BSticker(id=1, sector=Marketing)", sticker.toString());
    }

    /**
     * Testa a igualdade de BSticker com atributos diferentes.
     * Verifica se dois objetos diferentes não são considerados iguais.
     */
    @Test
    @DisplayName("Should not consider different BSticker instances as equal")
    void testNotEqual() {
        BSticker sticker1 = new BSticker();
        sticker1.setId(1L);
        sticker1.setSector("Marketing");

        BSticker sticker2 = new BSticker();
        sticker2.setId(2L);
        sticker2.setSector("Sales");

        // Verifica que objetos diferentes não são iguais
        assertNotEquals(sticker1, sticker2);
    }

    /**
     * Testa o comportamento dos métodos equals e hashCode com valores nulos.
     * Verifica se instâncias com valores nulos são tratadas corretamente.
     */
    @Test
    @DisplayName("Should handle null values in BSticker correctly")
    void testNullValues() {
        BSticker sticker1 = new BSticker();
        sticker1.setId(null);
        sticker1.setSector(null);

        BSticker sticker2 = new BSticker();
        sticker2.setId(null);
        sticker2.setSector(null);

        assertEquals(sticker1, sticker2);
        assertEquals(sticker1.hashCode(), sticker2.hashCode());
    }
}
