package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    @DisplayName("Should map BriefingType to BriefingTypeView")
    void shouldMapBriefingTypeToBriefingTypeView() {
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
}
