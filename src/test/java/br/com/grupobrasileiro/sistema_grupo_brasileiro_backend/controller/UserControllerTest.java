package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.UserService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    public void testSaveUser() throws Exception {
        UserForm userForm = new UserForm(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().phoneNumber(),
                faker.company().industry(),
                faker.job().title(),
                faker.idNumber().valid(),
                faker.internet().emailAddress(),
                faker.internet().password(8, 16, true, true, true),
                RoleEnum.ROLE_CLIENT.getCode(),
                true
        );

        UserView userView = new UserView(
                1L,
                userForm.name(),
                userForm.lastname(),
                userForm.phonenumber(),
                userForm.sector(),
                userForm.occupation(),
                userForm.nop(),
                userForm.email(),
                userForm.role(),
                userForm.status()
        );

        when(userService.save(any(UserForm.class))).thenReturn(userView);

        ResponseEntity<UserView> response = userController.save(userForm);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userView, response.getBody());
    }
}


