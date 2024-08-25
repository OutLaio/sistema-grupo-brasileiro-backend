package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import com.github.javafaker.Faker;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BAgencyBoardFormTest {

    private Validator validator;
    private Faker faker;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        faker = new Faker(); // Inicializa o Java Faker
    }

    @Test
    public void testValidBAgencyBoardForm() {
        BAgencyBoardForm form = new BAgencyBoardForm(
            faker.address().streetAddress(), // boardLocation
            faker.bool().bool(), // companySharing
            faker.commerce().department(), // boardType
            faker.commerce().material(), // material
            faker.lorem().sentence(), // observations
            faker.number().randomNumber() // projectId
        );

        Set<ConstraintViolation<BAgencyBoardForm>> violations = validator.validate(form);
        assertTrue(violations.isEmpty(), "There should be no validation errors");
    }

    @Test
    public void testInvalidBoardLocation() {
        BAgencyBoardForm form = new BAgencyBoardForm(
            "", // Invalid boardLocation
            faker.bool().bool(),
            faker.commerce().department(),
            faker.commerce().material(),
            faker.lorem().sentence(),
            faker.number().randomNumber()
        );

        Set<ConstraintViolation<BAgencyBoardForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty(), "There should be validation errors");
        assertEquals("BoardLocation is required!", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidCompanySharing() {
        BAgencyBoardForm form = new BAgencyBoardForm(
            faker.address().streetAddress(),
            null, // Invalid companySharing
            faker.commerce().department(),
            faker.commerce().material(),
            faker.lorem().sentence(),
            faker.number().randomNumber()
        );

        Set<ConstraintViolation<BAgencyBoardForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty(), "There should be validation errors");
        assertEquals("CompanySharing is required!", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidBoardType() {
        BAgencyBoardForm form = new BAgencyBoardForm(
            faker.address().streetAddress(),
            faker.bool().bool(),
            "", // Invalid boardType
            faker.commerce().material(),
            faker.lorem().sentence(),
            faker.number().randomNumber()
        );

        Set<ConstraintViolation<BAgencyBoardForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty(), "There should be validation errors");
        assertEquals("BoardType is required!", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidMaterial() {
        BAgencyBoardForm form = new BAgencyBoardForm(
            faker.address().streetAddress(),
            faker.bool().bool(),
            faker.commerce().department(),
            "", // Invalid material
            faker.lorem().sentence(),
            faker.number().randomNumber()
        );

        Set<ConstraintViolation<BAgencyBoardForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty(), "There should be validation errors");
        assertEquals("Material is required!", violations.iterator().next().getMessage());
    }

    @Test
    public void testValidProjectId() {
        BAgencyBoardForm form = new BAgencyBoardForm(
            faker.address().streetAddress(),
            faker.bool().bool(),
            faker.commerce().department(),
            faker.commerce().material(),
            faker.lorem().sentence(),
            null // Invalid projectId
        );

        Set<ConstraintViolation<BAgencyBoardForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty(), "There should be validation errors");
        assertEquals("ProjectId must be a valid ID", violations.iterator().next().getMessage());
    }
}
