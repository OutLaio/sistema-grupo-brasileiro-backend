package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;

@WebMvcTest(PasswordResetController.class)
public class PasswordResetControllerTest {

    @Mock
    private UserRepository userRepository; // Mock do UserRepository

    @Mock
    private PasswordEncoder passwordEncoder; // Mock do PasswordEncoder

    @Mock
    private TokenService tokenService; // Mock do TokenService

    @InjectMocks
    private PasswordResetController passwordResetController; // Controller com os mocks

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks
        mockMvc = MockMvcBuilders.standaloneSetup(passwordResetController).build(); // Configura o MockMvc
    }

    @Test
    public void testResetPasswordSuccess() throws Exception {
        String token = "valid-token";
        String emailFromToken = "user@example.com";
        String newPassword = "NewPassword123!";

        // Configura o comportamento dos mocks
        when(tokenService.validateToken(token)).thenReturn(emailFromToken);
        when(userRepository.findByEmail(emailFromToken)).thenReturn(new User(/* parâmetros de inicialização */));
        when(passwordEncoder.encode(newPassword)).thenReturn("encoded-password");

        mockMvc.perform(post("/api/v1/resetPassword")
                .param("token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"newPassword\":\"" + newPassword + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Password successfully changed!"));
    }

    @Test
    public void testResetPasswordInvalidToken() throws Exception {
        String token = "invalid-token";

        // Configura o comportamento dos mocks
        when(tokenService.validateToken(token)).thenReturn(null);

        mockMvc.perform(post("/api/v1/resetPassword")
                .param("token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"newPassword\":\"NewPassword123!\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Token inválido ou expirado"));
    }

    @Test
    public void testResetPasswordUserNotFound() throws Exception {
        String token = "valid-token";
        String emailFromToken = "user@example.com";

        // Configura o comportamento dos mocks
        when(tokenService.validateToken(token)).thenReturn(emailFromToken);
        when(userRepository.findByEmail(emailFromToken)).thenReturn(null);

        mockMvc.perform(post("/api/v1/resetPassword")
                .param("token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"newPassword\":\"NewPassword123!\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuário não encontrado com e-mail: " + emailFromToken));
    }

    @Test
    public void testResetPasswordInvalidRequest() throws Exception {
        String token = "valid-token";

        // Configura o comportamento dos mocks
        when(tokenService.validateToken(token)).thenReturn("user@example.com");

        mockMvc.perform(post("/api/v1/resetPassword")
                .param("token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"newPassword\":\"short\"}")) // Senha inválida
                .andExpect(status().isBadRequest());
    }
}
