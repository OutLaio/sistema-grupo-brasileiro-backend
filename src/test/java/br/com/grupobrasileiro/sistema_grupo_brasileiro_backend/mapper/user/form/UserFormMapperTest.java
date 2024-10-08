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
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.form.UserFormMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
     * Testa a exceção quando o perfil não é encontrado.
     * Verifica se a exceção correta é lançada.
     */
    @Test
    @DisplayName("Should throw exception when profile not found")
    void throwExceptionWhenProfileNotFound() {
        // Arrange
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        Long profileId = 1L;

        UserForm userForm = new UserForm(email, password, profileId);

        // Simula que o perfil não foi encontrado
        when(profileRepository.findById(profileId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userFormMapper.map(userForm);
        });

        assertEquals("Profile with number " + profileId + " not found", exception.getMessage());
    }
    
    /**
     * Testa o mapeamento de UserForm com um profileId inválido (por exemplo, -1).
     * Verifica se o mapeamento lida corretamente com um profileId inválido.
     */
    @Test
    @DisplayName("Should handle invalid profileId in UserForm")
    void handleInvalidProfileIdInUserForm() {
        // Arrange
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        long invalidProfileId = -1L; // Valor inválido para profileId

        UserForm userForm = new UserForm(email, password, invalidProfileId);

        // Simula que o perfil não foi encontrado
        when(profileRepository.findById(invalidProfileId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userFormMapper.map(userForm);
        });

        assertEquals("Profile with number " + invalidProfileId + " not found", exception.getMessage());
    }

    /**
     * Testa o mapeamento de UserForm com senha vazia.
     * Verifica se a validação lida corretamente com uma senha vazia.
     */
    @Test
    @DisplayName("Should throw validation exception for empty password in UserForm")
    void handleEmptyPasswordInUserForm() {
        // Arrange
        String email = faker.internet().emailAddress();
        String password = "";  // Senha vazia
        Long profileId = 1L;

        UserForm userForm = new UserForm(email, password, profileId);
        Profile profile = new Profile(); // Simula um perfil válido
        when(profileRepository.findById(profileId)).thenReturn(Optional.of(profile));

        // Create a Validator to validate the UserForm object
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Act: Validate the UserForm manually
        Set<ConstraintViolation<UserForm>> violations = validator.validate(userForm);

        // Assert: There should be validation violations due to empty password
        assertFalse(violations.isEmpty(), "There should be validation violations");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("password")));
    }
}
