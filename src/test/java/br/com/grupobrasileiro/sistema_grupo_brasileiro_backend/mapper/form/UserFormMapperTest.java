package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;

public class UserFormMapperTest {

    @Autowired
    private UserFormMapper userFormMapper;

    @BeforeEach
    public void setUp() {
        userFormMapper = new UserFormMapper();
    }

    @Test
    public void testMap() {
        // Cria um UserForm de exemplo
        UserForm userForm = new UserForm(
                "John",
                "Doe",
                "123456789",
                "IT",
                "Developer",
                "NOP123",
                "john.doe@example.com",
                "Password123", null);

        User user = userFormMapper.map(userForm);

        assertEquals("John", user.getName());
        assertEquals("Doe", user.getLastname());
        assertEquals("123456789", user.getPhonenumber());
        assertEquals("IT", user.getSector());
        assertEquals("Developer", user.getOccupation());
        assertEquals("NOP123", user.getNop());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("Password123", user.getPassword());
        assertEquals(RoleEnum.CLIENT, user.getRole());
    }
}
