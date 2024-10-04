package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;
    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
        // Limpa o banco de dados antes de cada teste
        userRepository.deleteAll();
        profileRepository.deleteAll();
    }

    private Profile findOrCreateProfile() {
        List<Profile> existingProfiles = profileRepository.findAll();
        if (!existingProfiles.isEmpty()) {
            return existingProfiles.get(0);
        } else {
            Profile newProfile = new Profile();
            newProfile.setDescription("Test Profile");
            // Não definimos o ID, deixamos o banco de dados gerar automaticamente
            return profileRepository.save(newProfile);
        }
    }

    @Test
    @DisplayName("Should return empty when retrieving user by non-existing email")
    void testRetrieveUserByNonExistingEmail() {
        Optional<User> retrievedUser = userRepository.findByEmail("nonexistent@example.com");
        assertThat(retrievedUser).isNotPresent();
    }

    @Test
    @Rollback(false)
    @DisplayName("Should delete a user")
    void testDeleteUser() {
        Profile profile = findOrCreateProfile();

        User user = new User();
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());
        user.setDisabled(false);
        user.setProfile(profile);
        
        user = userRepository.save(user);

        userRepository.delete(user);
        Optional<User> deletedUser = userRepository.findByEmail(user.getEmail());

        assertThat(deletedUser).isNotPresent();
    }

    @Test
    @Rollback(false)
    @DisplayName("Should update an existing user")
    void testUpdateUser() {
        Profile profile = findOrCreateProfile();

        User user = new User();
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());
        user.setDisabled(false);
        user.setProfile(profile);
        
        user = userRepository.save(user);

        String updatedEmail = faker.internet().emailAddress();
        user.setEmail(updatedEmail);
        userRepository.save(user);

        Optional<User> updatedUser = userRepository.findByEmail(updatedEmail);

        assertThat(updatedUser).isPresent();
        assertThat(updatedUser.get().getEmail()).isEqualTo(updatedEmail);
    }
}