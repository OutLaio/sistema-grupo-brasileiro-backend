//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.javafaker.Faker;
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.EmailRequestForm;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TestSecurityConfig;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.email.EmailService;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.argThat;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(PasswordRecoveryController.class)
//@Import(TestSecurityConfig.class)
//public class PasswordRecoveryControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @MockBean
//    private TokenService tokenService;
//
//    @MockBean
//    private EmailService emailService;
//
//    private ObjectMapper objectMapper;
//    private Faker faker;
//
//    @BeforeEach
//    void setUp() {
//        objectMapper = new ObjectMapper();
//        faker = new Faker();
//    }
//
//    @Test
//    @WithMockUser
//    void testRequestReset_Success() throws Exception {
//        // Arrange
//        String email = faker.internet().emailAddress();
//        String token = faker.internet().uuid();
//        EmailRequestForm emailRequestForm = new EmailRequestForm(email);
//        User user = new User();
//
//        when(userRepository.findByEmail(email)).thenReturn(user);
//        when(tokenService.generateToken(user)).thenReturn(token);
//
//        doNothing().when(emailService).send(argThat(request ->
//            "no-reply@everdev.com".equals(request.emailFrom()) &&
//            email.equals(request.emailTo()) &&
//            "Password Reset".equals(request.subject()) &&
//            request.text().contains("http://localhost:4200/resetPassword?token=" + token)
//        ));
//
//        // Act & Assert
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/requestReset")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(emailRequestForm)))
//            .andExpect(status().isOk())
//            .andExpect(MockMvcResultMatchers.content().string("E-mail enviado com sucesso!"));
//
//        verify(userRepository).findByEmail(email);
//        verify(tokenService).generateToken(user);
//        verify(emailService).send(argThat(request ->
//            "no-reply@everdev.com".equals(request.emailFrom()) &&
//            email.equals(request.emailTo()) &&
//            "Password Reset".equals(request.subject()) &&
//            request.text().contains("http://localhost:4200/resetPassword?token=" + token)
//        ));
//    }
//
//    @Test
//    @WithMockUser
//    void testRequestReset_UserNotFound() throws Exception {
//        // Arrange
//        String email = faker.internet().emailAddress();
//
//        // Simula a situação onde o usuário não é encontrado
//        when(userRepository.findByEmail(email)).thenReturn(null);
//
//        // Act
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/testRequestReset")
//                .param("email", email))
//            .andDo(MockMvcResultHandlers.print())
//            .andReturn();
//
//        // Assert
//        int statusCode = result.getResponse().getStatus();
//
//        // Verifique o código de status da resposta
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), statusCode, "O código de status da resposta não é 500");
//
//        // Verifique se a resposta contém uma indicação genérica de erro
//        String responseContent = result.getResponse().getContentAsString();
//        assertTrue(responseContent.contains("Erro") || responseContent.isEmpty(), "A resposta não contém uma indicação genérica de erro ou está vazia");
//    }
//
//
//    @Test
//    @WithMockUser
//    void testRequestReset_ExceptionHandling() throws Exception {
//        // Arrange
//        String email = faker.internet().emailAddress();
//        EmailRequestForm emailRequestForm = new EmailRequestForm(email);
//
//        when(userRepository.findByEmail(email)).thenThrow(new RuntimeException("Erro interno"));
//
//        // Act
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/requestReset")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(emailRequestForm)))
//            .andDo(MockMvcResultHandlers.print())
//            .andReturn();
//
//        // Assert
//        int statusCode = result.getResponse().getStatus();
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), statusCode, "O código de status da resposta não é 500");
//
//        // Verifique o conteúdo da resposta
//        String responseContent = result.getResponse().getContentAsString();
//        System.out.println("Response content: " + responseContent);
//    }
//
//
//}