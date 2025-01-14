package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.RecoveryToken;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
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
    @DisplayName("Should create and check existence of a recovery token")
    void testCreateAndCheckRecoveryToken() {
        // Arrange
        String tokenValue = faker.internet().uuid();
        RecoveryToken recoveryToken = new RecoveryToken();
        recoveryToken.setToken(tokenValue);
        
        // Act
        recoveryTokenRepository.save(recoveryToken);
        
        // Assert: Verifica se o token existe
        assertThat(recoveryTokenRepository.existsByToken(tokenValue)).isTrue();
    }

    /**
     * Testa a exclusão de um token de recuperação.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should delete a recovery token and check its absence")
    void testDeleteRecoveryToken() {
        // Arrange
        String tokenValue = faker.internet().uuid();
        RecoveryToken recoveryToken = new RecoveryToken();
        recoveryToken.setToken(tokenValue);
        recoveryToken = recoveryTokenRepository.save(recoveryToken);

        // Act
        recoveryTokenRepository.delete(recoveryToken);

        // Assert: Verifica que o token não existe mais
        assertThat(recoveryTokenRepository.existsByToken(tokenValue)).isFalse();
    }
}
