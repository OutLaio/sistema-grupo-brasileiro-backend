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

    @Autowired
    private ProfileRepository profileRepository; // Adicionei a injeção do ProfileRepository

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
        // Salve o perfil no banco
        Profile savedProfile = profileRepository.save(profile); 

        User user = new User();
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());
        user.setDisabled(false);
        user.setProfile(savedProfile); // Atribua o perfil criado
        
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

    /**
     * Testa a exclusão de um usuário.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should delete a user")
    void testDeleteUser() {
        // Arrange
        User user = new User();
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());
        user.setDisabled(false);
        user.setProfile(new Profile()); // Atribua um novo perfil
        
        user = userRepository.save(user);

        // Act
        userRepository.delete(user);
        Optional<User> deletedUser = userRepository.findByEmail(user.getEmail());

        // Assert
        assertThat(deletedUser).isNotPresent();
    }

    /**
     * Testa a atualização de um usuário.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should update an existing user")
    void testUpdateUser() {
        // Arrange
        User user = new User();
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());
        user.setDisabled(false);
        user.setProfile(new Profile()); // Atribua um novo perfil
        
        user = userRepository.save(user);

        // Act
        String updatedEmail = faker.internet().emailAddress();
        user.setEmail(updatedEmail);
        userRepository.save(user);

        Optional<User> updatedUser = userRepository.findByEmail(updatedEmail);

        // Assert
        assertThat(updatedUser).isPresent();
        assertThat(updatedUser.get().getEmail()).isEqualTo(updatedEmail);
    }

    /**
     * Testa a criação de um usuário com um email duplicado.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should not allow creation of a user with an existing email")
    void testCreateUserWithDuplicateEmail() {
        // Arrange
        User user1 = new User();
        user1.setEmail(faker.internet().emailAddress());
        user1.setPassword(faker.internet().password());
        user1.setDisabled(false);
        user1.setProfile(new Profile()); // Atribua um novo perfil
        userRepository.save(user1);

        User user2 = new User();
        user2.setEmail(user1.getEmail()); // Duplicando o e-mail
        user2.setPassword(faker.internet().password());
        user2.setDisabled(false);
        user2.setProfile(new Profile()); // Atribua um novo perfil

        // Act & Assert
        // Verifica se uma exceção é lançada ao tentar salvar um usuário com email duplicado
        try {
            userRepository.save(user2);
        } catch (Exception e) {
            // Assert para garantir que o primeiro usuário ainda é recuperável
            Optional<User> retrievedUser = userRepository.findByEmail(user1.getEmail());
            assertThat(retrievedUser).isPresent();
            assertThat(retrievedUser.get().getEmail()).isEqualTo(user1.getEmail());
            return; // Retorna para evitar falha no teste
        }
        // Se nenhuma exceção foi lançada, o teste falhou
        assertThat(false).isTrue();
    }
}

