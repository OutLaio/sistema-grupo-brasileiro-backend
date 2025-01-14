package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

/**
 * Testa a classe Material.
 * Verifica se os métodos da classe funcionam corretamente e se os dados são configurados adequadamente.
 */
public class MaterialTest {

    private Material material;
    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();

        // Criando instância de Material
        material = new Material();
        material.setId(faker.number().randomNumber());
        material.setDescription(faker.commerce().material());
    }

    /**
     * Testa a criação da instância da classe Material.
     * Verifica se o ID e a descrição são definidos corretamente após a configuração inicial no método setUp.
     */
    @Test
    @DisplayName("Should correctly set properties of Material")
    void testMaterialCreation() {
        // Verifica se o ID foi gerado corretamente
        assertThat(material.getId()).isNotNull();

        // Verifica se a descrição foi atribuída corretamente
        assertThat(material.getDescription()).isNotNull().isNotEmpty();
    }

    /**
     * Testa a igualdade entre instâncias da classe Material.
     * Verifica se instâncias com o mesmo ID e atributos são consideradas iguais.
     */
    @Test
    @DisplayName("Should consider Material instances with the same attributes as equal")
    void testMaterialEquality() {
        // Criando uma segunda instância com o mesmo ID e descrição
        Material anotherMaterial = new Material();
        anotherMaterial.setId(material.getId());
        anotherMaterial.setDescription(material.getDescription());

        // Verifica se as instâncias com o mesmo ID e atributos são consideradas iguais
        assertThat(material).isEqualTo(anotherMaterial);
    }

    /**
     * Testa o método toString da classe Material.
     * Verifica se o método toString retorna uma representação correta da instância.
     */
    @Test
    @DisplayName("Should return correct string representation of Material")
    void testMaterialToString() {
        // Verifica o método toString
        String materialString = material.toString();
        assertThat(materialString).contains(material.getId().toString());
    }

    /**
     * Testa os métodos setters e getters da classe Material.
     * Verifica se os métodos setId e setDescription definem corretamente os atributos
     * e se os métodos getId e getDescription retornam os valores esperados.
     */
    @Test
    @DisplayName("Should set and get properties correctly")
    void testSettersAndGetters() {
        Long id = faker.number().randomNumber();
        String description = faker.commerce().material();

        material.setId(id);
        material.setDescription(description);

        assertThat(material.getId()).isEqualTo(id);
        assertThat(material.getDescription()).isEqualTo(description);
    }

    /**
     * Testa os métodos hashCode e equals da classe Material.
     * Verifica se duas instâncias com os mesmos valores de id são iguais e se têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should correctly implement equals and hashCode")
    void testEqualsAndHashCode() {
        Long id = 1L;

        Material material1 = new Material();
        material1.setId(id);
        Material material2 = new Material();
        material2.setId(id);
        Material material3 = new Material();
        material3.setId(2L); // Instância com id diferente

        assertThat(material1).isEqualTo(material2);
        assertThat(material1.hashCode()).isEqualTo(material2.hashCode());

        // Verifica que objetos diferentes não são iguais
        assertThat(material1).isNotEqualTo(material3);
        assertThat(material1.hashCode()).isNotEqualTo(material3.hashCode());
    }
}
