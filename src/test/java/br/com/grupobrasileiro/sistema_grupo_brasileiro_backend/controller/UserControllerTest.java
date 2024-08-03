package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.UserService;
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

        @BeforeEach
        public void setUp() {
                MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testSaveUser() throws Exception {
                UserForm userForm = new UserForm(
                                "João", "Silva", "21999999999", "Tecnologia", "Desenvolvedor", "123",
                                "joao.silva@example.com", "Senha@123", RoleEnum.ROLE_CLIENT.getCode());

                UserView userView = new UserView(
                                1L, "João", "Silva", "21999999999", "Tecnologia", "Desenvolvedor", "123",
                                "joao.silva@example.com", RoleEnum.ROLE_CLIENT.getCode());

                when(userService.save(any(UserForm.class))).thenReturn(userView);

                ResponseEntity<UserView> response = userController.save(userForm);

                assertEquals(HttpStatus.CREATED, response.getStatusCode());
                assertEquals(userView, response.getBody());
        }
}
