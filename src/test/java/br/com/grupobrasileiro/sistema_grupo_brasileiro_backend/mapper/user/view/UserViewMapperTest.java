package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.profile.view.ProfileView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;

public class UserViewMapperTest {

    private UserViewMapper userViewMapper;
    private Faker faker;
    private ProfileViewMapper profileViewMapper;

    @BeforeEach
    void setUp() {
        profileViewMapper = Mockito.mock(ProfileViewMapper.class);
        userViewMapper = new UserViewMapper();
        
        // Usando reflexão para acessar o campo privado
        try {
            var profileViewMapperField = UserViewMapper.class.getDeclaredField("profileViewMapper");
            profileViewMapperField.setAccessible(true);
            profileViewMapperField.set(userViewMapper, profileViewMapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        faker = new Faker();
    }

    /**
     * Testa o mapeamento de User para UserView.
     * Verifica se todos os campos são mapeados corretamente.
     */
    @Test
    @DisplayName("Should map User to UserView correctly")
    void mapUserToUserViewCorrectly() {
        // Arrange
        Long id = faker.number().randomNumber();
        String email = faker.internet().emailAddress();
        Profile profile = new Profile();
        profile.setId(faker.number().randomNumber());
        profile.setDescription(faker.company().industry());
        
        User user = new User();
        user.setId(id);
        user.setProfile(profile);
        user.setEmail(email);
        user.setPassword("password");
        user.setDisabled(false);

        ProfileView profileView = new ProfileView(profile.getId(), profile.getDescription());
        Mockito.when(profileViewMapper.map(profile)).thenReturn(profileView);

        // Act
        UserView userView = userViewMapper.map(user);

        // Assert
        assertEquals(user.getId(), userView.id(), "UserView ID should match");
        assertEquals(user.getEmail(), userView.email(), "UserView email should match");
        assertEquals(profileView, userView.profileView(), "UserView profileView should match");
    }

    /**
     * Testa o mapeamento de User para UserView com perfil nulo.
     * Verifica se o mapeamento lida corretamente com um User que tem o perfil nulo,
     * garantindo que o campo profileView no UserView seja nulo.
     */
    @Test
    @DisplayName("Should map User to UserView with null profile")
    void mapUserToUserViewWithNullProfile() {
        // Arrange
        Long id = faker.number().randomNumber();
        String email = faker.internet().emailAddress();

        User user = new User();
        user.setId(id);
        user.setProfile(null);  // Perfil nulo
        user.setEmail(email);
        user.setPassword("password");
        user.setDisabled(false);

        // Act
        UserView userView = userViewMapper.map(user);

        // Assert
        assertNotNull(userView, "UserView should not be null when user is mapped");
        assertEquals(user.getId(), userView.id(), "UserView ID should match");
        assertEquals(user.getEmail(), userView.email(), "UserView email should match");
        assertNull(userView.profileView(), "UserView profileView should be null when User profile is null");
    }

    /**
     * Testa o mapeamento de User desativado.
     * Verifica se o campo disabled é mapeado corretamente.
     */
    @Test
    @DisplayName("Should map User with disabled status")
    void mapUserWithDisabledStatus() {
        // Arrange
        Long id = faker.number().randomNumber();
        String email = faker.internet().emailAddress();
        Profile profile = new Profile();
        profile.setId(faker.number().randomNumber());
        profile.setDescription(faker.company().industry());
        
        User user = new User();
        user.setId(id);
        user.setProfile(profile);
        user.setEmail(email);
        user.setPassword("password");
        user.setDisabled(true);  // Usuário desativado

        ProfileView profileView = new ProfileView(profile.getId(), profile.getDescription());
        Mockito.when(profileViewMapper.map(profile)).thenReturn(profileView);

        // Act
        UserView userView = userViewMapper.map(user);

        // Assert
        assertNotNull(userView, "UserView should not be null when user is disabled");
    }

    /**
     * Testa o mapeamento de User para UserView com campos faltando.
     * Aqui será considerado email obrigatório como um campo válido.
     */
    @Test
    @DisplayName("Should map User with valid email")
    void mapUserWithValidEmail() {
        // Arrange
        Long id = faker.number().randomNumber();
        Profile profile = new Profile();
        profile.setId(faker.number().randomNumber());
        profile.setDescription(faker.company().industry());
        
        User user = new User();
        user.setId(id);
        user.setProfile(profile);
        user.setEmail(faker.internet().emailAddress());  // Email válido
        user.setPassword("password");
        user.setDisabled(false);

        ProfileView profileView = new ProfileView(profile.getId(), profile.getDescription());
        Mockito.when(profileViewMapper.map(profile)).thenReturn(profileView);

        // Act
        UserView userView = userViewMapper.map(user);

        // Assert
        assertNotNull(userView, "UserView should not be null when fields are valid");
        assertEquals(user.getEmail(), userView.email(), "Email should match");
    }
}
