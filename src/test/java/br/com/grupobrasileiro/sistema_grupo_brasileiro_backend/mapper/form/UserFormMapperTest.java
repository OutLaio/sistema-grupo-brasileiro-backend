package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form.UserFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;

public class UserFormMapperTest {

    private UserFormMapper userFormMapper;

    @BeforeEach
    public void setUp() {
        userFormMapper = new UserFormMapper();
    }

    @Test
    public void testMap() {

        UserForm userForm = new UserForm(
                "John",
                "Doe",
                "123456789",
                "IT",
                "Developer",
                "NOP123",
                "john.doe@example.com",
                "Password123", null);

        // Mapeia o UserForm para User
        User user = userFormMapper.map(userForm);

        // Converte User para UserView com valores esperados
        UserView userView = new UserView(
                user.getId(),
                user.getName(),
                user.getLastname(),
                user.getPhonenumber(),
                user.getSector(),
                user.getOccupation(),
                user.getNop(),
                user.getEmail(),
                RoleEnum.ROLE_CLIENT.getCode());

        assertEquals("John", userView.name());
        assertEquals("Doe", userView.lastname());
        assertEquals("123456789", userView.phonenumber());
        assertEquals("IT", userView.sector());
        assertEquals("Developer", userView.occupation());
        assertEquals("NOP123", userView.nop());
        assertEquals("john.doe@example.com", userView.email());
        assertEquals(Integer.valueOf(RoleEnum.ROLE_CLIENT.getCode()), userView.role());
    }
}
