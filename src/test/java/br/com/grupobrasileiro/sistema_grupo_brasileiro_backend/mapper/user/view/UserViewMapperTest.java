package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void shouldMapUserToUserViewCorrectly() {
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
}
