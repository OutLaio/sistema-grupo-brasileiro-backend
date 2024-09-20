package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
public class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository profileRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    /**
     * Testa a criação e recuperação de um perfil.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should create and retrieve a profile")
    void testCreateAndRetrieveProfile() {
        // Arrange
        Profile profile = new Profile();
        profile.setDescription(faker.job().title());
        
        // Act
        profileRepository.save(profile);
        Optional<Profile> retrievedProfile = profileRepository.findById(profile.getId());

        // Assert
        assertThat(retrievedProfile).isPresent();
        assertThat(retrievedProfile.get().getDescription()).isEqualTo(profile.getDescription());
    }
    
    /**
     * Testa a exclusão de um perfil.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should delete a profile")
    void testDeleteProfile() {
        // Arrange
        Profile profile = new Profile();
        profile.setDescription(faker.job().title());
        profile = profileRepository.save(profile);

        // Act
        profileRepository.delete(profile);
        Optional<Profile> deletedProfile = profileRepository.findById(profile.getId());

        // Assert
        assertThat(deletedProfile).isNotPresent();
    }
}
