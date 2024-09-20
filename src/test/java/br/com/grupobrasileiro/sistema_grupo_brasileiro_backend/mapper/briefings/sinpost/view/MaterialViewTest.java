package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.sinpost.view;


import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.MaterialView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view.MaterialViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.Material;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests the MaterialViewMapper class.
 * Verifies the mapping from Material to MaterialView.
 */
public class MaterialViewTest {

    private MaterialViewMapper materialViewMapper;
    private Faker faker;

    @BeforeEach
    void setUp() {
        materialViewMapper = new MaterialViewMapper();
        faker = new Faker();
    }

    /**
     * Tests the mapping of Material to MaterialView.
     * Verifies that the values are correctly mapped.
     */
    @Test
    @DisplayName("Should correctly map Material to MaterialView")
    void shouldMapMaterialToMaterialView() {
        // Create fake data using Faker
        Long id = faker.number().randomNumber();
        String description = faker.lorem().sentence();

        // Create a fake Material object
        Material material = new Material();
        material.setId(id);
        material.setDescription(description);

        // Execute the method to be tested
        MaterialView result = materialViewMapper.map(material);

        // Verify that the result was mapped correctly
        assertThat(result.id()).isEqualTo(id);
        assertThat(result.description()).isEqualTo(description);
    }

    /**
     * Tests the mapping with null values in the Material object.
     * Verifies that the mapper handles null values correctly.
     */
    @Test
    @DisplayName("Should handle null values in Material correctly")
    void shouldHandleNullValuesInMaterial() {
        // Create a Material object with null values
        Material material = new Material();
        material.setId(null);
        material.setDescription(null);

        // Execute the method to be tested
        MaterialView result = materialViewMapper.map(material);

        // Verify that the result was mapped correctly
        assertThat(result.id()).isNull();
        assertThat(result.description()).isNull();
    }

    /**
     * Tests the mapping of Material with an empty description.
     * Verifies that the empty description is handled correctly.
     */
    @Test
    @DisplayName("Should map Material with empty description correctly")
    void shouldMapMaterialWithEmptyDescription() {
        // Create a Material object with an empty description
        Material material = new Material();
        material.setId(faker.number().randomNumber());
        material.setDescription("");

        // Execute the method to be tested
        MaterialView result = materialViewMapper.map(material);

        // Verify that the result was mapped correctly
        assertThat(result.id()).isNotNull();
        assertThat(result.description()).isEqualTo("");
    }

    /**
     * Tests the mapping of a Material object with only an ID.
     * Verifies that the description is null in the result.
     */
    @Test
    @DisplayName("Should map Material with only ID correctly")
    void shouldMapMaterialWithOnlyId() {
        // Create a Material object with only ID
        Long id = faker.number().randomNumber();
        Material material = new Material();
        material.setId(id);
        material.setDescription(null);

        // Execute the method to be tested
        MaterialView result = materialViewMapper.map(material);

        // Verify that the result was mapped correctly
        assertThat(result.id()).isEqualTo(id);
        assertThat(result.description()).isNull();
    }

    /**
     * Tests the mapping of multiple Material objects to verify consistent mapping.
     * This can be useful to check if the mapper handles batch processing well.
     */
    @Test
    @DisplayName("Should map multiple Materials to MaterialViews correctly")
    void shouldMapMultipleMaterialsToMaterialViews() {
        // Create an array of Material objects
        Material[] materials = new Material[3];
        for (int i = 0; i < materials.length; i++) {
            materials[i] = new Material();
            materials[i].setId(faker.number().randomNumber());
            materials[i].setDescription(faker.lorem().sentence());
        }

        // Map each Material to MaterialView and verify
        for (Material material : materials) {
            MaterialView result = materialViewMapper.map(material);
            assertThat(result.id()).isEqualTo(material.getId());
            assertThat(result.description()).isEqualTo(material.getDescription());
        }
    }
}
