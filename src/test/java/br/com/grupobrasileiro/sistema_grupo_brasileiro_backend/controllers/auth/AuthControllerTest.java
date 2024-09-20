package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controllers.auth;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.auth.AuthController;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.LoginForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.RecoveryPasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.ResetPasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.view.TokenView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form.EmployeeForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form.UserDetailsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.auth.AuthService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.user.EmployeeService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.user.UserService;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker(); // Inicializando o Faker para gerar dados dinâmicos
    }

    @Test
    @DisplayName("Should register a new employee successfully")
    void shouldRegisterNewEmployeeSuccessfully() {
        // Arrange
        UserDetailsForm userDetailsForm = new UserDetailsForm(
            // Using Faker to generate dynamic data for user and employee forms
            new EmployeeForm(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().phoneNumber(),
                null, null, null, null
            ),
            new UserForm(
                faker.internet().emailAddress(),
                faker.internet().password(),
                1L
            )
        );

        User mockUser = new User(); // Mocking User object
        EmployeeView mockEmployeeView = new EmployeeView(
            faker.number().randomNumber(), null, faker.name().fullName(),
            faker.company().name(), null, null, null, null, null
        );

        when(userService.create(any())).thenReturn(mockUser);
        when(employeeService.addEmployee(any(), any())).thenReturn(mockEmployeeView);

        // Act
        ResponseEntity<?> response = authController.register(userDetailsForm);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), 
            () -> "Should return HTTP 201 CREATED status");
        assertEquals(mockEmployeeView, response.getBody(),
            () -> "The response body should be the expected EmployeeView");
    }

    @Test
    @DisplayName("Should login successfully and return a token")
    void shouldLoginSuccessfullyAndReturnToken() {
        // Arrange
        LoginForm loginForm = new LoginForm(faker.internet().emailAddress(), faker.internet().password());
        String mockToken = faker.internet().uuid(); // Gerando token dinâmico com o Faker
        
        when(authService.doLogin(any())).thenReturn(mockToken);

        // Act
        ResponseEntity<?> response = authController.login(loginForm);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(),
            () -> "Should return HTTP 200 OK status");
        assertEquals(new TokenView(mockToken), response.getBody(),
            () -> "The response body should be a TokenView with the generated token");
    }

    @Test
@DisplayName("Should request password reset successfully")
void shouldRequestPasswordResetSuccessfully() {
    // Arrange
    RecoveryPasswordForm recoveryPasswordForm = new RecoveryPasswordForm(faker.internet().emailAddress());

    // Act
    ResponseEntity<String> response = authController.requestReset(recoveryPasswordForm);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode(),
        () -> "Should return HTTP 200 OK status");
    assertEquals("E-mail enviado com sucesso!", response.getBody(),
        () -> "The success message should be 'E-mail enviado com sucesso!'");
}


   @Test
@DisplayName("Should reset password successfully")
void shouldResetPasswordSuccessfully() {
    // Arrange
    ResetPasswordForm resetPasswordForm = new ResetPasswordForm(faker.internet().emailAddress(), faker.internet().password());
    // Configurar o mock para não fazer nada ou retornar um valor adequado
    doNothing().when(authService).resetPassword(any());

    // Act
    ResponseEntity<String> response = authController.resetPassword(resetPasswordForm);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode(),
        () -> "Should return HTTP 200 OK status");
    assertEquals("Password successfully changed!", response.getBody(),
        () -> "The success message should be 'Password successfully changed!'");
}

}
