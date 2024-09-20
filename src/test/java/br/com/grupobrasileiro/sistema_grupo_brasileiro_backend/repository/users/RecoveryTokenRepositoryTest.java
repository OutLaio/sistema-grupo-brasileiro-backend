package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users;


import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.RecoveryToken;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
public class RecoveryTokenRepositoryTest {

    @Autowired
    private RecoveryTokenRepository recoveryTokenRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    /**
     * Testa a criação e recuperação de um token de recuperação.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should create and retrieve a recovery token")
    void testCreateAndRetrieveRecoveryToken() {
        // Arrange
        String tokenValue = faker.internet().uuid();
        RecoveryToken recoveryToken = new RecoveryToken();
        recoveryToken.setToken(tokenValue);
        
        // Act
        recoveryTokenRepository.save(recoveryToken);
        Optional<RecoveryToken> retrievedToken = recoveryTokenRepository.findByToken(tokenValue);

        // Assert
        assertThat(retrievedToken).isPresent();
        assertThat(retrievedToken.get().getToken()).isEqualTo(tokenValue);
    }

    /**
     * Testa a exclusão de um token de recuperação.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should delete a recovery token")
    void testDeleteRecoveryToken() {
        // Arrange
        String tokenValue = faker.internet().uuid();
        RecoveryToken recoveryToken = new RecoveryToken();
        recoveryToken.setToken(tokenValue);
        recoveryToken = recoveryTokenRepository.save(recoveryToken);

        // Act
        recoveryTokenRepository.delete(recoveryToken);
        Optional<RecoveryToken> deletedToken = recoveryTokenRepository.findByToken(tokenValue);

        // Assert
        assertThat(deletedToken).isNotPresent();
    }
}
