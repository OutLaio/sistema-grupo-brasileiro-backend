package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserFormTest {

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    @Test
    void testValidUserForm() {
        String name = faker.name().firstName();
        String lastname = faker.name().lastName();
        String phonenumber = faker.phoneNumber().phoneNumber();
        String sector = faker.company().industry();
        String occupation = faker.job().title();
        String nop = faker.idNumber().valid();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 16, true, true, true); // Corrected line
        Integer role = RoleEnum.ROLE_CLIENT.getCode(); // Simulate valid role code

        UserForm userForm = new UserForm(
                name, 
                lastname, 
                phonenumber, 
                sector, 
                occupation, 
                nop, 
                email, 
                password, 
                role,
                true
        );

        assertNotNull(userForm);
        assertEquals(name, userForm.name());
        assertEquals(lastname, userForm.lastname());
        assertEquals(phonenumber, userForm.phonenumber());
        assertEquals(sector, userForm.sector());
        assertEquals(occupation, userForm.occupation());
        assertEquals(nop, userForm.nop());
        assertEquals(email, userForm.email());
        assertEquals(password, userForm.password());
        assertEquals(role, userForm.role());
        assertTrue(userForm.status());
    }

    @Test
    void testInvalidUserFormMissingName() {
        String lastname = faker.name().lastName();
        String phonenumber = faker.phoneNumber().phoneNumber();
        String sector = faker.company().industry();
        String occupation = faker.job().title();
        String nop = faker.idNumber().valid();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 16, true, true, true);
        Integer role = RoleEnum.ROLE_CLIENT.getCode();

        // Expect an IllegalArgumentException for missing name
        assertThrows(IllegalArgumentException.class, () -> new UserForm("", lastname, phonenumber, sector, occupation, nop, email, password, role, true));
    }

    @Test
    void testInvalidUserFormInvalidEmail() {
        String name = faker.name().firstName();
        String lastname = faker.name().lastName();
        String phonenumber = faker.phoneNumber().phoneNumber();
        String sector = faker.company().industry();
        String occupation = faker.job().title();
        String nop = faker.idNumber().valid();
        String invalidEmail = "invalid-email"; // Invalid email format
        String password = faker.internet().password(8, 16, true, true, true);
        Integer role = RoleEnum.ROLE_CLIENT.getCode();

        // Expect an IllegalArgumentException for invalid email format
        assertThrows(IllegalArgumentException.class, () -> new UserForm(name, lastname, phonenumber, sector, occupation, nop, invalidEmail, password, role, true));
    }

    @Test
    void testInvalidUserFormShortPassword() {
        String name = faker.name().firstName();
        String lastname = faker.name().lastName();
        String phonenumber = faker.phoneNumber().phoneNumber();
        String sector = faker.company().industry();
        String occupation = faker.job().title();
        String nop = faker.idNumber().valid();
        String email = faker.internet().emailAddress();
        String shortPassword = "short"; // Password too short
        Integer role = RoleEnum.ROLE_CLIENT.getCode();

        // Expect an IllegalArgumentException for short password
        assertThrows(IllegalArgumentException.class, () -> new UserForm(name, lastname, phonenumber, sector, occupation, nop, email, shortPassword, role, true));
    }
}


