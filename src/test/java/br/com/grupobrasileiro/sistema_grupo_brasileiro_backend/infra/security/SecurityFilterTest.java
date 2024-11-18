package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SecurityFilterTest {

    @InjectMocks
    private SecurityFilter securityFilter;

    @Mock
    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private final String token = "test.token.value";
    private final String userEmail = "test@example.com";

    @BeforeEach
    public void setup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testDoFilterInternal_WithValidTokenAndUser() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(tokenService.validateToken(token)).thenReturn(userEmail);

        User user = mock(User.class);
        when(user.getAuthorities()).thenReturn(null);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));

        securityFilter.doFilterInternal(request, response, filterChain);

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertTrue(authentication instanceof UsernamePasswordAuthenticationToken);
        assertEquals(user, authentication.getPrincipal());

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternal_WithInvalidToken() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(tokenService.validateToken(token)).thenReturn(null); // Token inválido

        securityFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternal_WithValidTokenAndUserNotFound() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(tokenService.validateToken(token)).thenReturn(userEmail);

        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> securityFilter.doFilterInternal(request, response, filterChain));

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void testDoFilterInternal_WithNoAuthorizationHeader() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        securityFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void testRecoverToken_WithBearerToken() {
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        String recoveredToken = ReflectionTestUtils.invokeMethod(securityFilter, "recoverToken", request);

        assertEquals(token, recoveredToken);
    }

    @Test
    public void testRecoverToken_WithNoBearerToken() {
        when(request.getHeader("Authorization")).thenReturn("NotBearerToken");

        String recoveredToken = ReflectionTestUtils.invokeMethod(securityFilter, "recoverToken", request);

        assertNull(recoveredToken);
    }

    @Test
    public void testRecoverToken_WithNullHeader() {
        when(request.getHeader("Authorization")).thenReturn(null);

        String recoveredToken = ReflectionTestUtils.invokeMethod(securityFilter, "recoverToken", request);

        assertNull(recoveredToken);
    }
}
