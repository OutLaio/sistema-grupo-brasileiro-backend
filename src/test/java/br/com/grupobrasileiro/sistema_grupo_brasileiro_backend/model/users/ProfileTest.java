package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

/**
 * Testa a classe Profile.
 * Verifica o funcionamento dos métodos gerados pelo Lombok, construtores e métodos toString.
 */
public class ProfileTest {

    /**
     * Cria uma instância de Profile com dados fixos para os testes.
     * 
     * @param id O ID do perfil.
     * @param description A descrição do perfil.
     * @return Uma instância de Profile preenchida com dados fixos.
     */
    private Profile createSampleProfile(Long id, String description) {
        Profile profile = new Profile();
        profile.setId(id);
        profile.setDescription(description);
        return profile;
    }

    /**
     * Testa o construtor padrão da classe Profile.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    void testDefaultConstructor() {
        Profile profile = new Profile();
        assertThat(profile).isNotNull();
    }

    /**
     * Testa os métodos setters e getters da classe Profile.
     * Verifica se os métodos set e get definem e retornam corretamente os atributos
     * da instância de Profile.
     */
    @Test
    void testSettersAndGetters() {
        Profile profile = createSampleProfile(1L, "Admin");

        assertThat(profile.getId()).isEqualTo(1L);
        assertThat(profile.getDescription()).isEqualTo("Admin");
    }

    /**
     * Testa os métodos equals e hashCode da classe Profile.
     * Verifica se duas instâncias com os mesmos valores de id são iguais
     * e se têm o mesmo hashCode.
     */
    @Test
    void testEqualsAndHashCode() {
        // Cria dois perfis idênticos com o mesmo ID
        Profile profile1 = createSampleProfile(123L, "Admin");
        Profile profile2 = createSampleProfile(123L, "Admin");

        // Cria uma terceira instância com um ID diferente
        Profile profile3 = createSampleProfile(124L, "Admin");

        // Verifica que dois objetos com o mesmo ID são considerados iguais
        assertThat(profile1).isEqualTo(profile2);
        assertThat(profile1.hashCode()).isEqualTo(profile2.hashCode());

        // Verifica que objetos com IDs diferentes não são iguais
        assertThat(profile1).isNotEqualTo(profile3);
        assertThat(profile1.hashCode()).isNotEqualTo(profile3.hashCode());
    }

    /**
     * Testa o método toString da classe Profile.
     * Verifica se o método toString retorna uma representação correta da instância
     * com os valores de id e description.
     */
    @Test
    void testToString() {
        Profile profile = createSampleProfile(123L, "Admin");

        // Obtenha a string real do método toString
        String actualToString = profile.toString();

       
        String expectedToString = "Profile(id=123, description=Admin, users=[])";

        // Compare a string real com a esperada
        assertThat(actualToString).isEqualTo(expectedToString);
    }
}
