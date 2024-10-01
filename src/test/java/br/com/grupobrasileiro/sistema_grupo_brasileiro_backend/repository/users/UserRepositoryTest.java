package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import jakarta.transaction.Transactional;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;


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
        
        // Salvar o perfil no banco de dados
        Profile savedProfile = profileRepository.save(profile); 
        System.out.println("Saved Profile ID: " + savedProfile.getId());

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setDisabled(false);
        user.setProfile(savedProfile); // Atribua o perfil salvo ao User

        // Act
        User savedUser = userRepository.save(user);

        // Tente recuperar o usuário
        Optional<User> retrievedUser = userRepository.findByEmail(savedUser.getEmail());

        // Assert
        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getEmail()).isEqualTo(savedUser.getEmail());
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
        Profile profile = new Profile();
        profile.setDescription("Test Profile");
        Profile savedProfile = profileRepository.save(profile); 

        User user = new User();
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());
        user.setDisabled(false);
        user.setProfile(savedProfile);
        
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
        Profile profile = new Profile();
        profile.setDescription("Test Profile");
        Profile savedProfile = profileRepository.save(profile); 

        User user = new User();
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());
        user.setDisabled(false);
        user.setProfile(savedProfile);
        
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

  
}
