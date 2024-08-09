package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

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
        entityManager.persist(user);
        entityManager.flush();

        // Act
        UserDetails foundUserDetails = userRepository.findByEmail(email);

        // Assert
        assertThat(foundUserDetails).isNotNull();
        assertThat(foundUserDetails).isInstanceOf(User.class);

        User foundUser = (User) foundUserDetails;
        assertThat(foundUser.getUsername()).isEqualTo(email);
        assertThat(foundUser.getName()).isEqualTo(user.getName());
    }

    @Test
    public void testFindByRole() {
        // Arrange
        int role = faker.number().numberBetween(1, 3);
        for (int i = 0; i < 5; i++) {
            User user = new User(
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
            entityManager.persist(user);
        }
        entityManager.flush();

        // Act
        Page<User> usersPage = userRepository.findByRole(role, PageRequest.of(0, 10));

        // Assert
        assertThat(usersPage).isNotNull();
        assertThat(usersPage.getTotalElements()).isGreaterThan(0);

        for (User user : usersPage.getContent()) {
            assertThat(user.getRole()).isEqualTo(role);
        }
    }
}

