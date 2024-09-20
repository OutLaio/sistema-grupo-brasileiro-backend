//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums;
//
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.Arrays;
//
//public class ProjectStatusEnumTest {
//
//    @Test
//    void testGetCode() {
//        for (ProjectStatusEnum status : ProjectStatusEnum.values()) {
//            assertNotNull(status.getCode(), "O código não deve ser nulo.");
//            assertFalse(status.getCode().isEmpty(), "O código não deve ser uma string vazia.");
//        }
//    }
//
//    @Test
//    void testGetDescription() {
//        for (ProjectStatusEnum status : ProjectStatusEnum.values()) {
//            assertNotNull(status.getDescription(), "A descrição não deve ser nula.");
//            assertFalse(status.getDescription().isEmpty(), "A descrição não deve ser uma string vazia.");
//        }
//    }
//
//    @Test
//    void testFromCodeValid() {
//        for (ProjectStatusEnum status : ProjectStatusEnum.values()) {
//            ProjectStatusEnum result = ProjectStatusEnum.fromCode(status.getCode());
//            assertEquals(status, result, "O enum retornado deve corresponder ao enum de entrada.");
//        }
//    }
//
//    @Test
//    void testFromCodeInvalid() {
//        String invalidCode = "XX";
//        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
//            ProjectStatusEnum.fromCode(invalidCode);
//        });
//        assertEquals("Invalid code: " + invalidCode, thrown.getMessage(), "A mensagem de exceção deve ser correta.");
//    }
//
//    @Test
//    void testEnumValues() {
//        assertTrue(Arrays.asList(ProjectStatusEnum.values()).containsAll(Arrays.asList(
//            ProjectStatusEnum.A_FAZER,
//            ProjectStatusEnum.EM_ANDAMENTO,
//            ProjectStatusEnum.A_APROVAR,
//            ProjectStatusEnum.APROVADO,
//            ProjectStatusEnum.EM_CONFEC,
//            ProjectStatusEnum.CONCLUIDO
//        )), "Todos os statuses esperados devem estar presentes no enum.");
//    }
//
//    @Test
//    void testGetCodeForSpecificEnum() {
//        ProjectStatusEnum status = ProjectStatusEnum.A_FAZER;
//        assertEquals("AF", status.getCode(), "O código para o enum específico está incorreto.");
//    }
//
//    @Test
//    void testGetDescriptionForSpecificEnum() {
//        ProjectStatusEnum status = ProjectStatusEnum.A_FAZER;
//        assertEquals("A Fazer", status.getDescription(), "A descrição para o enum específico está incorreta.");
//    }
//
//    @Test
//    void testEnumCodeAndDescriptionConsistency() {
//        for (ProjectStatusEnum status : ProjectStatusEnum.values()) {
//            assertEquals(status.getDescription(), ProjectStatusEnum.fromCode(status.getCode()).getDescription(),
//                "A descrição obtida a partir do código deve corresponder à descrição original.");
//        }
//    }
//}
