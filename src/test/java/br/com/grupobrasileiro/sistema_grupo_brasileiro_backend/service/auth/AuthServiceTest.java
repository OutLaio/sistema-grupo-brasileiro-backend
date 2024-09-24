package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.LoginForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.RecoveryPasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.ResetPasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.email.PasswordRequest;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.InvalidTokenException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.UserIsNotActiveException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.email.EmailService;

@DisplayName("AuthService Tests")
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;
    
    @Mock
    private EmailService emailService;
    
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    @DisplayName("Should login successfully when user is found and active")
    void doLogin_Successful() {
        // Arrange
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        LoginForm form = new LoginForm(email, password);

        User user = new User();
        user.setDisabled(false);

        when(userRepository.findByEmail(form.email())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>()));

        when(tokenService.generateToken(any(User.class))).thenReturn("token");

        // Act
        String result = authService.doLogin(form);

        // Assert
        assertEquals("token", result, () -> "Expected the token to be 'token'");
    }

    @Test
    @DisplayName("Should throw exception when user is not found")
    void doLogin_UserNotFound() {
        // Arrange
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        LoginForm form = new LoginForm(email, password);

        when(userRepository.findByEmail(form.email())).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> authService.doLogin(form),
                () -> "Expected an EntityNotFoundException to be thrown");

        assertTrue(exception.getMessage().contains("User not found for email"),
                () -> "Expected exception message to contain 'User not found for email'");
    }

    @Test
    @DisplayName("Should throw exception when user is inactive")
    void doLogin_UserInactive() {
        // Arrange
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        LoginForm form = new LoginForm(email, password);

        User user = new User();
        user.setDisabled(true);

        when(userRepository.findByEmail(form.email())).thenReturn(Optional.of(user));

        // Act & Assert
        UserIsNotActiveException exception = assertThrows(UserIsNotActiveException.class,
                () -> authService.doLogin(form),
                () -> "Expected a UserIsNotActiveException to be thrown");

        assertEquals("Acesso negado.", exception.getMessage(),
                () -> "Expected message to be 'Acesso negado.'");
    }
    
    @Test
    @DisplayName("Should send recovery password email when user is found")
    void requestRecoveryPassword_Success() {
        // Arrange
        String email = faker.internet().emailAddress();
        RecoveryPasswordForm form = new RecoveryPasswordForm(email);
        User user = new User();

        when(userRepository.findByEmail(form.email())).thenReturn(Optional.of(user));
        when(tokenService.generateToken(any(User.class))).thenReturn("token");

        // Act
        authService.requestRecoveryPassword(form);

        // Assert
        verify(emailService, times(1)).send(any(PasswordRequest.class));
    }

    @Test
    @DisplayName("Should throw exception when user is not found")
    void requestRecoveryPassword_UserNotFound() {
        // Arrange
        String email = faker.internet().emailAddress();
        RecoveryPasswordForm form = new RecoveryPasswordForm(email);

        when(userRepository.findByEmail(form.email())).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> authService.requestRecoveryPassword(form),
                () -> "Expected an EntityNotFoundException to be thrown");

        assertEquals("Usuário não encontrado com e-mail: " + form.email(), exception.getMessage(),
                () -> "Expected exception message to be 'Usuário não encontrado com e-mail: " + form.email() + "'");
    }


    
    
    @Test
    @DisplayName("Should reset password successfully when token is valid")
    void resetPassword_Success() {
        // Arrange
        String email = faker.internet().emailAddress();
        String newPassword = faker.internet().password();
        ResetPasswordForm form = new ResetPasswordForm("valid-token", newPassword);
        User user = new User();

        when(tokenService.validateToken(form.token())).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        authService.resetPassword(form);

        // Assert
        verify(userRepository, times(1)).save(user);
        verify(tokenService, times(1)).invalidateToken(form.token());
        assertEquals(passwordEncoder.encode(newPassword), user.getPassword(),
                () -> "Expected the user's password to be updated");
    }

    @Test
    @DisplayName("Should throw InvalidTokenException when token is invalid")
    void resetPassword_InvalidToken() {
        // Arrange
        ResetPasswordForm form = new ResetPasswordForm("invalid-token", faker.internet().password());

        when(tokenService.validateToken(form.token())).thenReturn(null);

        // Act & Assert
        InvalidTokenException exception = assertThrows(InvalidTokenException.class,
                () -> authService.resetPassword(form),
                () -> "Expected an InvalidTokenException to be thrown");

        assertEquals("Token inválido ou expirado", exception.getMessage(),
                () -> "Expected exception message to be 'Token inválido ou expirado'");
    }
    
    @Test
    @DisplayName("Should throw exception when user is not found")
    void resetPassword_UserNotFound() {
        // Arrange
        ResetPasswordForm form = new ResetPasswordForm("valid-token", faker.internet().password());
        String emailFromToken = faker.internet().emailAddress(); // Simule um e-mail válido

        when(tokenService.validateToken(form.token())).thenReturn(emailFromToken);
        when(userRepository.findByEmail(emailFromToken)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> authService.resetPassword(form),
                () -> "Expected an EntityNotFoundException to be thrown");

        assertEquals("User not found for email: " + emailFromToken, exception.getMessage(),
                () -> "Expected exception message to be 'User not found for email: " + emailFromToken + "'");
    }




}
