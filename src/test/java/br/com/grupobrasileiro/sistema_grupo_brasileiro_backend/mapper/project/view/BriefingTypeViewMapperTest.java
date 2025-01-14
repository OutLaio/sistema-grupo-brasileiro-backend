package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;

class BriefingTypeViewMapperTest {

    private BriefingTypeViewMapper briefingTypeViewMapper;
    private Faker faker;

    @BeforeEach
    void setUp() {
        briefingTypeViewMapper = new BriefingTypeViewMapper();
        faker = new Faker();
    }

    /**
     * Testa o mapeamento de BriefingType para BriefingTypeView.
     * Verifica se todos os campos são mapeados corretamente.
     */
    @Test
    @DisplayName("Should map BriefingType to BriefingTypeView")
    void testMapToView() {
        // Arrange
        Long id = faker.number().randomNumber();
        String description = faker.lorem().sentence();

        BriefingType briefingType = new BriefingType(id, description);

        // Act
        BriefingTypeView result = briefingTypeViewMapper.map(briefingType);

        // Assert
        assertNotNull(result, "Mapped BriefingTypeView should not be null");
        assertEquals(id, result.id(), "BriefingTypeView ID should match");
        assertEquals(description, result.description(), "BriefingTypeView description should match");
    }

    /**
     * Testa o mapeamento de BriefingType com descrição nula.
     * Verifica se o mapeamento é feito corretamente e se a descrição é tratada como nula.
     */
    @Test
    @DisplayName("Should handle null description in BriefingType")
    void testHandleNullDescription() {
        // Arrange
        Long id = faker.number().randomNumber();
        BriefingType briefingType = new BriefingType(id, null);

        // Act
        BriefingTypeView result = briefingTypeViewMapper.map(briefingType);

        // Assert
        assertNotNull(result, "Mapped BriefingTypeView should not be null");
        assertEquals(id, result.id(), "BriefingTypeView ID should match");
        assertNull(result.description(), "BriefingTypeView description should be null");
    }

    /**
     * Testa o mapeamento de BriefingType com ID nulo.
     * Verifica se o ID é tratado corretamente como nulo no BriefingTypeView.
     */
    @Test
    @DisplayName("Should handle null ID in BriefingType")
    void testHandleNullId() {
        // Arrange
        BriefingType briefingType = new BriefingType(null, "Test Description");

        // Act
        BriefingTypeView result = briefingTypeViewMapper.map(briefingType);

        // Assert
        assertNotNull(result, "Mapped BriefingTypeView should not be null");
        assertNull(result.id(), "BriefingTypeView ID should be null");
        assertEquals("Test Description", result.description(), "BriefingTypeView description should match");
    }

    /**
     * Testa o mapeamento de BriefingType com descrição vazia.
     * Verifica se a descrição é mapeada corretamente como vazia.
     */
    @Test
    @DisplayName("Should handle empty description in BriefingType")
    void testHandleEmptyDescription() {
        // Arrange
        Long id = faker.number().randomNumber();
        BriefingType briefingType = new BriefingType(id, "");

        // Act
        BriefingTypeView result = briefingTypeViewMapper.map(briefingType);

        // Assert
        assertNotNull(result, "Mapped BriefingTypeView should not be null");
        assertEquals(id, result.id(), "BriefingTypeView ID should match");
        assertEquals("", result.description(), "BriefingTypeView description should be empty");
    }
}
