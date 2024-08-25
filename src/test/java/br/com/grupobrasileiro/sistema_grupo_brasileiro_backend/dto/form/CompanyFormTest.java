package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class CompanyFormTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidCompanyForm() {
        CompanyForm form = new CompanyForm("Valid Company");

        Set<ConstraintViolation<CompanyForm>> violations = validator.validate(form);
        assertTrue(violations.isEmpty(), "There should be no validation errors");
    }

    @Test
    public void testCompanyFormWithBlankName() {
        CompanyForm form = new CompanyForm("");

        Set<ConstraintViolation<CompanyForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty(), "There should be validation errors");
        assertEquals(1, violations.size(), "There should be one validation error");
        ConstraintViolation<CompanyForm> violation = violations.iterator().next();
        assertEquals("Name is required!", violation.getMessage(), "The error message should match");
    }

    @Test
    public void testCompanyFormWithNullName() {
        CompanyForm form = new CompanyForm(null);

        Set<ConstraintViolation<CompanyForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty(), "There should be validation errors");
        assertEquals(1, violations.size(), "There should be one validation error");
        ConstraintViolation<CompanyForm> violation = violations.iterator().next();
        assertEquals("Name is required!", violation.getMessage(), "The error message should match");
    }
}
