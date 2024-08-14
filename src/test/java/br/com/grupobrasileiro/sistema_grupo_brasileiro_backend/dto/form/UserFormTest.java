package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;

public class UserFormTest {

    private final Faker faker = new Faker();

    @Test
    public void testValidUserForm() {
        String name = faker.name().firstName();
        String lastname = faker.name().lastName();
        String phonenumber = faker.phoneNumber().phoneNumber();
        String sector = faker.company().industry();
        String occupation = faker.job().title();
        String nop = faker.bothify("??###");
        String email = faker.internet().emailAddress();
        String password = "Password123!";
        Integer role = RoleEnum.ROLE_CLIENT.getCode();  // Usando RoleEnum existente

        UserForm form = new UserForm(name, lastname, phonenumber, sector, occupation, nop, email, password, role);

        assertEquals(name, form.name());
        assertEquals(lastname, form.lastname());
        assertEquals(phonenumber, form.phonenumber());
        assertEquals(sector, form.sector());
        assertEquals(occupation, form.occupation());
        assertEquals(nop, form.nop());
        assertEquals(email, form.email());
        assertEquals(password, form.password());
        assertEquals(role, form.role());
    }

    @Test
    public void testUserFormWithInvalidEmail() {
        String invalidEmail = "invalid-email";
        UserForm form = new UserForm(faker.name().firstName(), faker.name().lastName(),
            faker.phoneNumber().phoneNumber(), faker.company().industry(),
            faker.job().title(), faker.bothify("??###"), invalidEmail,
            "Password123!", RoleEnum.ROLE_CLIENT.getCode());

           
        assertEquals(invalidEmail, form.email());
    }

    @Test
    public void testDefaultRole() {
        UserForm form = new UserForm("John", "Doe", "123456789", "IT", "Developer",
            "NOP123", "john.doe@example.com", "Password123!", null);

        assertEquals(RoleEnum.ROLE_CLIENT.getCode(), form.role());
    }

   
}


