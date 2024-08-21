package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.github.javafaker.Faker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        faker = new Faker();
    }

    @Test
    public void testFindByEmail() {
        // Arrange
        String email = faker.internet().emailAddress();
        User user = new User(
            null,  // ID é gerado automaticamente
            faker.name().firstName(),
            faker.name().lastName(),
            faker.phoneNumber().phoneNumber(),
            faker.job().field(),
            faker.job().title(),
            faker.lorem().word(),
            email,
            faker.internet().password(),
            faker.number().numberBetween(1, 3)
        );
        userRepository.save(user);

        // Act
        UserDetails foundUserDetails = userRepository.findByEmail(email);

        // Assert
        assertThat(foundUserDetails).isNotNull();
        assertThat(foundUserDetails).isInstanceOf(User.class);  // Certifique-se de que o tipo é User
        assertThat(foundUserDetails.getUsername()).isEqualTo(email);
        assertThat(((User)foundUserDetails).getName()).isEqualTo(user.getName());
        assertThat(((User)foundUserDetails).getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void testFindByRole() {
        // Arrange
        int role = faker.number().numberBetween(1, 3);
        for (int i = 0; i < 5; i++) {
            User user = new User(
                null,  // ID é gerado automaticamente
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().phoneNumber(),
                faker.job().field(),
                faker.job().title(),
                faker.lorem().word(),
                faker.internet().emailAddress(),
                faker.internet().password(),
                role
            );
            userRepository.save(user);
        }

        // Act
        Page<User> usersPage = userRepository.findByRole(role, PageRequest.of(0, 10));

        // Assert
        assertThat(usersPage).isNotNull();
        assertThat(usersPage.getTotalElements()).isGreaterThan(0);
        for (User user : usersPage.getContent()) {
            assertThat(user.getRole()).isEqualTo(role);
        }
    }

    @Test
    public void testFindByEmailNotFound() {
        // Act
        UserDetails foundUserDetails = userRepository.findByEmail("nonexistent@example.com");

        // Assert
        assertThat(foundUserDetails).isNull();
    }

    @Test
    public void testFindByRoleEmpty() {
        // Act
        Page<User> usersPage = userRepository.findByRole(faker.number().numberBetween(4, 10), PageRequest.of(0, 10));

        // Assert
        assertThat(usersPage).isNotNull();
        assertThat(usersPage.getTotalElements()).isEqualTo(0);
    }
}
