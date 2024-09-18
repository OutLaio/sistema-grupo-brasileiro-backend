package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    void testMaterialCreation() {
        // Verifica se o ID foi gerado corretamente
        assertThat(material.getId()).isNotNull();

        // Verifica se a descrição foi atribuída corretamente
        assertThat(material.getDescription()).isNotNull().isNotEmpty();
    }

    @Test
    void testMaterialEquality() {
        // Criando uma segunda instância com o mesmo ID
        Material anotherMaterial = new Material();
        anotherMaterial.setId(material.getId());
        anotherMaterial.setDescription(material.getDescription());

        // Verifica se as instâncias com o mesmo ID e atributos são consideradas iguais
        assertThat(material).isEqualTo(anotherMaterial);
    }

    @Test
    void testMaterialToString() {
        // Verifica o método toString
        String materialString = material.toString();
        assertThat(materialString).contains(material.getId().toString());
    }
}
