package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class MeasurementFormTest {

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidMeasurementForm() {
        MeasurementForm measurementForm = new MeasurementForm(10.0f, 20.0f, 1L);
        Set<ConstraintViolation<MeasurementForm>> violations = validator.validate(measurementForm);
        assertTrue(violations.isEmpty(), "Expected no constraint violations");
    }

    @Test
    public void testInvalidMeasurementForm() {
        MeasurementForm measurementForm = new MeasurementForm(0.0f, 0.0f, 1L); // Assuming zero is invalid
        Set<ConstraintViolation<MeasurementForm>> violations = validator.validate(measurementForm);
        assertTrue(violations.size() > 0, "Expected constraint violations");
    }
}

