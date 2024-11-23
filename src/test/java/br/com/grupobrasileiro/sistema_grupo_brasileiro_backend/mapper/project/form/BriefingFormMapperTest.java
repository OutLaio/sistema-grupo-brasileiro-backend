package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.form;


import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;

class BriefingFormMapperTest {

    @InjectMocks
    private BriefingFormMapper briefingFormMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testa o mapeamento de BriefingForm para Briefing.
     * Verifica se todos os campos obrigatórios são mapeados corretamente.
     */
    @Test
    @DisplayName("Should map BriefingForm to Briefing correctly")
    void mapBriefingFormToBriefing() {
        // Arrange
        LocalDate expectedDate = LocalDate.of(2024, 9, 24);
        BriefingForm briefingForm = new BriefingForm(
            "Test Description",
            Collections.emptySet(),
            "Test Company",
            1L,
            null // MeasurementsForm, null se não for necessário para o teste
        );

        // Act
        Briefing result = briefingFormMapper.map(briefingForm);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(expectedDate, result.getExpectedTime(), "Expected time should match");
        assertEquals("Test Description", result.getDetailedDescription(), "Detailed description should match");
        assertEquals("Test Company", result.getOtherCompany(), "Other company should match");
        assertNotNull(result.getStartTime(), "Start time should not be null");
    }

    @Test
    @DisplayName("Should handle null values for optional fields in BriefingForm")
    void handleNullOptionalFieldsInBriefingForm() {
        // Arrange
        LocalDate expectedDate = LocalDate.now();
        BriefingForm briefingForm = new BriefingForm(
            null,
            Collections.emptySet(),
            null,
            1L,
            null
        );

        // Act
        Briefing result = briefingFormMapper.map(briefingForm);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertNotNull(result.getStartTime(), "Start time should not be null");
     //   assertNull(result.getDetailedDescription(), "Detailed description should be null");
      //  assertNull(result.getOtherCompany(), "Other company should be null");
    }

    @Test
    @DisplayName("Should map all fields from BriefingForm to Briefing when all fields are provided")
    void mapCompleteBriefingForm() {
        // Arrange
        LocalDate expectedDate = LocalDate.of(2024, 12, 31);
        BriefingForm briefingForm = new BriefingForm(
            "Complete Test Description",
            Collections.emptySet(),
            "Another Test Company",
            1L,
            null // ou crie um MeasurementsForm se necessário
        );

        // Act
        Briefing result = briefingFormMapper.map(briefingForm);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(expectedDate, result.getExpectedTime(), "Expected time should match");
        assertEquals("Complete Test Description", result.getDetailedDescription(), "Detailed description should match");
        assertEquals("Another Test Company", result.getOtherCompany(), "Other company should match");
    }

    @Test
    @DisplayName("Should handle past expected date in BriefingForm")
    void handlePastExpectedDate() {
        // Arrange
        LocalDate expectedDate = LocalDate.of(2020, 1, 1);
        BriefingForm briefingForm = new BriefingForm(
            "Past Expected Date Description",
            Collections.emptySet(),
            "Company Past",
            1L,
            null
        );

        // Act
        Briefing result = briefingFormMapper.map(briefingForm);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(expectedDate, result.getExpectedTime(), "Expected time should match");
        assertEquals("Past Expected Date Description", result.getDetailedDescription(), "Detailed description should match");
        assertEquals("Company Past", result.getOtherCompany(), "Other company should match");
    }

    @Test
    @DisplayName("Should handle extreme values for expected date in BriefingForm")
    void handleExtremeExpectedDate() {
        // Arrange
        LocalDate expectedDate = LocalDate.of(3000, 1, 1);
        BriefingForm briefingForm = new BriefingForm(
            "Extreme Future Date Description",
            Collections.emptySet(),
            "Future Company",
            1L,
            null
        );

        // Act
        Briefing result = briefingFormMapper.map(briefingForm);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(expectedDate, result.getExpectedTime(), "Expected time should match");
        assertEquals("Extreme Future Date Description", result.getDetailedDescription(), "Detailed description should match");
        assertEquals("Future Company", result.getOtherCompany(), "Other company should match");
    }
    
}