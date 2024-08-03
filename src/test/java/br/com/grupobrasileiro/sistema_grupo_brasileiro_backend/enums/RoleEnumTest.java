package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleEnumTest {

    @Test
    public void testAdminRole() {
        RoleEnum adminRole = RoleEnum.ADMIN;
        assertEquals("admin", adminRole.getRole(), "O papel ADMIN deve retornar 'admin'");
    }

    @Test
    public void testClientRole() {
        RoleEnum clientRole = RoleEnum.CLIENT;
        assertEquals("user", clientRole.getRole(), "O papel CLIENT deve retornar 'user'");
    }

    @Test
    public void testRoleEnumValues() {
        RoleEnum[] roles = RoleEnum.values();
        assertEquals(2, roles.length, "Deve haver exatamente 2 papéis no enum RoleEnum");

        assertEquals("admin", roles[0].getRole(), "O primeiro papel deve ser 'admin'");
        assertEquals("user", roles[1].getRole(), "O segundo papel deve ser 'user'");
    }
}
