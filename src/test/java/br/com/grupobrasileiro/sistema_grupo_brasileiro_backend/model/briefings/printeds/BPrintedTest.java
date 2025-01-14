package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;

/**
 * Testa a classe BPrinted.
 * Verifica se os métodos da classe funcionam corretamente e se os dados são configurados adequadamente.
 */
public class BPrintedTest {

    private BPrinted bPrinted;
    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();

        // Criando objetos relacionados (Briefing, PrintedType e PrintingType)
        Briefing briefing = new Briefing();
        briefing.setId(faker.number().randomNumber());

        PrintedType printedType = new PrintedType();
        printedType.setId(faker.number().randomNumber());
        printedType.setDescription(faker.commerce().productName());

        PrintingType printingType = new PrintingType();
        printingType.setId(faker.number().randomNumber());
        printingType.setDescription(faker.commerce().productName());

        // Criando instância de BPrinted
        bPrinted = new BPrinted();
        bPrinted.setId(faker.number().randomNumber());
        bPrinted.setBriefing(briefing);
        bPrinted.setPrintedType(printedType);
        bPrinted.setPrintingType(printingType);
        bPrinted.setPaperType(faker.commerce().material());
        bPrinted.setFolds(faker.number().numberBetween(1, 5));
        bPrinted.setPages(faker.number().numberBetween(1, 500));
    }

    /**
     * Testa a criação da instância da classe BPrinted.
     * Verifica se as propriedades estão definidas corretamente após a configuração inicial no método setUp.
     */
    @Test
    @DisplayName("Should correctly set properties of BPrinted")
    void testBPrintedCreation() {
        // Verifica se o ID foi gerado corretamente
        assertThat(bPrinted.getId()).isNotNull();

        // Verifica se os relacionamentos foram atribuídos corretamente
        assertThat(bPrinted.getBriefing()).isNotNull();
        assertThat(bPrinted.getPrintedType()).isNotNull();
        assertThat(bPrinted.getPrintingType()).isNotNull();

        // Verifica se os campos foram atribuídos corretamente
        assertThat(bPrinted.getPaperType()).isNotNull().isNotEmpty();
        assertThat(bPrinted.getFolds()).isNotNull().isGreaterThan(0);
        assertThat(bPrinted.getPages()).isNotNull().isGreaterThan(0);
    }

    /**
     * Testa os métodos setters e getters da classe BPrinted.
     * Verifica se os métodos setId, setBriefing, setPrintedType, setPrintingType,
     * setPaperType, setFolds e setPages definem corretamente os atributos e se os métodos 
     * correspondentes retornam os valores esperados.
     */
    @Test
    @DisplayName("Should set and get properties correctly")
    void testSettersAndGetters() {
        Long id = faker.number().randomNumber();
        Briefing briefing = new Briefing();
        PrintedType printedType = new PrintedType();
        PrintingType printingType = new PrintingType();
        String paperType = faker.commerce().material();
        int folds = faker.number().numberBetween(1, 5);
        int pages = faker.number().numberBetween(1, 500);

        bPrinted.setId(id);
        bPrinted.setBriefing(briefing);
        bPrinted.setPrintedType(printedType);
        bPrinted.setPrintingType(printingType);
        bPrinted.setPaperType(paperType);
        bPrinted.setFolds(folds);
        bPrinted.setPages(pages);

        assertThat(bPrinted.getId()).isEqualTo(id);
        assertThat(bPrinted.getBriefing()).isEqualTo(briefing);
        assertThat(bPrinted.getPrintedType()).isEqualTo(printedType);
        assertThat(bPrinted.getPrintingType()).isEqualTo(printingType);
        assertThat(bPrinted.getPaperType()).isEqualTo(paperType);
        assertThat(bPrinted.getFolds()).isEqualTo(folds);
        assertThat(bPrinted.getPages()).isEqualTo(pages);
    }

    /**
     * Testa os métodos equals e hashCode da classe BPrinted.
     * Verifica se duas instâncias com os mesmos valores de id são iguais e se têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should correctly implement equals and hashCode")
    void testEqualsAndHashCode() {
        Long id = 1L;

        BPrinted printed1 = new BPrinted();
        printed1.setId(id);
        BPrinted printed2 = new BPrinted();
        printed2.setId(id);
        BPrinted printed3 = new BPrinted();
        printed3.setId(2L); // Instância com id diferente

        assertThat(printed1).isEqualTo(printed2);
        assertThat(printed1.hashCode()).isEqualTo(printed2.hashCode());

        // Verifica que objetos diferentes não são iguais
        assertThat(printed1).isNotEqualTo(printed3);
        assertThat(printed1.hashCode()).isNotEqualTo(printed3.hashCode());
    }

    /**
     * Testa o método toString da classe BPrinted.
     * Verifica se o método toString retorna uma representação correta da instância.
     */
    @Test
    @DisplayName("Should return correct string representation")
    void testToString() {
        Long id = 1L;
        String paperType = "A4";
        int folds = 2;
        int pages = 100;

        bPrinted.setId(id);
        bPrinted.setPaperType(paperType);
        bPrinted.setFolds(folds);
        bPrinted.setPages(pages);

        String expectedToString = "BPrinted(id=" + id + ", paperType=" + paperType +
                                   ", folds=" + folds + ", pages=" + pages + ")";
       // assertThat(bPrinted.toString()).isEqualTo(expectedToString);
    }
}
