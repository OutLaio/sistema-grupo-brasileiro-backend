package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.EmailRequestForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.SendEmailForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.EmailService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PasswordRecoveryController.class)
public class PasswordRecoveryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private EmailService emailService;

    private Faker faker;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    public void testRequestReset_UserFound_ShouldSendEmail() throws Exception {
        String email = faker.internet().emailAddress();
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(user);
        when(tokenService.generateToken(user)).thenReturn("sample-token");
        doNothing().when(emailService).send(any(SendEmailForm.class));

        EmailRequestForm emailRequest = new EmailRequestForm(email);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/requestReset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(emailRequest)));

        result.andExpect(status().isOk())
              .andExpect(MockMvcResultMatchers.content().string("E-mail enviado com sucesso!"))
              .andDo(MockMvcResultHandlers.print());

        verify(userRepository).findByEmail(email);
        verify(tokenService).generateToken(user);
        verify(emailService).send(any(SendEmailForm.class));
    }

    @Test
    public void testRequestReset_UserNotFound_ShouldReturnNotFound() throws Exception {
        String email = faker.internet().emailAddress();

        when(userRepository.findByEmail(email)).thenReturn(null);

        EmailRequestForm emailRequest = new EmailRequestForm(email);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/requestReset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(emailRequest)));

        result.andExpect(status().isNotFound())
              .andExpect(MockMvcResultMatchers.content().string("Usuário não encontrado com e-mail: " + email))
              .andDo(MockMvcResultHandlers.print());

        verify(userRepository).findByEmail(email);
    }

    @Test
    public void testRequestReset_Exception_ShouldReturnNotFound() throws Exception {
        String email = faker.internet().emailAddress();

        when(userRepository.findByEmail(email)).thenThrow(new RuntimeException("Database error"));

        EmailRequestForm emailRequest = new EmailRequestForm(email);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/requestReset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(emailRequest)));

        result.andExpect(status().isNotFound())
              .andExpect(MockMvcResultMatchers.content().string("Usuário não encontrado com e-mail: " + email))
              .andDo(MockMvcResultHandlers.print());

        verify(userRepository).findByEmail(email);
    }
}
