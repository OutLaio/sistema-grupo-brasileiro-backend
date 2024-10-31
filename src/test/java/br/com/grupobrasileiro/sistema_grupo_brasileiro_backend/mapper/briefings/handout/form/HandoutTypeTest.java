package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.form;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.HandoutType;

public class HandoutTypeTest {

    @Test
    public void testEqualsAndHashCode() {
        HandoutType handoutType1 = new HandoutType(1L, "Tipo A");
        HandoutType handoutType2 = new HandoutType(1L, "Tipo A");
        HandoutType handoutType3 = new HandoutType(2L, "Tipo B");

        // Testando a igualdade
        assertEquals(handoutType1, handoutType2, "Os objetos devem ser iguais");
        assertNotEquals(handoutType1, handoutType3, "Os objetos não devem ser iguais");

        // Testando o hashCode
        assertEquals(handoutType1.hashCode(), handoutType2.hashCode(), "Os hashCodes devem ser iguais");
        assertNotEquals(handoutType1.hashCode(), handoutType3.hashCode(), "Os hashCodes não devem ser iguais");
    }

    @Test
    public void testToString() {
        HandoutType handoutType = new HandoutType(1L, "Tipo A");
        String expectedString = "HandoutType{id=1, description='Tipo A'}";

        // Testando o método toString
        assertEquals(expectedString, handoutType.toString(), "A representação em string deve estar correta");
    }

    @Test
    public void testConstructorAndGetters() {
        HandoutType handoutType = new HandoutType(1L, "Tipo A");

        // Testando os construtores e métodos getters
        assertEquals(1L, handoutType.getId(), "O ID deve ser 1");
        assertEquals("Tipo A", handoutType.getDescription(), "A descrição deve ser 'Tipo A'");
    }
}