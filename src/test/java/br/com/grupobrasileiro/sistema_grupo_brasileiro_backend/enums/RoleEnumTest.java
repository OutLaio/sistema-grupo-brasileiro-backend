//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums;
//
//import com.github.javafaker.Faker;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class RoleEnumTest {
//
//	@Test
//	void testGetCode() {
//
//	    for (RoleEnum role : RoleEnum.values()) {
//	        assertNotNull(role.getCode(), "O código não deve ser nulo.");
//	        assertTrue(role.getCode() >= 0, "O código deve ser um valor não negativo.");  // Permitir zero como valor válido
//	    }
//	}
//
//
//    @Test
//    void testGetDescription() {
//        // Checks whether the getDescription() method returns non-null and correct descriptions for each enum value
//        for (RoleEnum role : RoleEnum.values()) {
//            assertNotNull(role.getDescription(), "A descrição não deve ser nula.");
//            assertFalse(role.getDescription().isEmpty(), "A descrição não deve ser uma string vazia.");
//        }
//    }
//
//    @Test
//    void testFromCodeValid() {
//        // Checks if the fromCode(Integer code) method returns the correct enum value for valid codes
//        for (RoleEnum role : RoleEnum.values()) {
//            RoleEnum result = RoleEnum.fromCode(role.getCode());
//            assertEquals(role, result, "O enum retornado deve corresponder ao enum de entrada.");
//        }
//    }
//
//    @Test
//    void testFromCodeInvalid() {
//    	// Test the fromCode method with an invalid code
//        Integer invalidCode = Faker.instance().number().numberBetween(4, 100); // Code out of valid range
//        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
//            RoleEnum.fromCode(invalidCode);
//        });
//        assertEquals("Invalid code: " + invalidCode, thrown.getMessage(), "A mensagem de exceção deve ser correta.");
//    }
//
//    @Test
//    void testEnumValues() {
//    	// Check if the enumeration contains exactly the expected values
//        assertEquals(3, RoleEnum.values().length, "Deve haver exatamente 3 valores na enumeração RoleEnum.");
//    }
//
//    @Test
//    void testEnumCodeAndDescriptionConsistency() {
//    	// Check if the description for each code is consistent
//        for (RoleEnum role : RoleEnum.values()) {
//            assertEquals(role.getDescription(), RoleEnum.fromCode(role.getCode()).getDescription(),
//                "A descrição para o código deve ser consistente.");
//        }
//    }
//}
//
