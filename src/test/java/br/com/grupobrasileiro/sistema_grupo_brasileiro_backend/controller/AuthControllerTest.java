//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.LoginRequestForm;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.UserService;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.LoginRequestForm;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.UserService;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//
//	@WebMvcTest(AuthController.class)
//	@AutoConfigureMockMvc
//	public class AuthControllerTest {
//	    @Autowired
//	    private MockMvc mockMvc;
//
//	    @MockBean
//	    private AuthenticationManager authenticationManager;
//
//	    @MockBean
//	    private UserRepository userRepository;
//
//	    @MockBean
//	    private TokenService tokenService;
//
//	    @MockBean
//	    private UserService userService;
//
//	    private ObjectMapper objectMapper;
//
//	    @BeforeEach
//	    public void setup() {
//	        objectMapper = new ObjectMapper();
//	    }
//
//	    @Test
//	    public void testLoginSuccess() throws Exception {
//	        // Arrange
//	        LoginRequestForm loginRequest = new LoginRequestForm("user@example.com", "password123");
//	        User user = new User();
//	        user.setActive(true);
//
//	        when(userRepository.findByEmail(anyString())).thenReturn(user);
//	        when(authenticationManager.authenticate(any())).thenReturn(new UsernamePasswordAuthenticationToken(user, null));
//	        when(tokenService.generateToken(any())).thenReturn("dummyToken");
//
//	        // Act & Assert
//	        mockMvc.perform(post("/api/v1/auth/login")
//	                .contentType(MediaType.APPLICATION_JSON)
//	                .content(objectMapper.writeValueAsString(loginRequest))
//	                .with(csrf()))
//	                .andExpect(status().isOk())
//	                .andExpect(jsonPath("$.token").value("dummyToken"));
//	    }
//
//
//	    @Test
//	    public void testLoginUserNotFound() throws Exception {
//	        // Arrange
//	        LoginRequestForm loginRequest = new LoginRequestForm("user@example.com", "password123");
//
//
//	        when(userRepository.findByEmail(anyString())).thenThrow(new EntityNotFoundException("Usuário não encontrado com e-mail: user@example.com"));
//
//	        // Act & Assert
//	        mockMvc.perform(post("/api/v1/auth/login")
//	                .contentType(MediaType.APPLICATION_JSON)
//	                .content(objectMapper.writeValueAsString(loginRequest))
//	                .with(csrf()))
//	                .andExpect(status().isNotFound())
//	                .andExpect(jsonPath("$.message").value("Usuário não encontrado com e-mail: user@example.com"));
//	    }
//
//
//    @Test
//    public void testLoginUserNotActive() throws Exception {
//        // Arrange
//        LoginRequestForm loginRequest = new LoginRequestForm("user@example.com", "password123");
//        User user = new User();
//        user.setActive(false);
//
//        when(userRepository.findByEmail(anyString())).thenReturn(user);
//
//        // Act & Assert
//        mockMvc.perform(post("/api/v1/auth/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(loginRequest)))
//                .andExpect(status().isForbidden())
//                .andExpect(jsonPath("$.message").value("Acesso negado."));
//    }
//}
//
