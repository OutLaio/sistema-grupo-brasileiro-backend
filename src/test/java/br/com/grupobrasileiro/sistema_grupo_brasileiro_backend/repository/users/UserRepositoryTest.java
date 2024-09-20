package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    /**
     * Testa a criação e recuperação de um usuário pelo email.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should create and retrieve a user by email")
    void testCreateAndRetrieveUserByEmail() {
        // Arrange
        Profile profile = new Profile();
        profile.setDescription("Test Profile");
        // Salve o perfil no banco, se necessário, antes de usar
        
        User user = new User();
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());
        user.setDisabled(false);
        user.setProfile(profile); // Atribua o perfil criado
        
        // Act
        userRepository.save(user);
        Optional<User> retrievedUser = userRepository.findByEmail(user.getEmail());

        // Assert
        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getEmail()).isEqualTo(user.getEmail());
    }

    /**
     * Testa a busca de um usuário com um email que não existe.
     */
    @Test
    @DisplayName("Should return empty when retrieving user by non-existing email")
    void testRetrieveUserByNonExistingEmail() {
        // Act
        Optional<User> retrievedUser = userRepository.findByEmail("nonexistent@example.com");

        // Assert
        assertThat(retrievedUser).isNotPresent();
    }
}
