package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private UserService userService;

        @MockBean
        private TokenService tokenService;

        private ObjectMapper objectMapper;

        @BeforeEach
        void setUp() {
                objectMapper = new ObjectMapper();
        }

        @Test
        public void testSaveUser_Success() throws Exception {
                // Create a UserForm and a UserView for testing
                UserForm userForm = new UserForm(
                                "João", "Silva", "+55 (11) 98888-8888", "Tecnologia", "Desenvolvedor", "123456",
                                "joao.silva@example.com", "Password123!", RoleEnum.CLIENT);

                UserView userView = new UserView(
                                1L, "João", "Silva", "+55 (11) 98888-8888", "Tecnologia", "Desenvolvedor", "123456",
                                "joao.silva@example.com", RoleEnum.CLIENT);

                // Mocking behavior
                when(userService.save(userForm)).thenReturn(userView);

                mockMvc.perform(post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userForm)))
                                .andExpect(status().isCreated())
                                .andExpect(content().json(objectMapper.writeValueAsString(userView)));
        }

        @Test
        public void testSaveUser_InvalidRequest() throws Exception {
                // Create an invalid UserForm with missing fields or invalid data
                UserForm invalidUserForm = new UserForm(
                                "", "", "", "", "", "", "", "", RoleEnum.CLIENT);

                // Perform the request with invalid data
                mockMvc.perform(post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalidUserForm)))
                                .andExpect(status().isBadRequest());
        }
}
