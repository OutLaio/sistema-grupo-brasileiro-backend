package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.LoginForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(LoginController.class)
class LoginControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSucesso() throws Exception {
        LoginForm loginForm = new LoginForm("renata@example.com", "password123");

        // Simulate the call to service
        when(userService.login(loginForm.getEmail(), loginForm.getPassword())).thenReturn("token123");

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginForm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token123"));
    }

    @Test
    void testLoginFalha() throws Exception {
        LoginForm loginForm = new LoginForm("renata@example.com", "wrongpassword");

        // Simulate the call to service
        when(userService.login(loginForm.getEmail(), loginForm.getPassword())).thenThrow(new RuntimeException("Autenticação falhou"));

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginForm)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Autenticação falhou"));
    }
}
