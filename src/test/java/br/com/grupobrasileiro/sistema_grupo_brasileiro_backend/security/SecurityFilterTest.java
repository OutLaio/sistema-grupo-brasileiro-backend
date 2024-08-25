package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.SecurityFilter;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;

@WebMvcTest
public class SecurityFilterTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SecurityFilter securityFilter;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(securityFilter)
                .addFilter(securityFilter) // Adiciona o filtro ao MockMvc
                .build();
    }

    @Test
    void testFilterWithValidToken() throws Exception {
        String validToken = "valid-token";
        String userEmail = "user@example.com";

        // Mock behavior
        when(tokenService.validateToken(validToken)).thenReturn(userEmail);
        UserDetails userDetails = mock(UserDetails.class);
        when(userRepository.findByEmail(userEmail)).thenReturn(userDetails);
        when(userDetails.getAuthorities()).thenReturn(null); // Ajuste conforme necessário

        // Execute test
        mockMvc.perform(get("/protected-endpoint")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + validToken))
                .andExpect(status().isOk()); // Espera status 200 OK

        // Verificar o contexto de segurança
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        assertEquals(userDetails, auth.getPrincipal());
    }

    @Test
    void testFilterWithInvalidToken() throws Exception {
        String invalidToken = "invalid-token";

        // Mock behavior
        when(tokenService.validateToken(invalidToken)).thenReturn(null);

        // Execute test
        mockMvc.perform(get("/protected-endpoint")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + invalidToken))
                .andExpect(status().isUnauthorized()); // Espera status 401 Unauthorized

        // Verificar o contexto de segurança
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        assertNull(auth);
    }

    @Test
    void testFilterWithoutToken() throws Exception {
        // Execute test
        mockMvc.perform(get("/protected-endpoint"))
                .andExpect(status().isUnauthorized()); // Espera status 401 Unauthorized

        // Verificar o contexto de segurança
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        assertNull(auth);
    }
}
