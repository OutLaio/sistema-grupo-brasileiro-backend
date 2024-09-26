package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

public class RecoveryTokenTest {

    private final Faker faker = new Faker();

    /**
     * Cria uma instância de RecoveryToken com valores fixos para garantir a consistência do teste.
     * 
     * @return Uma instância de RecoveryToken preenchida com dados fixos.
     */
    private RecoveryToken createSampleRecoveryToken(Long id, String token) {
        RecoveryToken recoveryToken = new RecoveryToken();
        recoveryToken.setId(id);
        recoveryToken.setToken(token);
        return recoveryToken;
    }

    /**
     * Testa o método toString da classe RecoveryToken.
     * Verifica se o método toString retorna uma representação correta da instância
     * com os valores de id e token.
     */
    @Test
    @DisplayName("Should return a correct string representation of the RecoveryToken instance")
    void testToString() {
        // Crie um RecoveryToken com valores fixos
        RecoveryToken recoveryToken = createSampleRecoveryToken(123L, "abc123");

        // Obtenha a string real do método toString
        String actualToString = recoveryToken.toString();

        // Gere a string esperada com base na implementação atual
        String expectedToString = "RecoveryToken(id=123, token=abc123)";

        // Compare a string real com a esperada
        assertThat(actualToString).isEqualTo(expectedToString);
    }

    /**
     * Testa os métodos equals e hashCode da classe RecoveryToken.
     * Verifica se duas instâncias com os mesmos valores de id e token são iguais
     * e se têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should consider equal instances with the same id and token")
    void testEqualsAndHashCode() {
        // Cria duas instâncias idênticas com o mesmo ID e token
        Long id = faker.number().randomNumber();
        String token = faker.lorem().word();

        RecoveryToken token1 = createSampleRecoveryToken(id, token);
        RecoveryToken token2 = createSampleRecoveryToken(id, token); 

        // Cria uma terceira instância com um ID diferente
        RecoveryToken token3 = createSampleRecoveryToken(id + 1, token); 

        // Verifica que dois objetos com o mesmo ID e token são considerados iguais
        assertThat(token1).isEqualTo(token2);
        assertThat(token1.hashCode()).isEqualTo(token2.hashCode());

        // Verifica que objetos com IDs diferentes não são iguais
        assertThat(token1).isNotEqualTo(token3);
        assertThat(token1.hashCode()).isNotEqualTo(token3.hashCode());
    }

    /**
     * Testa a criação de um RecoveryToken com valores nulos.
     * Verifica se a instância pode ser criada e não é nula.
     */
    @Test
    @DisplayName("Should create a RecoveryToken instance and not be null")
    void testCreateRecoveryTokenWithNullValues() {
        RecoveryToken recoveryToken = createSampleRecoveryToken(null, null);
        assertNotNull(recoveryToken);
    }

    /**
     * Testa a criação de um RecoveryToken com valores padrão.
     * Verifica se os atributos são atribuídos corretamente.
     */
    @Test
    @DisplayName("Should correctly set attributes when creating RecoveryToken with default values")
    void testCreateRecoveryTokenWithDefaultValues() {
        RecoveryToken recoveryToken = createSampleRecoveryToken(1L, "defaultToken");
        assertThat(recoveryToken.getId()).isEqualTo(1L);
        assertThat(recoveryToken.getToken()).isEqualTo("defaultToken");
    }

    /**
     * Testa a alteração do atributo token de um RecoveryToken.
     * Verifica se o método setToken altera corretamente o valor.
     */
    @Test
    @DisplayName("Should correctly set token attribute")
    void testSetToken() {
        RecoveryToken recoveryToken = createSampleRecoveryToken(1L, "initialToken");
        recoveryToken.setToken("updatedToken");
        assertThat(recoveryToken.getToken()).isEqualTo("updatedToken");
    }
}
