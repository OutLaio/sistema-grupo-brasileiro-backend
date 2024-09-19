package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;

/**
 * Testa a classe PrintingShirtType.
 * Verifica o funcionamento dos métodos gerados pelo Lombok, construtores e métodos toString.
 */
public class PrintingShirtTypeTest {

    private final Faker faker = new Faker();

    /**
     * Testa o construtor padrão da classe PrintingShirtType.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    void testDefaultConstructor() {
        PrintingShirtType printingShirtType = new PrintingShirtType();
        assertThat(printingShirtType).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe PrintingShirtType.
     * Verifica se o construtor com parâmetros define corretamente os atributos id e description.
     */
    @Test
    void testParameterizedConstructor() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        PrintingShirtType printingShirtType = new PrintingShirtType(id, description);
        assertThat(printingShirtType.getId()).isEqualTo(id);
        assertThat(printingShirtType.getDescription()).isEqualTo(description);
    }

    /**
     * Testa os métodos setters e getters da classe PrintingShirtType.
     * Verifica se os métodos setId e setDescription definem corretamente os atributos
     * e se os métodos getId e getDescription retornam os valores esperados.
     */
    @Test
    void testSettersAndGetters() {
        PrintingShirtType printingShirtType = new PrintingShirtType();
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        printingShirtType.setId(id);
        printingShirtType.setDescription(description);

        assertThat(printingShirtType.getId()).isEqualTo(id);
        assertThat(printingShirtType.getDescription()).isEqualTo(description);
    }

    /**
     * Testa os métodos equals e hashCode da classe PrintingShirtType.
     * Verifica se duas instâncias com os mesmos valores de id e description são iguais
     * e se têm o mesmo hashCode.
     */
    @Test
    void testEqualsAndHashCode() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        PrintingShirtType printingShirtType1 = new PrintingShirtType(id, description);
        PrintingShirtType printingShirtType2 = new PrintingShirtType(id, description);
        PrintingShirtType printingShirtType3 = new PrintingShirtType(id + 1, description); // Instância com id diferente

        assertThat(printingShirtType1).isEqualTo(printingShirtType2);
        assertThat(printingShirtType1.hashCode()).isEqualTo(printingShirtType2.hashCode());

        // Verifica que objetos diferentes não são iguais
        assertThat(printingShirtType1).isNotEqualTo(printingShirtType3);
        assertThat(printingShirtType1.hashCode()).isNotEqualTo(printingShirtType3.hashCode());
    }

    /**
     * Testa o método toString da classe PrintingShirtType.
     * Verifica se o método toString retorna uma representação correta da instância
     * com o valor do id.
     */
    @Test
    void testToString() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        PrintingShirtType printingShirtType = new PrintingShirtType(id, description);
        String expectedToString = "PrintingShirtType(id=" + id + ")";
        assertThat(printingShirtType.toString()).contains(expectedToString);
    }
}
