package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

public class UserTest {

    private final Faker faker = new Faker();

    /**
     * Cria uma instância de User com dados fixos para garantir a consistência do teste.
     * 
     * @param id O ID do usuário.
     * @param email O e-mail do usuário.
     * @param password A senha do usuário.
     * @param disabled Indica se o usuário está desativado.
     * @param profile O perfil associado ao usuário.
     * @return Uma instância de User preenchida com dados fixos.
     */
    private User createSampleUser(Long id, String email, String password, Boolean disabled, Profile profile) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setDisabled(disabled);
        user.setProfile(profile);
        return user;
    }

    /**
     * Testa o método toString da classe User.
     * Verifica se o método toString retorna uma representação correta da instância
     * com os valores de id, email, password, disabled e profile.
     */
    @Test
    @DisplayName("Should return a correct string representation of the User instance")
    void testToString() {
        // Crie um Profile para associar ao User
        Profile profile = new Profile();
        profile.setId(1L);
        profile.setDescription("Admin");

        // Crie um User com valores fixos
        User user = createSampleUser(123L, "user@example.com", "password123", false, profile);

        // Obtenha a string real do método toString
        String actualToString = user.toString();

        // Gere a string esperada com base na implementação atual
        String expectedToString = "User(id=123, profile=Profile(id=1, description=Admin, users=[]), email=user@example.com, password=password123, disabled=false, employee=null)";

        // Compare a string real com a esperada
        assertThat(actualToString).isEqualTo(expectedToString);
    }

    /**
     * Testa os métodos equals e hashCode da classe User.
     * Verifica se duas instâncias com os mesmos valores de id e email são iguais
     * e se têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should consider equal instances with the same id and email")
    void testEqualsAndHashCode() {
        // Cria um Profile para associar ao User
        Profile profile = new Profile();
        profile.setId(1L);
        profile.setDescription("Admin");

        // Cria duas instâncias idênticas com o mesmo ID, email e perfil
        Long id = faker.number().randomNumber();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        Boolean disabled = faker.bool().bool();

        User user1 = createSampleUser(id, email, password, disabled, profile);
        User user2 = createSampleUser(id, email, password, disabled, profile); 

        // Cria uma terceira instância com um ID diferente
        User user3 = createSampleUser(id + 1, email, password, disabled, profile); 

        // Verifica que dois objetos com o mesmo ID e email são considerados iguais
        assertThat(user1).isEqualTo(user2);
        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());

        // Verifica que objetos com IDs diferentes não são iguais
        assertThat(user1).isNotEqualTo(user3);
        assertThat(user1.hashCode()).isNotEqualTo(user3.hashCode());
    }

    /**
     * Testa a criação de um User com valores nulos.
     * Verifica se a instância pode ser criada e não é nula.
     */
    @Test
    @DisplayName("Should create a User instance and not be null")
    void testCreateUserWithNullValues() {
        User user = createSampleUser(null, null, null, null, null);
        assertNotNull(user);
    }

    /**
     * Testa a criação de um User com valores padrão.
     * Verifica se os atributos são atribuídos corretamente.
     */
    @Test
    @DisplayName("Should correctly set attributes when creating User with default values")
    void testCreateUserWithDefaultValues() {
        Profile profile = new Profile();
        profile.setId(1L);
        profile.setDescription("Admin");

        User user = createSampleUser(1L, "test@example.com", "testPass", false, profile);
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("test@example.com");
        assertThat(user.getPassword()).isEqualTo("testPass");
        assertThat(user.getDisabled()).isFalse();
        assertThat(user.getProfile()).isEqualTo(profile);
    }

    /**
     * Testa a alteração do atributo disabled de um User.
     * Verifica se o método setDisabled altera corretamente o valor.
     */
    @Test
    @DisplayName("Should correctly set disabled attribute")
    void testSetDisabled() {
        User user = createSampleUser(1L, "user@example.com", "password123", false, new Profile());
        user.setDisabled(true);
        assertThat(user.getDisabled()).isTrue();
    }
}
