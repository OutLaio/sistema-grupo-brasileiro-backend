package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthControllerIntegrationTest {

    @Test
    public void testUserFormCreation() {
        UserForm userForm = new UserForm(
                "John", "Doe", "123456789", "IT", "Developer", "123", "john.doe@example.com", "Password@123",
                RoleEnum.ROLE_CLIENT.getCode());
        assertEquals("John", userForm.name());
    }
}
