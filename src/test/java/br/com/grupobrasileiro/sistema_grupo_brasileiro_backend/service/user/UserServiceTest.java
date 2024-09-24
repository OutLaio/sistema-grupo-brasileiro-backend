package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.user;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.PasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EmailUniqueViolationException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.IncorrectPasswordException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.form.UserFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.ProfileRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserFormMapper userFormMapper;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should deactivate user successfully")
    void shouldDeactivateUserSuccessfully() {
        // Arrange
        Long userId = 1L;
        User user = new User(userId, mock(Profile.class), "email@example.com", "password", false, null);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        userService.deactivateUser(userId);

        // Assert
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);
        assertTrue(user.getDisabled(), () -> "Expected user to be disabled");
    }

    @Test
    @DisplayName("Should throw exception when deactivating non-existing user")
    void shouldThrowExceptionWhenDeactivatingNonExistingUser() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class,
            () -> userService.deactivateUser(userId),
            () -> "Expected deactivating user to throw EntityNotFoundException");
        assertEquals("Funcionário não encontrado, id: 1", thrown.getMessage(), 
            () -> "Expected message to be 'Funcionário não encontrado, id: 1'");
    }

    @Test
    @DisplayName("Should throw exception when creating user with existing email")
    void shouldThrowExceptionWhenCreatingUserWithExistingEmail() {
        // Arrange
        UserForm userForm = new UserForm("email@example.com", "password", 1L);
        when(userRepository.findByEmail(userForm.email())).thenReturn(Optional.of(new User()));

        // Act & Assert
        EmailUniqueViolationException thrown = assertThrows(EmailUniqueViolationException.class,
            () -> userService.create(userForm),
            () -> "Expected creating user to throw EmailUniqueViolationException");
        assertEquals("Email já está em uso", thrown.getMessage(), 
            () -> "Expected message to be 'Email já está em uso'");
    }

    @Test
    @DisplayName("Should change user password successfully")
    void shouldChangePasswordSuccessfully() {
        // Arrange
        Long userId = 1L;
        PasswordForm passwordForm = new PasswordForm(userId, "currentPassword", "newPassword");
        User user = new User(userId, mock(Profile.class), "email@example.com", "encodedCurrentPassword", false, null);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(passwordForm.currentPassword())).thenReturn("encodedCurrentPassword");
        when(passwordEncoder.encode(passwordForm.newPassword())).thenReturn("encodedNewPassword");

        // Act
        userService.changePassword(passwordForm);

        // Assert
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);
        assertEquals("encodedNewPassword", user.getPassword(), 
            () -> "Expected user password to be updated");
    }

    @Test
    @DisplayName("Should throw exception when changing password with incorrect current password")
    void shouldThrowExceptionWhenChangingPasswordWithIncorrectCurrentPassword() {
        // Arrange
        Long userId = 1L;
        PasswordForm passwordForm = new PasswordForm(userId, "currentPassword", "newPassword");
        User user = new User(userId, mock(Profile.class), "email@example.com", "encodedWrongPassword", false, null);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(passwordForm.currentPassword())).thenReturn("encodedCurrentPassword");

        // Act & Assert
        IncorrectPasswordException thrown = assertThrows(IncorrectPasswordException.class,
            () -> userService.changePassword(passwordForm),
            () -> "Expected changing password to throw IncorrectPasswordException");
        assertEquals("A senha atual está incorreta!", thrown.getMessage(), 
            () -> "Expected message to be 'A senha atual está incorreta!'");
    }

    @Test
    @DisplayName("Should throw EmailUniqueViolationException when email is already in use")
    void shouldThrowEmailUniqueViolationExceptionWhenEmailIsInUse() {
        // Arrange
        Long profileId = 1L;
        String email = "email@example.com";
        String password = "password123";
        UserForm userForm = new UserForm(email, password, profileId);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        // Act & Assert
        EmailUniqueViolationException thrown = assertThrows(
            EmailUniqueViolationException.class,
            () -> userService.create(userForm),
            () -> "Expected create() to throw, but it didn't"
        );
        assertEquals("Email já está em uso", thrown.getMessage(), 
            () -> "Expected message to be 'Email já está em uso'");
        
        verify(userRepository, times(1)).findByEmail(email);
        verify(profileRepository, never()).findById(profileId);
        verify(userFormMapper, never()).map(userForm); 
        verify(userRepository, never()).save(any()); 
    }

    @Test
    @DisplayName("Should create user successfully")
    void shouldCreateUserSuccessfully() {
        // Criar uma instância de UserForm com os valores de teste
        UserForm userForm = new UserForm("test@example.com", "Password1!", 1L);

        // Criar uma instância de Profile com o id correto
        Profile profile = new Profile();
        profile.setId(1L); 

        // Criar uma instância de User com os valores esperados
        User expectedUser = new User();
        expectedUser.setEmail("test@example.com");
        expectedUser.setPassword("Password1!");
        expectedUser.setProfile(profile); 

        // Configurar o mock do repositório
        when(userRepository.findByEmail(userForm.email())).thenReturn(Optional.empty());
        when(userFormMapper.map(userForm)).thenReturn(expectedUser);
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        // Executar o método a ser testado
        User createdUser = userService.create(userForm);

        // Verificar se o usuário criado é o esperado
        assertEquals(expectedUser, createdUser, 
            () -> "Expected created user to match expected user");
        verify(userRepository).save(any(User.class));
        verify(userFormMapper).map(userForm); 
    }
}
