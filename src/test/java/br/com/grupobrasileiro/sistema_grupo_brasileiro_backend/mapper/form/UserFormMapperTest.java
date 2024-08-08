package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form.UserFormMapper;
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
    public void testMap() {
        UserForm userForm = new UserForm(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().phoneNumber(),
                faker.company().industry(),
                faker.job().title(),
                faker.bothify("NOP###"),
                faker.internet().emailAddress(),
                faker.internet().password(),
                RoleEnum.ROLE_CLIENT.getCode(),
                true
        );

        // Map UserForm to User
        User user = userFormMapper.map(userForm);

        // Convert User to UserView with expected values
        UserView userView = new UserView(
                user.getId(),
                user.getName(),
                user.getLastname(),
                user.getPhonenumber(),
                user.getSector(),
                user.getOccupation(),
                user.getNop(),
                user.getEmail(),
                user.getRole(),
                user.isEnabled() // Ajustado para isEnabled()
        );

        assertEquals(userForm.name(), userView.name());
        assertEquals(userForm.lastname(), userView.lastname());
        assertEquals(userForm.phonenumber(), userView.phonenumber());
        assertEquals(userForm.sector(), userView.sector());
        assertEquals(userForm.occupation(), userView.occupation());
        assertEquals(userForm.nop(), userView.nop());
        assertEquals(userForm.email(), userView.email());
        assertEquals(userForm.role(), userView.role());
        assertEquals(userForm.status(), userView.status()); // Ajustado para usar isEnabled()
    }
}
