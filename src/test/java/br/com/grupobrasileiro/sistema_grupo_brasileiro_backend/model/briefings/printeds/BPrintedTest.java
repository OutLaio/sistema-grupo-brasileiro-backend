package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
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
}
