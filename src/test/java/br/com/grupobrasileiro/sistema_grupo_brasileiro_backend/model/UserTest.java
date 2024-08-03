package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;

public class UserTest {
    @Test
    public void testUserGettersAndSetters() {
        User user = new User();

        user.setId(1L);
        user.setName("João");
        user.setLastname("Silva");
        user.setPhonenumber("+55 (11) 98888-8888");
        user.setSector("Tecnologia");
        user.setOccupation("Desenvolvedor");
        user.setNop("123456");
        user.setEmail("joao.silva@example.com");
        user.setRole(RoleEnum.ROLE_CLIENT.getCode()); // Atualize aqui

        assertEquals(1L, user.getId());
        assertEquals("João", user.getName());
        assertEquals("Silva", user.getLastname());
        assertEquals("+55 (11) 98888-8888", user.getPhonenumber());
        assertEquals("Tecnologia", user.getSector());
        assertEquals("Desenvolvedor", user.getOccupation());
        assertEquals("123456", user.getNop());
        assertEquals("joao.silva@example.com", user.getEmail());
        assertEquals(RoleEnum.ROLE_CLIENT.getCode(), user.getRole());
    }
}