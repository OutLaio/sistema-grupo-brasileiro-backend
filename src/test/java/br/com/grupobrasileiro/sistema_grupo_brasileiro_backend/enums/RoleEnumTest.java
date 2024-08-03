package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RoleEnumTest {

    @Test
    public void testClientRole() {
        RoleEnum clientRole = RoleEnum.ROLE_CLIENT;
        assertEquals(Integer.valueOf(1), clientRole.getCode(), "O código do papel ROLE_CLIENT deve ser 1");
        assertEquals("Client", clientRole.getDescription(), "A descrição do papel ROLE_CLIENT deve ser 'Client'");
    }

    @Test
    public void testCollaboratorRole() {
        RoleEnum collaboratorRole = RoleEnum.ROLE_COLLABORATOR;
        assertEquals(Integer.valueOf(2), collaboratorRole.getCode(), "O código do papel ROLE_COLLABORATOR deve ser 2");
        assertEquals("Collaborator", collaboratorRole.getDescription(),
                "A descrição do papel ROLE_COLLABORATOR deve ser 'Collaborator'");
    }

    @Test
    public void testSupervisorRole() {
        RoleEnum supervisorRole = RoleEnum.ROLE_SUPERVISOR;
        assertEquals(Integer.valueOf(3), supervisorRole.getCode(), "O código do papel ROLE_SUPERVISOR deve ser 3");
        assertEquals("Supervisor", supervisorRole.getDescription(),
                "A descrição do papel ROLE_SUPERVISOR deve ser 'Supervisor'");
    }

    @Test
    public void testRoleEnumFromCode() {
        assertEquals(RoleEnum.ROLE_CLIENT, RoleEnum.fromCode(1), "O papel para o código 1 deve ser ROLE_CLIENT");
        assertEquals(RoleEnum.ROLE_COLLABORATOR, RoleEnum.fromCode(2),
                "O papel para o código 2 deve ser ROLE_COLLABORATOR");
        assertEquals(RoleEnum.ROLE_SUPERVISOR, RoleEnum.fromCode(3),
                "O papel para o código 3 deve ser ROLE_SUPERVISOR");
    }

    @Test
    public void testInvalidCode() {
        assertThrows(IllegalArgumentException.class, () -> RoleEnum.fromCode(99),
                "Deve lançar IllegalArgumentException para código inválido");
    }
}
