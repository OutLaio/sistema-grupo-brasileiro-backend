package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

/**
 * Testa a classe PrintedType.
 * Verifica se os métodos da classe funcionam corretamente e se os dados são configurados adequadamente.
 */
public class PrintedTypeTest {

    private PrintedType printedType;
    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
        
        // Criando instância de PrintedType
        printedType = new PrintedType();
        printedType.setId(faker.number().randomNumber());
        printedType.setDescription(faker.commerce().productName());
    }

    /**
     * Testa a criação da instância da classe PrintedType.
     * Verifica se o ID e a descrição são definidos corretamente após a configuração inicial no método setUp.
     */
    @Test
    @DisplayName("Should correctly set properties of PrintedType")
    void testPrintedTypeCreation() {
        // Verifica se o ID foi gerado corretamente
        assertThat(printedType.getId()).isNotNull();

        // Verifica se a descrição foi atribuída corretamente
        assertThat(printedType.getDescription()).isNotNull().isNotEmpty();
    }

    /**
     * Testa a igualdade entre instâncias da classe PrintedType.
     * Verifica se instâncias com o mesmo ID são consideradas iguais.
     */
    @Test
    @DisplayName("Should consider PrintedType instances with the same ID and description as equal")
    void testPrintedTypeEquality() {
        // Criando uma segunda instância com o mesmo ID e description
        PrintedType anotherPrintedType = new PrintedType();
        anotherPrintedType.setId(printedType.getId());
        anotherPrintedType.setDescription(printedType.getDescription());

        // Verifica se as instâncias com o mesmo ID e description são consideradas iguais
        assertThat(printedType).isEqualTo(anotherPrintedType);
        assertThat(printedType.hashCode()).isEqualTo(anotherPrintedType.hashCode());

        // Criando uma instância com o mesmo ID mas description diferente
        PrintedType differentDescriptionType = new PrintedType();
        differentDescriptionType.setId(printedType.getId());
        differentDescriptionType.setDescription(faker.commerce().productName());

        // Verifica que instâncias com o mesmo ID mas descriptions diferentes não são iguais
        assertThat(printedType).isNotEqualTo(differentDescriptionType);

        // Verifica que instâncias com IDs diferentes não são iguais
        PrintedType differentIdType = new PrintedType();
        differentIdType.setId(printedType.getId() + 1);
        differentIdType.setDescription(printedType.getDescription());
        assertThat(printedType).isNotEqualTo(differentIdType);
    }

    /**
     * Testa o método toString da classe PrintedType.
     * Verifica se o método toString retorna uma representação correta da instância.
     */
    @Test
    @DisplayName("Should return correct string representation of PrintedType")
    void testPrintedTypeToString() {
        // Verifica o método toString
        String printedTypeString = printedType.toString();
        assertThat(printedTypeString).contains(printedType.getId().toString());
        //assertThat(printedTypeString).contains(printedType.getDescription());
    }

    /**
     * Testa os métodos setters e getters da classe PrintedType.
     * Verifica se os métodos setId e setDescription definem corretamente os atributos
     * e se os métodos getId e getDescription retornam os valores esperados.
     */
    @Test
    @DisplayName("Should set and get properties correctly")
    void testSettersAndGetters() {
        Long id = faker.number().randomNumber();
        String description = faker.commerce().productName();

        printedType.setId(id);
        printedType.setDescription(description);

        assertThat(printedType.getId()).isEqualTo(id);
        assertThat(printedType.getDescription()).isEqualTo(description);
    }

    /**
     * Testa os métodos hashCode e equals da classe PrintedType.
     * Verifica se duas instâncias com os mesmos valores de id são iguais e se têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should correctly implement equals and hashCode")
    void testEqualsAndHashCode() {
        Long id = 1L;

        PrintedType printedType1 = new PrintedType();
        printedType1.setId(id);
        PrintedType printedType2 = new PrintedType();
        printedType2.setId(id);
        PrintedType printedType3 = new PrintedType();
        printedType3.setId(2L); // Instância com id diferente

        assertThat(printedType1).isEqualTo(printedType2);
        assertThat(printedType1.hashCode()).isEqualTo(printedType2.hashCode());

        // Verifica que objetos diferentes não são iguais
        assertThat(printedType1).isNotEqualTo(printedType3);
        assertThat(printedType1.hashCode()).isNotEqualTo(printedType3.hashCode());
    }
}
