package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.form;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.measurements.form.MeasurementsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class BriefingFormTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        // Inicializa o Validator do Hibernate
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testBriefingFormValid() {
        // Criando um objeto MeasurementsForm válido
        MeasurementsForm measurementsForm = new MeasurementsForm(
                new BigDecimal("10.5"), // height
                new BigDecimal("20.0")  // length
        );

        // Criando um objeto BriefingForm válido
        BriefingForm briefingForm = new BriefingForm(
                "Detailed description",    // detailedDescription
                Collections.emptySet(),     // companies
                "Other company",            // otherCompany
                1L,                         // idBriefingType
                measurementsForm            // measurements
        );

        // Validando o objeto
        Set<ConstraintViolation<BriefingForm>> violations = validator.validate(briefingForm);

        // Verificando que não há violações de validação
        assertTrue(violations.isEmpty());
    }

    @Test
    void testBriefingFormBlankDescription() {
        // Criando um objeto MeasurementsForm válido
        MeasurementsForm measurementsForm = new MeasurementsForm(
                new BigDecimal("10.5"), // height
                new BigDecimal("20.0")  // length
        );

        // Criando um objeto BriefingForm com detailedDescription em branco
        BriefingForm briefingForm = new BriefingForm(
                "",                       // detailedDescription
                Collections.emptySet(),    // companies
                "Other company",           // otherCompany
                1L,                        // idBriefingType
                measurementsForm           // measurements
        );

        // Validando o objeto
        Set<ConstraintViolation<BriefingForm>> violations = validator.validate(briefingForm);

        // Verificando que houve violação de validação no campo detailedDescription
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("The detailed description cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void testBriefingFormNullBriefingType() {
        // Criando um objeto MeasurementsForm válido
        MeasurementsForm measurementsForm = new MeasurementsForm(
                new BigDecimal("10.5"), // height
                new BigDecimal("20.0")  // length
        );

        // Criando um objeto BriefingForm com idBriefingType nulo
        BriefingForm briefingForm = new BriefingForm(
                "Detailed description",    // detailedDescription
                Collections.emptySet(),     // companies
                "Other company",            // otherCompany
                null,                       // idBriefingType
                measurementsForm            // measurements
        );

        // Validando o objeto
        Set<ConstraintViolation<BriefingForm>> violations = validator.validate(briefingForm);

        // Verificando que houve violação de validação no campo idBriefingType
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("The briefing type cannot be null", violations.iterator().next().getMessage());
    }

    @Test
    void testBriefingFormValidWithEmptyCompanies() {
        // Criando um objeto MeasurementsForm válido
        MeasurementsForm measurementsForm = new MeasurementsForm(
                new BigDecimal("10.5"), // height
                new BigDecimal("20.0")  // length
        );

        // Criando um objeto BriefingForm com companies como um conjunto vazio
        BriefingForm briefingForm = new BriefingForm(
                "Detailed description",    // detailedDescription
                Collections.emptySet(),     // companies
                "Other company",            // otherCompany
                1L,                         // idBriefingType
                measurementsForm            // measurements
        );

        // Validando o objeto
        Set<ConstraintViolation<BriefingForm>> violations = validator.validate(briefingForm);

        // Verificando que não há violações de validação
        assertTrue(violations.isEmpty());
    }
}
