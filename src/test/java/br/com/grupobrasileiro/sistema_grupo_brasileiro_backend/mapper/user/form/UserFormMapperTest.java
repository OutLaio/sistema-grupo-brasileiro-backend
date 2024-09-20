package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.form;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.ProfileRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

class UserFormMapperTest {

    @InjectMocks
    private UserFormMapper userFormMapper;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    /**
     * Testa o mapeamento de UserForm para User.
     * Verifica se todos os campos são mapeados corretamente.
     */
    @Test
    @DisplayName("Should map UserForm to User correctly")
    void shouldMapUserFormToUserSuccessfully() {
        // Arrange
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        Long profileId = 1L;

        UserForm userForm = new UserForm(
                email,
                password,
                profileId
        );

        Profile profile = new Profile(); // Supondo que você tenha uma classe Profile
        when(profileRepository.findById(profileId)).thenReturn(Optional.of(profile));
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        // Act
        User result = userFormMapper.map(userForm);

        // Assert
        assertNotNull(result, "Mapped User should not be null");
        assertNull(result.getId(), "User ID should be null");
        assertEquals(profile, result.getProfile(), "User profile should match");
        assertEquals(email, result.getEmail(), "User email should match");
        assertEquals("encodedPassword", result.getPassword(), "User password should be encoded");
        assertFalse(result.getDisabled(), "User should not be disabled");
        assertNull(result.getEmployee(), "User employee should be null");
    }

    /**
     * Testa a exceção quando o perfil não é encontrado.
     * Verifica se a exceção correta é lançada.
     */
    @Test
    @DisplayName("Should throw exception when profile not found")
    void shouldThrowExceptionWhenProfileNotFound() {
        // Arrange
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        Long profileId = 1L;

        UserForm userForm = new UserForm(
                email,
                password,
                profileId
        );

        when(profileRepository.findById(profileId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            userFormMapper.map(userForm);
        });

        assertEquals("Profile with number " + profileId + " not found", exception.getMessage());
    }
}
