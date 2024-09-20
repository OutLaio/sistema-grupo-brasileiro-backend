//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;
//
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.Validation;
//import jakarta.validation.Validator;
//import jakarta.validation.ValidatorFactory;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class ProjectFormTest {
//
//    private Validator validator;
//
//    @BeforeEach
//    public void setUp() {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        validator = factory.getValidator();
//    }
//
//    @Test
//    public void testValidProjectForm() {
//        ProjectForm projectForm = new ProjectForm("Valid Title", "Valid Description");
//        Set<ConstraintViolation<ProjectForm>> violations = validator.validate(projectForm);
//        assertTrue(violations.isEmpty(), "Não deve haver violações de validação para um ProjectForm válido");
//    }
//
//    @Test
//    public void testInvalidProjectForm() {
//        ProjectForm projectForm = new ProjectForm("", ""); // título e descrição em branco
//        Set<ConstraintViolation<ProjectForm>> violations = validator.validate(projectForm);
//
//        assertEquals(2, violations.size(), "Deve haver 2 violações de validação");
//
//        for (ConstraintViolation<ProjectForm> violation : violations) {
//            if (violation.getPropertyPath().toString().equals("title")) {
//                assertEquals("O título é obrigatório!", violation.getMessage());
//            } else if (violation.getPropertyPath().toString().equals("description")) {
//                assertEquals("Description is required!", violation.getMessage());
//            }
//        }
//    }
//}
