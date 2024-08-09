package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectStatusEnumTest {

    @Test
    void testGetCode() {
        // Checks if the code returned by the getCode method is correct for each enum
        for (ProjectStatusEnum status : ProjectStatusEnum.values()) {
            assertNotNull(status.getCode(), "O código não deve ser nulo.");
            assertTrue(!status.getCode().isEmpty(), "O código não deve ser uma string vazia.");
        }
    }

    @Test
    void testGetDescription() {
        // Checks whether the description returned by the getDescription method is correct for each enum
        for (ProjectStatusEnum status : ProjectStatusEnum.values()) {
            assertNotNull(status.getDescription(), "A descrição não deve ser nula.");
            assertTrue(!status.getDescription().isEmpty(), "A descrição não deve ser uma string vazia.");
        }
    }

    @Test
    void testFromCodeValid() {
        // Test the fromCode method with valid codes
        for (ProjectStatusEnum status : ProjectStatusEnum.values()) {
            ProjectStatusEnum result = ProjectStatusEnum.fromCode(status.getCode());
            assertEquals(status, result, "O enum retornado deve corresponder ao enum de entrada.");
        }
    }

    @Test
    void testFromCodeInvalid() {
        // Testa o método fromCode com um código inválido
        String invalidCode = "XX";
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            ProjectStatusEnum.fromCode(invalidCode);
        });
        assertEquals("Invalid code: " + invalidCode, thrown.getMessage(), "A mensagem de exceção deve ser correta.");
    }
}

