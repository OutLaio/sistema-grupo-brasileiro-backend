package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserViewTest {

    @Test
    public void testUserView_ValidData() {
        // Arrange
        Long id = 1L;
        String name = "John";
        String lastname = "Doe";
        String phonenumber = "1234567890";
        String sector = "IT";
        String occupation = "Developer";
        String nop = "NOP123";
        String email = "john.doe@example.com";
        RoleEnum role = RoleEnum.CLIENT;

        // Act
        UserView userView = new UserView(id, name, lastname, phonenumber, sector, occupation, nop, email, role);

        // Assert
        assertEquals(id, userView.id());
        assertEquals(name, userView.name());
        assertEquals(lastname, userView.lastname());
        assertEquals(phonenumber, userView.phonenumber());
        assertEquals(sector, userView.sector());
        assertEquals(occupation, userView.occupation());
        assertEquals(nop, userView.nop());
        assertEquals(email, userView.email());
        assertEquals(role, userView.role());
    }

    @Test
    public void testUserView_EmptyFields() {
        // Arrange
        Long id = 1L;
        String name = "";
        String lastname = "";
        String phonenumber = "";
        String sector = "";
        String occupation = "";
        String nop = "";
        String email = "";
        RoleEnum role = RoleEnum.CLIENT;

        // Act
        UserView userView = new UserView(id, name, lastname, phonenumber, sector, occupation, nop, email, role);

        // Assert
        assertEquals(id, userView.id());
        assertEquals(name, userView.name());
        assertEquals(lastname, userView.lastname());
        assertEquals(phonenumber, userView.phonenumber());
        assertEquals(sector, userView.sector());
        assertEquals(occupation, userView.occupation());
        assertEquals(nop, userView.nop());
        assertEquals(email, userView.email());
        assertEquals(role, userView.role());
    }
}
