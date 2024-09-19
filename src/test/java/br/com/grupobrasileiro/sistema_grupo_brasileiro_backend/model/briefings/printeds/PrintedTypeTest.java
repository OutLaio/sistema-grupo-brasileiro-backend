package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    void testPrintedTypeCreation() {
        // Verifica se o ID foi gerado corretamente
        assertThat(printedType.getId()).isNotNull();

        // Verifica se a descrição foi atribuída corretamente
        assertThat(printedType.getDescription()).isNotNull().isNotEmpty();
    }

    @Test
    void testPrintedTypeEquality() {
        // Criando uma segunda instância com o mesmo ID
        PrintedType anotherPrintedType = new PrintedType();
        anotherPrintedType.setId(printedType.getId());
        anotherPrintedType.setDescription(faker.commerce().productName());

        // Verifica se as instâncias com o mesmo ID são consideradas iguais
        assertThat(printedType).isEqualTo(anotherPrintedType);
    }

    @Test
    void testPrintedTypeToString() {
        // Verifica o método toString
        String printedTypeString = printedType.toString();
        assertThat(printedTypeString).contains(printedType.getId().toString());
    }
}
