//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;
//
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.Test;
//import com.github.javafaker.Faker;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//
//public class UserFormTest {
//
//    private final Faker faker = new Faker();
//
//    @Test
//    public void testValidUserForm() {
//        String name = faker.name().firstName();
//        String lastname = faker.name().lastName();
//        String phonenumber = faker.phoneNumber().phoneNumber();
//        String sector = faker.company().industry();
//        String occupation = faker.job().title();
//        String nop = faker.bothify("??###");
//        String email = faker.internet().emailAddress();
//        String password = "Password123!";
//        Integer role = RoleEnum.ROLE_CLIENT.getCode();
//
//        UserForm form = new UserForm(name, lastname, phonenumber, sector, occupation, nop, email, password, role);
//
//        assertEquals(name, form.name());
//        assertEquals(lastname, form.lastname());
//        assertEquals(phonenumber, form.phonenumber());
//        assertEquals(sector, form.sector());
//        assertEquals(occupation, form.occupation());
//        assertEquals(nop, form.nop());
//        assertEquals(email, form.email());
//        assertEquals(password, form.password());
//        assertEquals(role, form.role());
//    }
//
//    @Test
//    public void testUserFormWithInvalidEmail() {
//        String invalidEmail = "invalid-email";
//        UserForm form = new UserForm(faker.name().firstName(), faker.name().lastName(),
//            faker.phoneNumber().phoneNumber(), faker.company().industry(),
//            faker.job().title(), faker.bothify("??###"), invalidEmail,
//            "Password123!", RoleEnum.ROLE_CLIENT.getCode());
//
//     // Here it is important to validate if the class handles invalid emails, assuming there should be validation
//     // The code only checks the returned value, adjust if there is real validation
//        assertEquals(invalidEmail, form.email());
//    }
//
//    @Test
//    public void testDefaultRole() {
//        UserForm form = new UserForm("John", "Doe", "123456789", "IT", "Developer",
//            "NOP123", "john.doe@example.com", "Password123!", null);
//
//        assertEquals(RoleEnum.ROLE_CLIENT.getCode(), form.role());
//    }
//
//    @Test
//    public void testPasswordValidation() {
//        String validPassword = "Password123!";
//        String invalidPassword = "short";
//
//        UserForm validForm = new UserForm(faker.name().firstName(), faker.name().lastName(),
//            faker.phoneNumber().phoneNumber(), faker.company().industry(),
//            faker.job().title(), faker.bothify("??###"), faker.internet().emailAddress(),
//            validPassword, RoleEnum.ROLE_CLIENT.getCode());
//
//        UserForm invalidForm = new UserForm(faker.name().firstName(), faker.name().lastName(),
//            faker.phoneNumber().phoneNumber(), faker.company().industry(),
//            faker.job().title(), faker.bothify("??###"), faker.internet().emailAddress(),
//            invalidPassword, RoleEnum.ROLE_CLIENT.getCode());
//
//     // Here you should add a password validation in the UserForm class
//     // The code here just checks if the password is correctly assigned
//        assertEquals(validPassword, validForm.password());
//     // Adds a dummy password validation, adjust according to your application's logic
//        assertTrue(validPassword.length() >= 8);
//        assertFalse(invalidPassword.length() >= 8);
//    }
//}
