//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.when;
//
//import java.util.Collections;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import com.github.javafaker.Faker;
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;
//
//public class AuthorizationServiceTest {
//
//	 @InjectMocks
//	    private AuthorizationService authorizationService;
//
//	    @Mock
//	    private UserRepository userRepository;
//
//	    private Faker faker;
//
//	    @BeforeEach
//	    public void setUp() {
//	        MockitoAnnotations.openMocks(this);
//	        faker = new Faker();
//	    }
//
//    @Test
//    public void loadUserByUsername_UserExists_ReturnsUserDetails() {
//        // Arrange
//        String email = "test@example.com";
//        String password = "password";
//        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
//            email,
//            password,
//            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
//        );
//
//        when(userRepository.findByEmail(email)).thenReturn(userDetails);
//
//        // Act
//        UserDetails result = authorizationService.loadUserByUsername(email);
//
//        // Assert
//        assertNotNull(result, "UserDetails should not be null.");
//        assertEquals(email, result.getUsername(), "Username should match.");
//        assertEquals(password, result.getPassword(), "Password should match.");
//    }
//
//    @Test
//    void loadUserByUsername_UserDoesNotExist_ThrowsException() {
//        // Arrange
//        String emailLogin = "nonexistent@example.com";
//
//
//        when(userRepository.findByEmail(emailLogin)).thenThrow(new UsernameNotFoundException("User not found with email: " + emailLogin));
//
//        // Act & Assert
//        UsernameNotFoundException thrown = assertThrows(UsernameNotFoundException.class, () ->
//            authorizationService.loadUserByUsername(emailLogin)
//        );
//
//        // Assert
//        String expectedMessagePart = "User not found with email: " + emailLogin;
//        assertTrue(thrown.getMessage().contains(expectedMessagePart),
//            "Exception message should contain: " + expectedMessagePart);
//    }
//
//
//}