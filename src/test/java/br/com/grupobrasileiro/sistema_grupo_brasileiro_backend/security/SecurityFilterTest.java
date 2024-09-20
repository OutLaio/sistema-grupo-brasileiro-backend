//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.security;
//
//import static org.junit.Assert.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.Collections;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.test.web.servlet.MockMvc;
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.SecurityFilter;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class SecurityFilterTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private TokenService tokenService;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @Test
//    void testFilterWithValidToken() throws Exception {
//        String validToken = "valid-token";
//        String userEmail = "user@example.com";
//
//        // Mock behavior
//        when(tokenService.validateToken(validToken)).thenReturn(userEmail);
//        UserDetails userDetails = mock(UserDetails.class);
//        when(userRepository.findByEmail(userEmail)).thenReturn(userDetails);
//        when(userDetails.getAuthorities()).thenReturn(Collections.emptyList());
//
//        // Execute test
//        mockMvc.perform(get("/protected-endpoint")
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + validToken))
//                .andExpect(status().isOk())
//                .andExpect(result -> {
//                    SecurityContext context = SecurityContextHolder.getContext();
//                    Authentication auth = context.getAuthentication();
//                    assertNotNull(auth, "Authentication should not be null for valid token");
//                    assertEquals(userDetails, auth.getPrincipal(), "User should be authenticated with valid token");
//                });
//    }
//
//    @Test
//    void testFilterWithInvalidToken() throws Exception {
//        String invalidToken = "invalid-token";
//
//        // Mock behavior
//        when(tokenService.validateToken(invalidToken)).thenReturn(null);
//
//        // Execute test
//        mockMvc.perform(get("/protected-endpoint")
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + invalidToken))
//                .andExpect(status().isUnauthorized());
//
//        // Verificar o contexto de segurança
//        SecurityContext context = SecurityContextHolder.getContext();
//        Authentication auth = context.getAuthentication();
//        assertNull(auth, "Authentication should be null for invalid token");
//    }
//
//    @Test
//    void testFilterWithoutToken() throws Exception {
//        // Execute test
//        mockMvc.perform(get("/protected-endpoint"))
//                .andExpect(status().isUnauthorized());
//
//        // Verificar o contexto de segurança
//        SecurityContext context = SecurityContextHolder.getContext();
//        Authentication auth = context.getAuthentication();
//        assertNull(auth, "Authentication should be null when no token is provided");
//    }
//
//
//    @Test
//    void testFilterWithMalformedToken() throws Exception {
//        String malformedToken = "malformed-token";
//
//        // Mock behavior
//        when(tokenService.validateToken(malformedToken)).thenThrow(new RuntimeException("Token malformed"));
//
//        // Execute test
//        mockMvc.perform(get("/protected-endpoint")
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + malformedToken))
//                .andExpect(status().isUnauthorized());
//
//        // Verificar o contexto de segurança
//        SecurityContext context = SecurityContextHolder.getContext();
//        Authentication auth = context.getAuthentication();
//        assertNull(auth, "Authentication should be null for malformed token");
//    }
//
//}
