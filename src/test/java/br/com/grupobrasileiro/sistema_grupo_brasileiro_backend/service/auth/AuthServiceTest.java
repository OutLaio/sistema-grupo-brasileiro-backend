package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.auth;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.LoginForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.RecoveryPasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.ResetPasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.view.TokenView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.email.PasswordRequest;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.UserIsNotActiveException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view.EmployeeViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.email.EmailService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("AuthService Tests")
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TemplateEngine templateEngine;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @Mock
    private EmailService emailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmployeeViewMapper employeeViewMapper;

    @InjectMocks
    private AuthService authService;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    @DisplayName("Should login successfully when user is active")
    void shouldLoginSuccessfullyWhenUserIsActive() {
        // Arrange
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        LoginForm form = new LoginForm(email, password);

        User user = mockUser(email, password, false);
        Profile profile = new Profile();
        profile.setDescription("User Profile Description");
        user.setProfile(profile);
        Employee employee = new Employee();
        user.setEmployee(employee);
        EmployeeView expectedEmployeeView = mockEmployeeView(user);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
        when(tokenService.generateToken(any(User.class))).thenReturn("valid-token");
        when(employeeViewMapper.map(any(Employee.class))).thenReturn(expectedEmployeeView);

        // Act
        TokenView result = authService.doLogin(form, authenticationManager);

        // Assert
        assertNotNull(result, "TokenView should not be null");
        assertEquals("valid-token", result.token(), "Generated token should match");
        assertEquals(expectedEmployeeView, result.employee(), "EmployeeView should match the expected view");
    }

    @Test
    @DisplayName("Should throw exception when user is not found during login")
    void shouldThrowExceptionWhenUserNotFoundDuringLogin() {
        // Arrange
        LoginForm form = new LoginForm(faker.internet().emailAddress(), faker.internet().password());
        when(userRepository.findByEmail(form.email())).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> authService.doLogin(form, authenticationManager));

        assertTrue(exception.getMessage().contains("O e-mail informado não está cadastrado."),
                "Expected exception message to indicate missing email");
    }

    @Test
    @DisplayName("Should throw exception when user is inactive during login")
    void shouldThrowExceptionWhenUserIsInactiveDuringLogin() {
        // Arrange
        User inactiveUser = mockUser(faker.internet().emailAddress(), faker.internet().password(), true);
        LoginForm form = new LoginForm(inactiveUser.getEmail(), faker.internet().password());

        // Garantir que o mock do usuário seja configurado corretamente
        when(userRepository.findByEmail(form.email())).thenReturn(Optional.of(inactiveUser));
        // Mock do método de autenticação
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials")); // ou outro comportamento desejado

        // Act & Assert
        UserIsNotActiveException exception = assertThrows(UserIsNotActiveException.class,
                () -> authService.doLogin(form, authenticationManager));

        assertEquals("Sua conta está desativada. Entre em contato com o administrador para mais informações.",
                exception.getMessage());
    }

    @Test
    @DisplayName("Should successfully request password recovery")
    void shouldRequestRecoveryPasswordSuccessfully() {
        // Arrange
        String email = "test@example.com";
        String token = "recovery-token";
        String resetUrl = "http://localhost:4200/redefinir-senha?token=" + token;
        String userName = "John Doe";

        RecoveryPasswordForm form = new RecoveryPasswordForm(email);

        User user = new User();
        user.setEmail(email);
        Employee employee = new Employee();
        employee.setName("John");
        employee.setLastName("Doe");
        user.setEmployee(employee);

        // Mocking dependencies
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(tokenService.generatePasswordToken(user)).thenReturn(token);
        when(templateEngine.process(eq("request-password"), any(Context.class))).thenReturn("Email body content");

        // Act
        authService.requestRecoveryPassword(form);

        // Assert
        verify(userRepository).findByEmail(email);
        verify(tokenService).generatePasswordToken(user);
        verify(templateEngine).process(eq("request-password"), any(Context.class));

        ArgumentCaptor<PasswordRequest> emailCaptor = ArgumentCaptor.forClass(PasswordRequest.class);
        verify(emailService).send(emailCaptor.capture());
        PasswordRequest capturedEmail = emailCaptor.getValue();

        assertEquals("no-reply@everdev.com", capturedEmail.emailFrom());
        assertEquals(email, capturedEmail.emailTo());
        assertEquals("Password Reset", capturedEmail.subject());
        assertEquals("Email body content", capturedEmail.text());
    }

    @Test
    @DisplayName("Should throw exception when requesting recovery for nonexistent user")
    void shouldThrowExceptionWhenRequestingRecoveryForNonexistentUser() {
        // Arrange
        RecoveryPasswordForm form = new RecoveryPasswordForm(faker.internet().emailAddress());
        when(userRepository.findByEmail(form.email())).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> authService.requestRecoveryPassword(form));

        assertEquals("User not found for email: " + form.email(), exception.getMessage());
    }

    @Test
    @DisplayName("Should successfully reset password")
    void shouldResetPasswordSuccessfully() {
        // Arrange
        String email = faker.internet().emailAddress();
        User user = mockUser(email, null, false);
        ResetPasswordForm form = new ResetPasswordForm("valid-token", "new-password");

        when(tokenService.validatePasswordToken(form.token())).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(form.password())).thenReturn("encoded-password");

        // Act
        authService.resetPassword(form);

        // Assert
        assertEquals("encoded-password", user.getPassword(), "Password should be updated");
        verify(userRepository, times(1)).save(user);
        verify(tokenService, times(1)).invalidateToken(form.token());
    }

    private User mockUser(String email, String password, boolean disabled) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setDisabled(disabled);

        // Inicializando Profile para evitar NullPointerException
        Profile profile = new Profile();
        profile.setDescription("User Profile Description");  // Você pode modificar a descrição conforme necessário
        user.setProfile(profile);

        return user;
    }

    private EmployeeView mockEmployeeView(User user) {
        return new EmployeeView(1L, null, "John", "Doe", "1234567890", "IT", "Developer", "Main Branch", 1L);
    }
}
