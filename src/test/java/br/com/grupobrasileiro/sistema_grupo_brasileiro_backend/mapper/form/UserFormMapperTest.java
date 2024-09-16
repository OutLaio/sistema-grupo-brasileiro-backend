package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.form.UserFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;

public class UserFormMapperTest {

    private UserFormMapper userFormMapper;
    private Faker faker;

    @BeforeEach
    public void setUp() {
        userFormMapper = new UserFormMapper();
        faker = new Faker();
    }

    @Test
    public void testMap_WithValidData() {
        UserForm userForm = new UserForm(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().phoneNumber(),
                faker.company().industry(),
                faker.job().title(),
                faker.bothify("NOP###"),
                faker.internet().emailAddress(),
                faker.internet().password(),
                RoleEnum.ROLE_CLIENT.getCode()
        );

        // Map UserForm to User
        User user = userFormMapper.map(userForm);

        // Assert that all fields are correctly mapped
        assertEquals(userForm.name(), user.getName());
        assertEquals(userForm.lastname(), user.getLastname());
        assertEquals(userForm.phonenumber(), user.getPhonenumber());
        assertEquals(userForm.sector(), user.getSector());
        assertEquals(userForm.occupation(), user.getOccupation());
        assertEquals(userForm.nop(), user.getNop());
        assertEquals(userForm.email(), user.getEmail());
        assertEquals(userForm.role(), user.getRole());
    }

    @Test
    public void testMap_WithNullValues() {
        UserForm userForm = new UserForm(
                null,   // Nome nulo
                null,   // Sobrenome nulo
                null,   // Telefone nulo
                null,   // Setor nulo
                null,   // Ocupação nula
                null,   // NOP nulo
                null,   // Email nulo
                null,   // Senha nula
                null    // Role nula
        );

        // Map UserForm to User
        User user = userFormMapper.map(userForm);

        // Assert that the mapped User handles null values as expected
        assertNull(user.getName());
        assertNull(user.getLastname());
        assertNull(user.getPhonenumber());
        assertNull(user.getSector());
        assertNull(user.getOccupation());
        assertNull(user.getNop());
        assertNull(user.getEmail());
        assertNull(user.getPassword());

        // Check if role is set to the default value when null
        assertEquals(RoleEnum.ROLE_CLIENT.getCode(), user.getRole());
    }

    @Test
    public void testMap_WithInvalidEmail() {
        UserForm userForm = new UserForm(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().phoneNumber(),
                faker.company().industry(),
                faker.job().title(),
                faker.bothify("NOP###"),
                "invalid-email", // Email inválido
                faker.internet().password(),
                RoleEnum.ROLE_CLIENT.getCode()
        );

        // Map UserForm to User
        User user = userFormMapper.map(userForm);

        // Assert that the email is mapped as is (assuming no validation in mapper)
        assertEquals("invalid-email", user.getEmail());
    }

    @Test
    public void testMap_WithEmptyFields() {
        UserForm userForm = new UserForm(
                "", // Nome vazio
                "", // Sobrenome vazio
                "", // Telefone vazio
                "", // Setor vazio
                "", // Ocupação vazia
                "", // NOP vazio
                "", // Email vazio
                "", // Senha vazia
                RoleEnum.ROLE_CLIENT.getCode()
        );

        // Map UserForm to User
        User user = userFormMapper.map(userForm);

        // Assert that empty fields are mapped as empty strings
        assertEquals("", user.getName());
        assertEquals("", user.getLastname());
        assertEquals("", user.getPhonenumber());
        assertEquals("", user.getSector());
        assertEquals("", user.getOccupation());
        assertEquals("", user.getNop());
        assertEquals("", user.getEmail());
        assertEquals("", user.getPassword());
        assertEquals(RoleEnum.ROLE_CLIENT.getCode(), user.getRole());
    }
}
