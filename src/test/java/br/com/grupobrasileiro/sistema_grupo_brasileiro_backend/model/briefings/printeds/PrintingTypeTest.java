package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

/**
 * Testa a classe PrintingType.
 * Verifica se os métodos da classe funcionam corretamente e se os dados são configurados adequadamente.
 */
public class PrintingTypeTest {

    private PrintingType printingType;
    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();

        // Criando instância de PrintingType
        printingType = new PrintingType();
        printingType.setId(faker.number().randomNumber());
        printingType.setDescription(faker.commerce().material());
    }

    /**
     * Testa a criação da instância da classe PrintingType.
     * Verifica se o ID e a descrição são definidos corretamente após a configuração inicial no método setUp.
     */
    @Test
    @DisplayName("Should correctly set properties of PrintingType")
    void testPrintingTypeCreation() {
        // Verifica se o ID foi gerado corretamente
        assertThat(printingType.getId()).isNotNull();

        // Verifica se a descrição foi atribuída corretamente
        assertThat(printingType.getDescription()).isNotNull().isNotEmpty();
    }

    /**
     * Testa a igualdade entre instâncias da classe PrintingType.
     * Verifica se instâncias com o mesmo ID são consideradas iguais.
     */
    @Test
    @DisplayName("Should consider PrintingType instances with the same ID as equal")
    void testPrintingTypeEquality() {
        // Criando uma segunda instância com o mesmo ID
        PrintingType anotherPrintingType = new PrintingType();
        anotherPrintingType.setId(printingType.getId());
        anotherPrintingType.setDescription(faker.commerce().material());

        // Verifica se as instâncias com o mesmo ID são consideradas iguais
        assertThat(printingType).isEqualTo(anotherPrintingType);
    }

    /**
     * Testa o método toString da classe PrintingType.
     * Verifica se o método toString retorna uma representação correta da instância.
     */
    @Test
    @DisplayName("Should return correct string representation of PrintingType")
    void testPrintingTypeToString() {
        // Verifica o método toString
        String printingTypeString = printingType.toString();
        assertThat(printingTypeString).contains(printingType.getId().toString());
       // assertThat(printingTypeString).contains(printingType.getDescription());
    }

    /**
     * Testa os métodos setters e getters da classe PrintingType.
     * Verifica se os métodos setId e setDescription definem corretamente os atributos
     * e se os métodos getId e getDescription retornam os valores esperados.
     */
    @Test
    @DisplayName("Should set and get properties correctly")
    void testSettersAndGetters() {
        Long id = faker.number().randomNumber();
        String description = faker.commerce().material();

        printingType.setId(id);
        printingType.setDescription(description);

        assertThat(printingType.getId()).isEqualTo(id);
        assertThat(printingType.getDescription()).isEqualTo(description);
    }

    /**
     * Testa os métodos hashCode e equals da classe PrintingType.
     * Verifica se duas instâncias com os mesmos valores de id são iguais e se têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should correctly implement equals and hashCode")
    void testEqualsAndHashCode() {
        Long id = 1L;

        PrintingType printingType1 = new PrintingType();
        printingType1.setId(id);
        PrintingType printingType2 = new PrintingType();
        printingType2.setId(id);
        PrintingType printingType3 = new PrintingType();
        printingType3.setId(2L); // Instância com id diferente

        assertThat(printingType1).isEqualTo(printingType2);
        assertThat(printingType1.hashCode()).isEqualTo(printingType2.hashCode());

        // Verifica que objetos diferentes não são iguais
        assertThat(printingType1).isNotEqualTo(printingType3);
        assertThat(printingType1.hashCode()).isNotEqualTo(printingType3.hashCode());
    }
}
