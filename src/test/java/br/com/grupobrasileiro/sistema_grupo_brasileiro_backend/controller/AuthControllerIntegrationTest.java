package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.LoginRequestForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLogin() throws Exception {
        LoginRequestForm loginRequest = new LoginRequestForm("joao.silva@example.com", "password");

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"token\":\"dummyToken\"}"));
    }

    @Test
    public void testRegister() throws Exception {
        UserForm userForm = new UserForm(
                "João",
                "Silva",
                "+55 (11) 98888-8888",
                "Tecnologia",
                "Desenvolvedor",
                "123456",
                "joao.silva@example.com",
                "Password@123",
                RoleEnum.CLIENT);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userForm)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));
    }
}
