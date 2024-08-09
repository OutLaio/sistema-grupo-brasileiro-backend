package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;

public class UpdateUserFormTest {

    private final Faker faker = new Faker();
    private final Validator validator;

    public UpdateUserFormTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void testValidForm() {
        String name = faker.name().firstName();
        String lastname = faker.name().lastName();
        String phonenumber = faker.phoneNumber().phoneNumber();
        String occupation = faker.job().title();
        String nop = faker.lorem().word();
        String sector = faker.company().industry();

        UpdateUserForm form = new UpdateUserForm(name, lastname, phonenumber, occupation, nop, sector);

        Set<ConstraintViolation<UpdateUserForm>> violations = validator.validate(form);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testNullName() {
        String lastname = faker.name().lastName();
        String phonenumber = faker.phoneNumber().phoneNumber();
        String occupation = faker.job().title();
        String nop = faker.lorem().word();
        String sector = faker.company().industry();

        UpdateUserForm form = new UpdateUserForm(null, lastname, phonenumber, occupation, nop, sector);

        Set<ConstraintViolation<UpdateUserForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testNullLastname() {
        String name = faker.name().firstName();
        String phonenumber = faker.phoneNumber().phoneNumber();
        String occupation = faker.job().title();
        String nop = faker.lorem().word();
        String sector = faker.company().industry();

        UpdateUserForm form = new UpdateUserForm(name, null, phonenumber, occupation, nop, sector);

        Set<ConstraintViolation<UpdateUserForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testNullPhonenumber() {
        String name = faker.name().firstName();
        String lastname = faker.name().lastName();
        String occupation = faker.job().title();
        String nop = faker.lorem().word();
        String sector = faker.company().industry();

        UpdateUserForm form = new UpdateUserForm(name, lastname, null, occupation, nop, sector);

        Set<ConstraintViolation<UpdateUserForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testNullOccupation() {
        String name = faker.name().firstName();
        String lastname = faker.name().lastName();
        String phonenumber = faker.phoneNumber().phoneNumber();
        String nop = faker.lorem().word();
        String sector = faker.company().industry();

        UpdateUserForm form = new UpdateUserForm(name, lastname, phonenumber, null, nop, sector);

        Set<ConstraintViolation<UpdateUserForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testNullNop() {
        String name = faker.name().firstName();
        String lastname = faker.name().lastName();
        String phonenumber = faker.phoneNumber().phoneNumber();
        String occupation = faker.job().title();
        String sector = faker.company().industry();

        UpdateUserForm form = new UpdateUserForm(name, lastname, phonenumber, occupation, null, sector);

        Set<ConstraintViolation<UpdateUserForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testNullSector() {
        String name = faker.name().firstName();
        String lastname = faker.name().lastName();
        String phonenumber = faker.phoneNumber().phoneNumber();
        String occupation = faker.job().title();
        String nop = faker.lorem().word();

        UpdateUserForm form = new UpdateUserForm(name, lastname, phonenumber, occupation, nop, null);

        Set<ConstraintViolation<UpdateUserForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testNameTooLong() {
        String longName = faker.lorem().characters(256);
        String lastname = faker.name().lastName();
        String phonenumber = faker.phoneNumber().phoneNumber();
        String occupation = faker.job().title();
        String nop = faker.lorem().word();
        String sector = faker.company().industry();

        UpdateUserForm form = new UpdateUserForm(longName, lastname, phonenumber, occupation, nop, sector);

        Set<ConstraintViolation<UpdateUserForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testLastnameTooLong() {
        String name = faker.name().firstName();
        String longLastname = faker.lorem().characters(256);
        String phonenumber = faker.phoneNumber().phoneNumber();
        String occupation = faker.job().title();
        String nop = faker.lorem().word();
        String sector = faker.company().industry();

        UpdateUserForm form = new UpdateUserForm(name, longLastname, phonenumber, occupation, nop, sector);

        Set<ConstraintViolation<UpdateUserForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testPhonenumberTooLong() {
        String name = faker.name().firstName();
        String lastname = faker.name().lastName();
        String longPhonenumber = faker.lorem().characters(256);
        String occupation = faker.job().title();
        String nop = faker.lorem().word();
        String sector = faker.company().industry();

        UpdateUserForm form = new UpdateUserForm(name, lastname, longPhonenumber, occupation, nop, sector);

        Set<ConstraintViolation<UpdateUserForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testOccupationTooLong() {
        String name = faker.name().firstName();
        String lastname = faker.name().lastName();
        String phonenumber = faker.phoneNumber().phoneNumber();
        String longOccupation = faker.lorem().characters(256);
        String nop = faker.lorem().word();
        String sector = faker.company().industry();

        UpdateUserForm form = new UpdateUserForm(name, lastname, phonenumber, longOccupation, nop, sector);

        Set<ConstraintViolation<UpdateUserForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testNopTooLong() {
        String name = faker.name().firstName();
        String lastname = faker.name().lastName();
        String phonenumber = faker.phoneNumber().phoneNumber();
        String occupation = faker.job().title();
        String longNop = faker.lorem().characters(256);
        String sector = faker.company().industry();

        UpdateUserForm form = new UpdateUserForm(name, lastname, phonenumber, occupation, longNop, sector);

        Set<ConstraintViolation<UpdateUserForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testSectorTooLong() {
        String name = faker.name().firstName();
        String lastname = faker.name().lastName();
        String phonenumber = faker.phoneNumber().phoneNumber();
        String occupation = faker.job().title();
        String nop = faker.lorem().word();
        String longSector = faker.lorem().characters(256);

        UpdateUserForm form = new UpdateUserForm(name, lastname, phonenumber, occupation, nop, longSector);

        Set<ConstraintViolation<UpdateUserForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }
}
