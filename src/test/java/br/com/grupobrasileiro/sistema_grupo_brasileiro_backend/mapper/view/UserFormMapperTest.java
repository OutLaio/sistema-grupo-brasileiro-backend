package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
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
        // Criação do UserForm com dados de teste
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

        assertEquals("John", user.getName(), "O nome deve ser mapeado corretamente");
        assertEquals("Doe", user.getLastname(), "O sobrenome deve ser mapeado corretamente");
        assertEquals("123456789", user.getPhonenumber(), "O número de telefone deve ser mapeado corretamente");
        assertEquals("IT", user.getSector(), "O setor deve ser mapeado corretamente");
        assertEquals("Developer", user.getOccupation(), "A ocupação deve ser mapeada corretamente");
        assertEquals("NOP123", user.getNop(), "O NOP deve ser mapeado corretamente");
        assertEquals("john.doe@example.com", user.getEmail(), "O e-mail deve ser mapeado corretamente");
        assertEquals("Password123", user.getPassword(), "A senha deve ser mapeada corretamente");
        assertEquals(RoleEnum.ROLE_CLIENT, user.getRole(), "O papel deve ser mapeado corretamente");
    }
}
