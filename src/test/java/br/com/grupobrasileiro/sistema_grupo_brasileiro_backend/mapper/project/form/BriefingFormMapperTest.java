package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.form;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        LocalDateTime expectedTime = LocalDateTime.of(2024, 9, 24, 10, 0);
        BriefingForm briefingForm = new BriefingForm(
            expectedTime,
            "Test Description",
            Collections.emptySet(),
            "Test Company",
            1L,
            null
        );

        // Act
        Briefing result = briefingFormMapper.map(briefingForm);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(expectedTime, result.getExpectedTime(), "Expected time should match");
        assertEquals("Test Description", result.getDetailedDescription(), "Detailed description should match");
        assertEquals("Test Company", result.getOtherCompany(), "Other company should match");
        assertNotNull(result.getStartTime(), "Start time should not be null"); // Verificando se startTime foi preenchido
    }

    /**
     * Testa o mapeamento de BriefingForm com campos opcionais nulos.
     * Verifica se o mapeamento lida corretamente com valores nulos para campos não obrigatórios.
     */
    @Test
    @DisplayName("Should handle null values for optional fields in BriefingForm")
    void handleNullOptionalFieldsInBriefingForm() {
        // Arrange
        BriefingForm briefingForm = new BriefingForm(
            LocalDateTime.now(),
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
        assertEquals(null, result.getDetailedDescription(), "Detailed description should be null");
        assertEquals(null, result.getOtherCompany(), "Other company should be null");
    }

    /**
     * Testa o mapeamento de BriefingForm com todos os campos preenchidos.
     * Verifica se o mapeamento lida corretamente com um formulário completo.
     */
    @Test
    @DisplayName("Should map all fields from BriefingForm to Briefing when all fields are provided")
    void mapCompleteBriefingForm() {
        // Arrange
        LocalDateTime expectedTime = LocalDateTime.of(2024, 12, 31, 15, 0);
        BriefingForm briefingForm = new BriefingForm(
            expectedTime,
            "Complete Test Description",
            Collections.emptySet(),
            "Another Test Company",
            1L,
            null
        );

        // Act
        Briefing result = briefingFormMapper.map(briefingForm);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(expectedTime, result.getExpectedTime(), "Expected time should match");
        assertEquals("Complete Test Description", result.getDetailedDescription(), "Detailed description should match");
        assertEquals("Another Test Company", result.getOtherCompany(), "Other company should match");
    }

    /**
     * Testa o mapeamento de BriefingForm com um horário de conclusão esperado no passado.
     * Verifica se o mapeamento lida corretamente com datas do passado.
     */
    @Test
    @DisplayName("Should handle past expected time in BriefingForm")
    void handlePastExpectedTime() {
        // Arrange
        LocalDateTime expectedTime = LocalDateTime.of(2020, 1, 1, 10, 0);
        BriefingForm briefingForm = new BriefingForm(
            expectedTime,
            "Past Expected Time Description",
            Collections.emptySet(),
            "Company Past",
            1L,
            null
        );

        // Act
        Briefing result = briefingFormMapper.map(briefingForm);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(expectedTime, result.getExpectedTime(), "Expected time should match");
        assertEquals("Past Expected Time Description", result.getDetailedDescription(), "Detailed description should match");
        assertEquals("Company Past", result.getOtherCompany(), "Other company should match");
    }

    /**
     * Testa o mapeamento de BriefingForm com valores extremos no horário esperado.
     * Verifica se o mapeamento lida corretamente com valores limites de data.
     */
    @Test
    @DisplayName("Should handle extreme values for expected time in BriefingForm")
    void handleExtremeExpectedTime() {
        // Arrange
        LocalDateTime expectedTime = LocalDateTime.of(3000, 1, 1, 0, 0);
        BriefingForm briefingForm = new BriefingForm(
            expectedTime,
            "Extreme Future Time Description",
            Collections.emptySet(),
            "Future Company",
            1L,
            null
        );

        // Act
        Briefing result = briefingFormMapper.map(briefingForm);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(expectedTime, result.getExpectedTime(), "Expected time should match");
        assertEquals("Extreme Future Time Description", result.getDetailedDescription(), "Detailed description should match");
        assertEquals("Future Company", result.getOtherCompany(), "Other company should match");
    }
}

