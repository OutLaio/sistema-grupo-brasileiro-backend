package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    void testPrintingTypeCreation() {
        // Verifica se o ID foi gerado corretamente
        assertThat(printingType.getId()).isNotNull();

        // Verifica se a descrição foi atribuída corretamente
        assertThat(printingType.getDescription()).isNotNull().isNotEmpty();
    }

    @Test
    void testPrintingTypeEquality() {
        // Criando uma segunda instância com o mesmo ID
        PrintingType anotherPrintingType = new PrintingType();
        anotherPrintingType.setId(printingType.getId());
        anotherPrintingType.setDescription(faker.commerce().material());

        // Verifica se as instâncias com o mesmo ID são consideradas iguais
        assertThat(printingType).isEqualTo(anotherPrintingType);
    }

    @Test
    void testPrintingTypeToString() {
        // Verifica o método toString
        String printingTypeString = printingType.toString();
        assertThat(printingTypeString).contains(printingType.getId().toString());
    }
}
