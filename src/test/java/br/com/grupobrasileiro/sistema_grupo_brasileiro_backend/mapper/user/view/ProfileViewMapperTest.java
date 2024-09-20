package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.profile.view.ProfileView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;

public class ProfileViewMapperTest {

    private ProfileViewMapper profileViewMapper;
    private Faker faker;

    @BeforeEach
    void setUp() {
        profileViewMapper = new ProfileViewMapper();
        faker = new Faker();
    }

    /**
     * Testa o mapeamento de Profile para ProfileView.
     * Verifica se todos os campos são mapeados corretamente.
     */
    @Test
    @DisplayName("Should map Profile to ProfileView correctly")
    void shouldMapProfileToProfileViewCorrectly() {
        // Arrange
        Long id = faker.number().randomNumber();
        String description = faker.company().industry();
        Profile profile = new Profile(id, description, new HashSet<>());

        // Act
        ProfileView profileView = profileViewMapper.map(profile);

        // Assert
        assertEquals(profile.getId(), profileView.id(), "ProfileView ID should match");
        assertEquals(profile.getDescription(), profileView.description(), "ProfileView description should match");
    }
}
