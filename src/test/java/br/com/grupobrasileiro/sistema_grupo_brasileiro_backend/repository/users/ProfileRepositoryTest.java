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

    /**
     * Testa a recuperação de um perfil que não existe.
     */
    @Test
    @DisplayName("Should return empty when retrieving non-existing profile")
    void testRetrieveNonExistingProfile() {
        // Act
        Optional<Profile> retrievedProfile = profileRepository.findById(999L); // ID que não existe

        // Assert
        assertThat(retrievedProfile).isNotPresent();
    }

    /**
     * Testa a atualização de um perfil existente.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should update an existing profile")
    void testUpdateProfile() {
        // Arrange
        Profile profile = new Profile();
        profile.setDescription(faker.job().title());
        profile = profileRepository.save(profile);

        // Act
        profile.setDescription(faker.job().field());
        profileRepository.save(profile);
        Optional<Profile> updatedProfile = profileRepository.findById(profile.getId());

        // Assert
        assertThat(updatedProfile).isPresent();
        assertThat(updatedProfile.get().getDescription()).isEqualTo(profile.getDescription());
    }

    /**
     * Testa a criação de perfis com descrições diferentes.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should create profiles with different descriptions")
    void testCreateMultipleProfiles() {
        // Arrange
        Profile profile1 = new Profile();
        profile1.setDescription(faker.job().title());
        profileRepository.save(profile1);

        Profile profile2 = new Profile();
        profile2.setDescription(faker.job().title());
        profileRepository.save(profile2);

        // Act
        Optional<Profile> retrievedProfile1 = profileRepository.findById(profile1.getId());
        Optional<Profile> retrievedProfile2 = profileRepository.findById(profile2.getId());

        // Assert
        assertThat(retrievedProfile1).isPresent();
        assertThat(retrievedProfile2).isPresent();
        assertThat(retrievedProfile1.get().getDescription()).isNotEqualTo(retrievedProfile2.get().getDescription());
    }
}
