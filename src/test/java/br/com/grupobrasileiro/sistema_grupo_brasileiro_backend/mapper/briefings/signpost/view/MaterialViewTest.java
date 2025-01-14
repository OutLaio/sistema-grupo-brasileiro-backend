package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.MaterialView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view.MaterialViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.Material;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MaterialViewTest {

    private MaterialViewMapper materialViewMapper;
    private Faker faker;

    @BeforeEach
    void setUp() {
        materialViewMapper = new MaterialViewMapper();
        faker = new Faker();
    }

    @Test
    @DisplayName("Should correctly map Material to MaterialView")
    void mapMaterialToMaterialView() { // O nome do método deve começar com letra minúscula
        Long id = faker.number().randomNumber();
        String description = faker.lorem().sentence();

        Material material = new Material();
        material.setId(id);
        material.setDescription(description);

        MaterialView result = materialViewMapper.map(material);

        assertThat(result.id()).isEqualTo(id);
        assertThat(result.description()).isEqualTo(description);
    }

    @Test
    @DisplayName("Should handle null values in Material correctly")
    void handleNullValuesInMaterial() {
        Material material = new Material();
        material.setId(null);
        material.setDescription(null);

        MaterialView result = materialViewMapper.map(material);

        assertThat(result.id()).isNull();
        assertThat(result.description()).isNull();
    }

    @Test
    @DisplayName("Should map Material with empty description correctly")
    void mapMaterialWithEmptyDescription() {
        Material material = new Material();
        material.setId(faker.number().randomNumber());
        material.setDescription("");

        MaterialView result = materialViewMapper.map(material);

        assertThat(result.id()).isNotNull();
        assertThat(result.description()).isEqualTo("");
    }

    @Test
    @DisplayName("Should map Material with only ID correctly")
    void mapMaterialWithOnlyId() {
        Long id = faker.number().randomNumber();
        Material material = new Material();
        material.setId(id);
        material.setDescription(null);

        MaterialView result = materialViewMapper.map(material);

        assertThat(result.id()).isEqualTo(id);
        assertThat(result.description()).isNull();
    }

    @Test
    @DisplayName("Should map multiple Materials to MaterialViews correctly")
    void mapMultipleMaterialsToMaterialViews() {
        Material[] materials = new Material[3];
        for (int i = 0; i < materials.length; i++) {
            materials[i] = new Material();
            materials[i].setId(faker.number().randomNumber());
            materials[i].setDescription(faker.lorem().sentence());
        }

        for (Material material : materials) {
            MaterialView result = materialViewMapper.map(material);
            assertThat(result.id()).isEqualTo(material.getId());
            assertThat(result.description()).isEqualTo(material.getDescription());
        }
    }

    @Test
    @DisplayName("Should handle uninitialized Material correctly")
    void handleUninitializedMaterial() {
        Material material = new Material(); // Sem inicializar valores

        MaterialView result = materialViewMapper.map(material);

        assertThat(result.id()).isNull();
        assertThat(result.description()).isNull();
    }

    @Test
    @DisplayName("Should handle Material with long description correctly")
    void handleMaterialWithLongDescription() {
        Long id = faker.number().randomNumber();
        String longDescription = faker.lorem().fixedString(1000); // 1000 caracteres

        Material material = new Material();
        material.setId(id);
        material.setDescription(longDescription);

        MaterialView result = materialViewMapper.map(material);

        assertThat(result.id()).isEqualTo(id);
        assertThat(result.description()).isEqualTo(longDescription);
    }

    @Test
    @DisplayName("Should handle Material with special characters correctly")
    void handleMaterialWithSpecialCharacters() {
        Long id = faker.number().randomNumber();
        String specialCharacters = "!@#$%^&*()_+";

        Material material = new Material();
        material.setId(id);
        material.setDescription(specialCharacters);

        MaterialView result = materialViewMapper.map(material);

        assertThat(result.id()).isEqualTo(id);
        assertThat(result.description()).isEqualTo(specialCharacters);
    }
}
