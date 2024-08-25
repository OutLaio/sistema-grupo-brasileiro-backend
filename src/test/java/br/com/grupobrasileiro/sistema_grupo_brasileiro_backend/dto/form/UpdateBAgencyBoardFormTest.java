package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;

public class UpdateBAgencyBoardFormTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidUpdateBAgencyBoardForm() {
        UpdateBAgencyBoardForm form = new UpdateBAgencyBoardForm(
            "Valid Location",
            true,
            "ValidType",
            "ValidMaterial",
            "Some observations"
        );

        Set<ConstraintViolation<UpdateBAgencyBoardForm>> violations = validator.validate(form);
        assertTrue(violations.isEmpty(), "There should be no validation errors");
    }

    @Test
    public void testUpdateBAgencyBoardFormWithBlankBoardLocation() {
        UpdateBAgencyBoardForm form = new UpdateBAgencyBoardForm(
            "",
            true,
            "ValidType",
            "ValidMaterial",
            "Some observations"
        );

        Set<ConstraintViolation<UpdateBAgencyBoardForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty(), "There should be validation errors");
        assertEquals(1, violations.size(), "There should be one validation error");
        ConstraintViolation<UpdateBAgencyBoardForm> violation = violations.iterator().next();
        assertEquals("BoardLocation is required!", violation.getMessage(), "The error message should match");
    }

    @Test
    public void testUpdateBAgencyBoardFormWithNullCompanySharing() {
        UpdateBAgencyBoardForm form = new UpdateBAgencyBoardForm(
            "Valid Location",
            null,
            "ValidType",
            "ValidMaterial",
            "Some observations"
        );

        Set<ConstraintViolation<UpdateBAgencyBoardForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty(), "There should be validation errors");
        assertEquals(1, violations.size(), "There should be one validation error");
        ConstraintViolation<UpdateBAgencyBoardForm> violation = violations.iterator().next();
        assertEquals("CompanySharing is required!", violation.getMessage(), "The error message should match");
    }

    @Test
    public void testUpdateBAgencyBoardFormWithInvalidBoardType() {
        UpdateBAgencyBoardForm form = new UpdateBAgencyBoardForm(
            "Valid Location",
            true,
            "ThisBoardTypeIsWayTooLong", // Exceeds the max length of 20
            "ValidMaterial",
            "Some observations"
        );

        Set<ConstraintViolation<UpdateBAgencyBoardForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty(), "There should be validation errors");
        assertEquals(1, violations.size(), "There should be one validation error");
        ConstraintViolation<UpdateBAgencyBoardForm> violation = violations.iterator().next();
        assertEquals("BoardType must be less than 50 characters!", violation.getMessage(), "The error message should match");
    }

    @Test
    public void testUpdateBAgencyBoardFormWithBlankMaterial() {
        UpdateBAgencyBoardForm form = new UpdateBAgencyBoardForm(
            "Valid Location",
            true,
            "ValidType",
            "",
            "Some observations"
        );

        Set<ConstraintViolation<UpdateBAgencyBoardForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty(), "There should be validation errors");
        assertEquals(1, violations.size(), "There should be one validation error");
        ConstraintViolation<UpdateBAgencyBoardForm> violation = violations.iterator().next();
        assertEquals("Material is required!", violation.getMessage(), "The error message should match");
    }
}
